package m19.core;

import java.io.Serializable;
import java.io.IOException;
import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
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


  public Library(){
    _nextWorkId = 1;
    _nextUserId = 1;
    _userList = new HashMap<Integer,User>();  
    _workList = new HashMap<Integer,Work>();
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

  /**
  * Automaticly Increments NextUserId
  **/
  void createUser(String uName, String uMail){
    User u = new User(_nextUserId, uName, uMail);
    _userList.put(_nextUserId, u);
    _nextUserId++;
   }

  void addUser(User u){
    _userList.put(_nextWorkId,u);
    _nextUserId++;
  }

  void addBook(Book b){
    _workList.put(_nextWorkId, b);
    _nextWorkId++;
  }

  void addDvd(Dvd d){
    _workList.put(_nextWorkId, d);
    _nextWorkId++;
  }

  void createBook(String wTitle ,int wPrice ,int wCopies ,String wAuthor ,String wISBN, Category cat){
    Book b = new Book(_nextWorkId ,wTitle ,wPrice , wCopies, wAuthor, wISBN, cat);
    _workList.put(_nextWorkId, b);
    _nextWorkId++;
  }

  void createDvd(String wTitle ,int wPrice ,int wCopies ,String wDirector ,String wIGAC, Category cat){
    Dvd b = new Dvd(_nextWorkId ,wTitle ,wPrice ,wCopies ,wDirector ,wIGAC, cat);
    _workList.put(_nextWorkId, b);
    _nextWorkId++;
  }

  User findUserbyId(int id){
    User u = _userList.get(id);
    return u;
  }

  Work findWorkbyId(int id){
    Work w = _workList.get(id);
    return w;
  }
  
  /**
  * Search Parameter : Title
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
