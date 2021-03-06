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

import java.util.HashMap;


/**
 * The façade class.
 */
public class LibraryManager {
  private Library _library;  
  private Date _date;
  private String _filename;
  private Rules _rules;

  public LibraryManager(){
    _library = new Library();
    _date = new Date();
    _filename = null;
    _rules = null;
  }
  
  public HashMap<Integer,User> getAllUsers(){
    return _library.getAllUsers();
  }

  public HashMap<Integer,Work> getAllWorks(){
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
    User u = _library.findUserbyId(id);
    return u;
  }

  public Work getWork(String searchTerm){
    Work w = _library.findWorkbyTitle(searchTerm);
    return w;
  }

  public Work getWorkbyId(int id){
    Work w = _library.findWorkbyId(id);
    return w;
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

  public void advanceDate(int dateDif){
    _date.advanceTime(dateDif);
    _library.updateRequests(_date.getTime());
  }

  public String getFilename(){
    return _filename;
  }

  public int requestWork(int uId, int wId) throws RuleFailedException, NoSuchUserException, NoSuchWorkException{
    User u = getUser(uId);
    Work w = getWorkbyId(wId);

    if(u == null){
      throw new NoSuchUserException(uId);
    }

    if(w == null){
      throw new NoSuchWorkException(wId);
    }

    int res = _library.createRequest(u, w, _date.getTime());
    return res;
  }

  public void addUserReq(int uId, int wId){
    User u = getUser(uId);
    Work w = getWorkbyId(wId);
    w.addUserReq(u);
  }

  public int returnWork(int uId, int wId) throws WorkNotBorrowedByUserException, NoSuchUserException, NoSuchWorkException{
    User u = getUser(uId);
    Work w = getWorkbyId(wId);

    if(u == null){
      throw new NoSuchUserException(uId);
    }

    if(w == null){
      throw new NoSuchWorkException(wId);
    }

    if(!u.workIsRequested(wId)){
      throw new WorkNotBorrowedByUserException(wId, uId);
    }
    
    int res = _library.returnW(u, w, _date.getTime());
    return res;
  }

  public void payFine(int uId) throws NoSuchUserException{
    User u = getUser(uId);
    u.pay();
    int faulty = u.checkPassedDeadline();
    if(faulty == 0){
      u.setSuspension(false);
    }
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
    try{
      FileOutputStream fpout = new FileOutputStream(_filename);
      objOut = new ObjectOutputStream(fpout);
      objOut.writeObject(_library);
      objOut.writeObject(_date);

    } finally{
      if (objOut != null){
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
    try{
      FileOutputStream fpout = new FileOutputStream(filename);
      objOut = new ObjectOutputStream(fpout);
      objOut.writeObject(_library);
      objOut.writeObject(_date);

    } finally{
      if (objOut != null){
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
    try{
      FileInputStream file = new FileInputStream(filename);
      objIn = new ObjectInputStream(file);
      _library = (Library)objIn.readObject();
      _date = (Date)objIn.readObject(); 

    } finally {
      if (objIn != null){
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
