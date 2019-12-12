package m19.core;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
import m19.core.exception.ImportFileException;
import m19.app.exception.RuleFailedException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;
import m19.app.exception.WorkNotBorrowedByUserException;
import m19.app.exception.UserIsActiveException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * The façade class.
 */
public class LibraryManager {
  private Library _library;  
  private Date _date;
  private String _filename;
  private Rules _rules;

  /**
   * Constructor of the LibraryManager Class
   */
  public LibraryManager(){
    _library = new Library();
    _date = new Date();
    _filename = null;
    _rules = null;
  }
  
  public Map<Integer,User> getAllUsers(){
    return _library.getAllUsers();
  }

  public Map<Integer,Work> getAllWorks(){
    return _library.getAllWorks();
  }

  /** 
  * Automatic NextUserId incrementation 
  */
  public void registerUser(String uName, String uMail){
    _library.createUser(uName,uMail);
  }

  /** 
  * Automatic NextWorkId incrementation 
  */
  public void registerBook(String wTitle ,int wPrice ,int wCopies ,String wAuthor ,String wISBN, String cat){
    _library.createBook(wTitle ,wPrice ,wCopies ,wAuthor , wISBN, cat);
  }

  /** 
  * Automatic NextWorkId incrementation 
  */
  public void registerDvd(String wTitle ,int wPrice ,int wCopies ,String wDirector,String wIGAC, String cat){
    _library.createBook(wTitle ,wPrice ,wCopies ,wDirector , wIGAC, cat);
  }

  public User getUser(int id){
    return _library.findUserbyId(id);
  }

  /**
   * Gets a work by Search Term : Title
   * @param searchTerm
   * @return the Work with given Title
   */
  public Work getWork(String searchTerm){
    return _library.findWorkbyTitle(searchTerm);
  }

  public Work getWorkbyId(int id){
    return _library.findWorkbyId(id);
  }

  public int getUserNum(){
    return _library.getNextUserId();
  }

  public int getWorksNum(){
    return _library.getNextWorkId();
  }

  public int getDate(){
    return _date.getTime();
  }

  public String getFilename(){
    return _filename;
  }

  public int getNumberOfUserRequests(User user){
    return user.getNumRequests();
  }

  public void advanceDate(int dateDif){
    _date.advanceTime(dateDif);
    _library.updateRequests(_date.getTime());
  }

  public int getUserFine(User user){
    return user.getFine();
  }

  /**
   * @param userId
   * @param workId
   * @return the Return date for the work
   */
  public int requestWork(int userId, int workId) throws RuleFailedException, NoSuchUserException, NoSuchWorkException{
    User user = getUser(userId);
    Work work = getWorkbyId(workId);

    if(user == null){
      throw new NoSuchUserException(userId);
    }

    if(work == null){
      throw new NoSuchWorkException(workId);
    }

    return _library.createRequest(user, work, _date.getTime());
  }

  /** 
   * Adds a Request to a Work 
   * @param userId
   * @param workId
   */
  public void addUserReq(int userId, int workId) throws NoSuchUserException, NoSuchWorkException{
    User user = getUser(userId);
    Work work = getWorkbyId(workId);
    work.addUserReq(user);
  }

  /**
   * User with the given user Id returns the work with given work id
   * @param userId
   * @param workId
   * @return the fine associated with the return (0 if on time)
   */
  public int returnWork(int userId, int workId) throws WorkNotBorrowedByUserException, NoSuchUserException, NoSuchWorkException{
    User user = getUser(userId);
    Work work = getWorkbyId(workId);

    if(user == null){
      throw new NoSuchUserException(userId);
    }

    if(work == null){
      throw new NoSuchWorkException(workId);
    }

    if(!user.workIsRequested(workId)){
      throw new WorkNotBorrowedByUserException(workId, userId);
    }
    
    return _library.returnWork(user, work, _date.getTime());
  }

  /**
   * User pays the set amount of Fine
   * @param userId
   * @param userFine
   */
  public void payFine(int userId, int userFine) throws NoSuchUserException, UserIsActiveException{
    try{
      User user = getUser(userId);
      if(!user.getIsSuspended()){
        throw new UserIsActiveException(userId);
      }
      int faulty = user.checkPassedDeadline();
      if(faulty == 0){
        user.setSuspension(false);
      }
      user.pay(userFine);
    }catch(NullPointerException e){
      throw new NoSuchUserException(userId);
    }
  }

  public void payAllFine(int userId) throws NoSuchUserException, UserIsActiveException{
    try{
      User user = getUser(userId);
      if(!user.getIsSuspended()){
        throw new UserIsActiveException(userId);
      }
      int faulty = user.checkPassedDeadline();
      if(faulty == 0){
        user.setSuspension(false);
      }
      user.payAll();
    }catch(NullPointerException e){
      throw new NoSuchUserException(userId);
    }
  }

  /**
   * Fines the user the set amount
   * @param fine
   * @param user
   */
  public void updateFine(int fine, User user){
    user.setFine(fine);
  }

  /**
   * Notifies the User of the return of set Work
   * @param userId
   * @return a List of the Notification Class 
   */
  public List<Notification> warnUserWhenWorkIsAvailable(int userId) throws NoSuchUserException{
    try{
      User user = getUser(userId);
      return user.getNotification();
      
    }catch(NullPointerException e){
      throw new NoSuchUserException(userId);
    } 
  }

  public void clearUserNotifications(int userId) throws NoSuchUserException{
    try{
      User user = getUser(userId);
      user.clearNotifications();
      
    }catch(NullPointerException e){
      throw new NoSuchUserException(userId);
    } 
  }

  /**
   * Search Paramether: Title or Creator of the Work
   * @param searchTerm
   * @return the List of works with set paramther
   */
  public List<Work> worksSearchedByGivenTerm(String searchTerm){
    List<Work> arrayWorks = new ArrayList<Work>(getAllWorks().values());
    List<Work> searchedWorks = new ArrayList<Work>();
    for(Work work : arrayWorks){
      if(work.getTitle().toLowerCase().indexOf(searchTerm) != -1 || work.getCreator().toLowerCase().indexOf(searchTerm) != -1){
        searchedWorks.add(work);
      }
    }
    return searchedWorks;
  }

  /**
   * Serialize the persistent state of this application.
   * 
   * @throws MissingFileAssociationException if the name of the file to store the persistent
   *         state has not been set yet.
   * @throws IOException if some error happen during the serialization of the persistent state

   */
  public void save() throws MissingFileAssociationException, IOException {
    ObjectOutputStream objOut = null;
    FileOutputStream fpout = null;
    try{
      fpout = new FileOutputStream(_filename);
      objOut = new ObjectOutputStream(fpout);
      objOut.writeObject(_library);
      objOut.writeObject(_date);

    } finally{
      if (objOut != null){
        fpout.close();
        objOut.close();
      }
    }
  }

  /**
   * Serialize the persistent state of this application into the specified file.
   * 
   * @param filename the name of the target file
   *
   * @throws MissingFileAssociationException if the name of the file to store the persistent
   *         is not a valid one.
   * @throws IOException if some error happen during the serialization of the persistent state
   */
  public void saveAs(String filename) throws MissingFileAssociationException, IOException {
    _filename = filename;
    ObjectOutputStream objOut = null;
    FileOutputStream fpout = null;
    try{
      fpout = new FileOutputStream(filename);
      objOut = new ObjectOutputStream(fpout);
      objOut.writeObject(_library);
      objOut.writeObject(_date);

    } finally{
      if (objOut != null){
        fpout.close();
        objOut.close();
      }
    }
  } 

  /**
   * Recover the previously serialized persitent state of this application.
   * 
   * @param filename the name of the file containing the perssitente state to recover
   *
   * @throws IOException if there is a reading error while processing the file
   * @throws FileNotFoundException if the file does not exist
   * @throws ClassNotFoundException 
   */
  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
    ObjectInputStream objIn = null;
    FileInputStream file = null;
    try{
      file = new FileInputStream(filename);
      objIn = new ObjectInputStream(file);
      _library = (Library)objIn.readObject();
      _date = (Date)objIn.readObject(); 
      _filename = filename;

    } finally {
      if (objIn != null){
        file.close();
        objIn.close();
      }
    }
  }

  /**
   * Set the state of this application from a textual representation stored into a file.
   * 
   * @param datafile the filename of the file with the textual represntation of the state of this application.
   * @throws ImportFileException if it happens some error during the parsing of the textual representation.
   * @throws BadEntrySpecificationException
   */
  public void importFile(String datafile) throws ImportFileException{
    try {
      _library.importFile(datafile);
    } catch (IOException | BadEntrySpecificationException e) {
      throw new ImportFileException(e);
    }
  }
}
