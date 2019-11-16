package m19.core;

public class Book extends Work{
	private final String _author;
	private final String _isbn;

	public Book(int wID, String wTitle ,int wPrice ,int wCopies ,String wAuthor ,String wISBN, Category cat){
		super(wID, wTitle, wPrice, wCopies, cat);
		_author = wAuthor;
		_isbn = wISBN;
	}

	public String getCreator(){
		return _author;
	}

	public String getTypeId(){
		return _isbn;
	}

	public String getType(){
		return "Livro";
	}

}