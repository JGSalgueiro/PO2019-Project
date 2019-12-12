package m19.core;

import java.io.Serializable;
import java.io.IOException;
import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
import m19.app.exception.RuleFailedException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Library implementation
 * This class implements a Library and stores all the info related with it
 * such as Users , Works, Request Rules and Requests
 * @author Joao Salgueiro 93725
 * @author Pedro Marques 93746
 * @version 2.0
 * @since October 2019
 * @see 
 */
public class Library implements Serializable {
  /** 
   * Serial number for serialization. 
   */
  private static final long serialVersionUID = 201901101348L;
  /** 
   * The id of the next User to be created 
   */
  private int _nextUserId;
  /** 
   * The id of the next Work to be created 
   */
  private int _nextWorkId;
  /**
   * HashMap that stores all the Users by Id
   */
  private Map<Integer,User> _userList;
  /**
   * HashMap that stores all the Works by Id
   */
  private Map<Integer,Work> _workList;
  /**
   * List that stores all the Attended Requests
   */
  private List<Request> _requests;
  /**
   * List that stores all the Request Rules
   */
  private List<Rules> _rules;

  /**
   * Constructor of the Library Class
   * Inicializes the nextUserId and nextWorkId at 0
   * Adds all the current Request Rules to the _rules array
   */
  public Library(){
    _nextWorkId = 0;
    _nextUserId = 0;
    _userList = new HashMap<Integer,User>();  
    _workList = new HashMap<Integer,Work>();
    _requests = new ArrayList<Request>();
    _rules = new ArrayList<Rules>();
    _rules.add(new CheckSameReqTwice());
    _rules.add(new CheckUserSuspended());
    _rules.add(new CheckAvailableCopies());
    _rules.add(new CheckNumMaxReqs());
    _rules.add(new CheckReferenceWork());
    _rules.add(new CheckPrice());
  }

  /**
   * @return the NextWorkId
   */
  int getNextWorkId(){
    return _nextWorkId; 
  }

  /**
   * @return the NextUserId
   */
  int getNextUserId(){
    return _nextUserId;
  }

  /**
   * Increments the NextUserId variable 
   */
  void incrementUserId(){
    _nextUserId++;
  }

  /**
   * Increments the NextWorkId variable 
   */
  void incrementWorkId(){
    _nextWorkId++; 
  }

  /**
   * @return the HashMap that store Users
   */
  Map<Integer,User> getAllUsers(){
    return _userList;
  }

  /**
   * @return the HashMap that store Works
   */
  Map<Integer,Work> getAllWorks(){
    return _workList;
  }

  /**
   * Creates a new User and Stores it in the HashMap
   * Receives the User Name and User Email
   * @param uName
   * @param uMail
   */
  void createUser(String uName, String uMail){
    User u = new User(_nextUserId, uName, uMail);
    _userList.put(_nextUserId, u);
    _nextUserId++;

   }

   /**
   * Inserts a User into the HashMap
   * @param user
   */
  void addUser(User user){
    _userList.put(_nextUserId, user);
    _nextUserId++;
  }

  /**
   * Inserts a Request into the Request List
   * @param request
   */
  void attendRequest(Request request){
    _requests.add(request);
  }

  /**
   * Inserts a Book into the Works HashMap
   * @param book
   */
  void addBook(Book book){
    _workList.put(_nextWorkId, book);
    _nextWorkId++;
  }

  /**
   * Inserts a Dvd into the Works HashMap
   * @param dvd
   */
  void addDvd(Dvd dvd){
    _workList.put(_nextWorkId, dvd);
    _nextWorkId++;
  }

  /**
   * Creates a new Book and Stores it in the Works hash map
   * @param wTitle
   * @param wPrice
   * @param wCopies
   * @param wAuthor
   * @param wISBN
   * @param wCategory
   */
  void createBook(String wTitle ,int wPrice ,int wCopies ,String wAuthor ,String wISBN, String wCategory){
    Book book = new Book(_nextWorkId, wTitle, wPrice, wCopies, wAuthor, wISBN, wCategory);
    _workList.put(_nextWorkId, book);
    _nextWorkId++;
  }

  /**
   * Creates a new Dvd and Stores it in the Works hash map
   * @param wTitle
   * @param wPrice
   * @param wCopies
   * @param wDirector
   * @param wIGAC
   * @param wCategory
   */
  void createDvd(String wTitle ,int wPrice ,int wCopies ,String wDirector ,String wIGAC, String wCategory){
    Dvd dvd = new Dvd(_nextWorkId ,wTitle ,wPrice ,wCopies ,wDirector ,wIGAC, wCategory);
    _workList.put(_nextWorkId, dvd);
    _nextWorkId++;
  }

  /**
   * Verifys all Check rules for Requisitions in Order 
   * Returns the number of the Rule it failed
   * If no rule fails returns 0
   * @param user
   * @param work
   **/
  int verifyReq(User user, Work work){
    int i = 1;
    for(Rules rule : _rules){
      if(!rule.rule(user, work)){
        return i;
      }
      i++;
    }
    return 0;
  } 

  /**
   * Receives a user, a Work and a Date and creates an acording Request 
   * Automaticly removes the Available copies number of set Work by One
   * @param user
   * @param work
   * @param date
   * @return the created Request
   */
  int createRequest(User user, Work work, int date) throws RuleFailedException{ 
    int res = verifyReq(user, work);

    if(res == 0 && !user.getIsSuspended()){

      int deadline = user.atributeReturnDate(work.getCopies()) + date;
      Request request = new Request(deadline, user, work);
      _requests.add(request);
      user.makeRequest(request);
      work.removeAvailableCopies();
      return deadline;    
    }
    else if(res == 3){
      return -1;
    }
    throw new RuleFailedException(user.getUserID(), work.getWorkID(), res);
  }

  /**
   * User returns Work at the date passed as argument
   * @param user
   * @param work 
   * @param date
   * @return the fine corresponding to the deliver of set work (0 if delivered on time)
   */
  int returnWork(User user, Work work, int date){
    for(Request request : _requests){
      if(request.getUser() == user && request.getWork() == work){
        int deadline = request.getDeadline();
        work.incrementAvailableCopies();
        user.deleteRequests(request);
        _requests.remove(request);
        if(deadline >= date){
          user.workDeliveredOnTime();
        }
        else if(deadline < date){
          user.workNotDeliveredOnTime();
        }
        work.notificationReq();
        work.removeUserReq();
        user.checkStreak();  
        return request.getFine();
      }
    }
    return -1;
  }

  /**
   * Updates the request variables in acordance to the date
   * @param date
   */
  void updateRequests(int date){
    for(Request request : _requests){
      request.updateReq(date);
    }
  }

  /**
   * Searchs for the User with matching id
   * @param id
   * @return the corresponding User
   */
  User findUserbyId(int id){
    return _userList.get(id);
  }

  /**
   * Searchs for the Work with matching id
   * @param id
   * @return the corresponding Work
   */
  Work findWorkbyId(int id){
    return _workList.get(id);
  }
  
  /**
  * Search Parameter : Title of the Work
  * @param searchTerm
  * @return the work corresponding to the search Parameter
  **/
  Work findWorkbyTitle(String searchTerm){
    List<Work> workArrays = new ArrayList<Work>(_workList.values());
    for(Work work : workArrays){
        if(work.getTitle().equals(searchTerm)){
          return work;
        }
    }
    return null;
  }

  /**
   * By given String creates a Category Class
   * @param string
   * @return the corresponding category class
   */
  Category createCat(String string){
    switch(string){
      
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
