package m19.core;
import java.util.*;
import java.io.Serializable;

public abstract class Work implements Serializable{
	private final int _workID;
	private final String _title;
	private int _price;
	private int _copies;
	private int _availableCopies;
	private Category _category;
	private ArrayList<Request> _waitingReqs;

	public Work(int wID, String wTitle ,int wPrice ,int wCopies ,Category wCategory){
		_workID = wID;
		_title = wTitle;
		_price = wPrice;
		_copies = wCopies;
		_availableCopies = wCopies;
		_category = wCategory;
		_waitingReqs = new ArrayList<Request>();
	}

	public int getWorkID(){
		return _workID;
	}

	public String getTitle(){
		return _title;
	}

	public int getPrice(){
		return _price;
	}

	public int getCopies(){
		return _copies;
	}

	public int getAvailableCopies(){
		return _availableCopies;
	}

	public String getCategory(){
		if(_category == Category.REFERENCE){
			return "Referência";
		}
		else if(_category == Category.FICTION){
			return "Ficção";	
		}
		else if(_category == Category.SCITECH){
			return "Técnica e Científica";
		}

		return null;
	}

	void addAvailableCopies(int n){
		if(n > 0){
			_availableCopies = _availableCopies + n;
		}
	}

	void removeAvailableCopies(){
		_availableCopies--;
	}

	void reserveBook(Request req){
		_waitingReqs.add(req);
	}

	void deleteReserve(Request req){
		_waitingReqs.remove(req);
	}

	public abstract String getType();

	public abstract String getTypeId();

	public abstract String getCreator();
}