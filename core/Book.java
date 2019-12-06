package m19.core;

import java.io.Serializable;

/**
 * Class that represents a Book.
 */
public class Book extends Work{
	/** Serial number for serialization. */
	private static final Long serialVersionUID = 201901101348L;

	private final String _author;
	private final String _isbn;

	public Book(int wID, String wTitle ,int wPrice ,int wCopies ,String wAuthor ,String wISBN, String cat){
		super(wID, wTitle, wPrice, wCopies, cat);
		_author = wAuthor;
		_isbn = wISBN;
	}

	String getCreator(){
		return _author;
	}

	String getTypeId(){
		return _isbn;
	}

	String getType(){
		return "Livro";
	}

	public String toString(){
      	return (getWorkID() + " - " + getAvailableCopies()+ " de " + getCopies() + " - " + getType() + " - " + getTitle() + " - " + getPrice() + " - " + getWorkCategory() + " - "+ getCreator() + " - " + getTypeId());
	}

}