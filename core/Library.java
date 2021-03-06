package m19.core;

import java.io.Serializable;
import java.io.IOException;
import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
import m19.app.exception.RuleFailedException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 201901101348L;

  private int _nextUserId;
  private int _nextWorkId;
  private HashMap<Integer,User> _userList; 
  private HashMap<Integer,Work> _workList;
  //private List<Request> _pendingRequests;
  private List<Request> _requests;//atendedRequests
  private List<Rules> _rules;

  public Library(){
    _nextWorkId = 0;
    _nextUserId = 0;
    _userList = new HashMap<Integer,User>();  
    _workList = new HashMap<Integer,Work>();
    //_pendingRequests = new ArrayList<Request>();
    _requests = new ArrayList<Request>();
    _rules = new ArrayList<Rules>();
    _rules.add(new CheckSameReqTwice());
    _rules.add(new CheckUserSuspended());
    _rules.add(new CheckAvailableCopies());
    _rules.add(new CheckNumMaxReqs());
    _rules.add(new CheckReferenceWork());
    _rules.add(new CheckPrice());
  }

  int getNextWorkId(){
    return _nextWorkId; 
  }

  int getNextUserId(){
    return _nextUserId;
  }

  void incrementUserId(){
    _nextUserId++;
  }

  void incrementWorkId(){
    _nextWorkId++; 
  }

  HashMap<Integer,User> getAllUsers(){
    return _userList;
  }

  HashMap<Integer,Work> getAllWorks(){
    return _workList;
  }

  /*List<Request> getAllPendingRequests(){
    return _pendingRequests;
  }*/

  void createUser(String uName, String uMail){
    User u = new User(_nextUserId, uName, uMail);
    _userList.put(_nextUserId, u);
    _nextUserId++;

   }

  void addUser(User u){
    _userList.put(_nextUserId,u);
    _nextUserId++;
  }

  void attendRequest(Request req){
    _requests.add(req);
  }

  /*void waitForWork(Request req){
    _pendingRequests.add(req);
  }*/

  void addBook(Book b){
    _workList.put(_nextWorkId, b);
    _nextWorkId++;
  }

  void addDvd(Dvd d){
    _workList.put(_nextWorkId, d);
    _nextWorkId++;
  }

  void createBook(String wTitle ,int wPrice ,int wCopies ,String wAuthor ,String wISBN, String cat){
    Book b = new Book(_nextWorkId ,wTitle ,wPrice , wCopies, wAuthor, wISBN, cat);
    _workList.put(_nextWorkId, b);
    _nextWorkId++;
  }

  void createDvd(String wTitle ,int wPrice ,int wCopies ,String wDirector ,String wIGAC, String cat){
    Dvd b = new Dvd(_nextWorkId ,wTitle ,wPrice ,wCopies ,wDirector ,wIGAC, cat);
    _workList.put(_nextWorkId, b);
    _nextWorkId++;
  }

  /**
   * Verifys all Check rules for Requisitions in Order 
   * Returns the number of the Rule it failed
   * If no rule fails returns 0
   **/
  int verifyReq(User u, Work w){
    int i = 1;
    for(Rules r : _rules){
      if(!r.rule(u, w)){
        return i;
      }
      i++;
    }
    return 0;
  } 

  int atributeReturnDate(User u, Work w){
    if(u.getBehaviour().equals("FALTOSO")){
      return 2;
    }

    if(w.getAvailableCopies() == 1){
      if(u.getBehaviour().equals("NORMAL")){
        return 3;
      }
      if(u.getBehaviour().equals("CUMPRIDOR")){
        return 5;
      }
    }

    else if(w.getAvailableCopies() < 5){
      if(u.getBehaviour().equals("NORMAL")){
        return 8;
      }
      if(u.getBehaviour().equals("CUMPRIDOR")){
        return 15;
      }
    }

    else{
      if(u.getBehaviour().equals("NORMAL")){
        return 15;
      }
      if(u.getBehaviour().equals("CUMPRIDOR")){
        return 30;
      }
    }
    return -1;
  }

  int createRequest(User u, Work w, int date) throws RuleFailedException{ 
    int res = verifyReq(u, w);
    if(res == 0 && u.getIsSuspended() == false){
      int deadline = atributeReturnDate(u, w) + date;
      System.out.println("cona");
      Request r = new Request(deadline, u, w);
      _requests.add(r);
      u.makeRequest(r);
      w.removeAvailableCopies();
      return deadline;    
    }
    else if(res == 3){
      return -1;
    }
    throw new RuleFailedException(u.getUserID(), w.getWorkID(), res);
  }

//FIXME DAWG
  int returnW(User u, Work w, int date){
    w.notificationReq();
    for(Request r : _requests){
      if(r.getUser() == u && r.getWork() == w){
        Request ret = r;
        int deadline = ret.getDeadline();
        w.addAvailableCopies(1);
        u.deleteRequests(ret);
        _requests.remove(ret);
        if(deadline <= date){
          u.workDeliveredOnTime();
        }
        else if(deadline > date){
          u.workNotDeliveredOnTime();
        }
        u.checkStreak();
        return u.getFine();
      }
    }
    return -1;
  }

  void updateRequests(int date){
    for(Request r : _requests){
      r.updateReq(date);
    }
  }

  User findUserbyId(int id){
    User u = _userList.get(id);
    return u;
  }

  Work findWorkbyId(int id){
    Work w = _workList.get(id);
    return w;
  }

  /*Request findPendingRequestbyId(int id){
    Request r = _pendingRequests.get(id);
    return r;
  }*/
  
  /**
  * Search Parameter : Title of the Work
  **/
  Work findWorkbyTitle(String searchTerm){
    List<Work> workArrays = new ArrayList<Work>(_workList.values());
    for(Work w : workArrays){
        if(w.getTitle().equals(searchTerm)){
          return w;
        }
    }
    return null;
  }

  Category createCat(String s){
    switch(s){
      
      case "Referência":
        return Category.REFERENCE;
      
      case "Ficção":
        return Category.FICTION;
      
      case "Técnica e Científica":
        return Category.SCITECH;
      
      default:
        return null;
    }
  }

  /**
   * Read the text input file at the beginning of the program and populates the
   * instances of the various possible types (books, DVDs, users).
   * 
   * @param filename
   *          name of the file to load
   * @throws BadEntrySpecificationException
   * @throws IOException
   */
  void importFile(String filename) throws BadEntrySpecificationException, IOException {
    try{
      Parser parser = new Parser(this);
      parser.parseFile(filename);
    } catch(BadEntrySpecificationException | IOException e){}
  }
}
