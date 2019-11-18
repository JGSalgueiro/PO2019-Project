package m19.core;
import java.util.*;
import java.io.Serializable;

/**
 * Abstract Class that represents a Generic Work (DVD or Book).
 */
public abstract class Work implements Serializable{
	/** Serial number for serialization. */
	private static final Long serialVersionUID = 201901101348L;

	private final int _workID;
	private final String _title;
	private int _price;
	private int _copies;
	private int _availableCopies;
	private Category _category;

	public Work(int wID, String wTitle ,int wPrice ,int wCopies ,String cat){
		_workID = wID;
		_title = wTitle;
		_price = wPrice;
		_copies = wCopies;
		_availableCopies = wCopies;
		if(cat.equals("SCITECH")){
			_category = Category.SCITECH;
		}
		else if (cat.equals("FICTION")) {
			_category = Category.FICTION;
		}
		else if (cat.equals("REFERENCE")) {
			_category = Category.REFERENCE;
		}
		else{
			_category = null;
		}
	}

	int getWorkID(){
		return _workID;
	}

	String getTitle(){
		return _title;
	}

	int getPrice(){
		return _price;
	}

	int getCopies(){
		return _copies;
	}

	int getAvailableCopies(){
		return _availableCopies;
	}

	void addAvailableCopies(int n){
		if(n > 0){
			_availableCopies = _availableCopies + n;
		}
	}

	void removeAvailableCopies(){
		_availableCopies--;
	}

	String getWorkCategory(){
		if(_category == Category.FICTION){
			return "Ficção";
		}
		else if (_category == Category.SCITECH){
			return "Técnica e Científica";	
		}
		else if (_category == Category.REFERENCE){
			return "Referência";
		}

		return null;
	}

	abstract String getType();

	abstract String getTypeId();

	abstract String getCreator();

	public abstract String toString();
}