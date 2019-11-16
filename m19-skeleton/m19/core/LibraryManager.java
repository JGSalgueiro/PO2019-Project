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
import java.util.HashMap;


/**
 * The façade class.
 */
public class LibraryManager {
  private Library _library;  
  private Date _date;
  private String _filename;

  public LibraryManager(){
    _library = new Library();
    _date = new Date(0);
    _filename = null;
  }
  
  public HashMap<Integer,User> getAllUsers(){
    return _library.getAllUsers();
  }

  public HashMap<Integer,Work> getAllWorks(){
    return _library.getAllWorks();
  }

  public void registerUser(String uName, String uMail){
    _library.createUser(uName,uMail);
  }

  public void registerBook(String wTitle ,int wPrice ,int wCopies ,String wAuthor ,String wISBN, Category cat){
    _library.createBook(wTitle ,wPrice ,wCopies ,wAuthor , wISBN, cat);
  }

  public void registerDvd(String wTitle ,int wPrice ,int wCopies ,String wDirector,String wIGAC, Category cat){
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

  public int getWorksNum(){
    return _library.getNextWorkId();
  }

  public Date getDate(){
    return _date;
  }

  public String getFilename(){
    return _filename;
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
