package m19.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

import m19.core.exception.BadEntrySpecificationException;
import m19.core.Library;
import m19.core.Category;

/**
 * Class that Parses the a textual Input and converts it into a Library Object.
 */
public class Parser {
    private Library _library;

    Parser(Library lib) {
      _library = lib;
    }

  void parseFile(String filename) throws IOException, BadEntrySpecificationException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }

  private void parseLine(String line) throws BadEntrySpecificationException {
    String[] components = line.split(":");

    switch(components[0]) {
      case "DVD":
        parseDVD(components, line);
        break;
      case "BOOK":
        parseBook(components, line);
        break;
      case "USER":
        parseUser(components, line);
        break;
        
      default:
        throw new BadEntrySpecificationException("Invalid type " + components[0] +
                                                " in line " + line);
    }
  }

  private void parseDVD(String[] components, String line) throws BadEntrySpecificationException {
    if (components.length != 7){
      throw new BadEntrySpecificationException("Wrong number of fields (6) in " + line);
    }
    int wId = _library.getNextWorkId();
    Dvd dvd = new Dvd(wId, components[1], Integer.parseInt(components[3]), Integer.parseInt(components[6]),
                       components[2], components[5],components[4]);
    _library.addDvd(dvd);
  }

  private void parseBook(String[] components, String line) throws BadEntrySpecificationException {
    if (components.length != 7){
      throw new BadEntrySpecificationException("Wrong number of fields (6) in " + line);
    }
    int wId = _library.getNextWorkId();
    Book book = new Book(wId, components[1], Integer.parseInt(components[3]), Integer.parseInt(components[6]),
                       components[2], components[5], components[4]);
    _library.addBook(book);
  }

  private void parseUser(String[] components, String line) throws BadEntrySpecificationException {
    if (components.length != 3){
      throw new BadEntrySpecificationException("Wrong number of fields (2) in " + line);
    }
    int uId = _library.getNextUserId();
    User user = new User(uId,components[1],components[2]);
    _library.addUser(user);
  }
 }