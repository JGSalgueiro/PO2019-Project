package m19.core;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Abstract Class that represents a Generic Work.
 */
public abstract class Work extends Observable implements Serializable{
	/** Serial number for serialization. */
	private static final Long serialVersionUID = 201901101348L;

	private final int _workID;
	private final String _title;
	private int _price;
	private int _copies;
	private int _availableCopies;
	private Category _category;
	private List<Observer> _reqUsers;
  	private List<Observer> _retUsers;

  	/**
  	 * Constructor of the Work Class
  	 */
	public Work(int wID, String wTitle ,int wPrice ,int wCopies ,String cat){
		_workID = wID;
		_title = wTitle;
		_price = wPrice;
		_copies = wCopies;
		_availableCopies = wCopies;
		_reqUsers = new ArrayList<Observer>();
		_retUsers = new ArrayList<Observer>();
		if("SCITECH".equals(cat)){
			_category = Category.SCITECH;
		}
		else if ("FICTION".equals(cat)) {
			_category = Category.FICTION;
		}
		else if ("REFERENCE".equals(cat)) {
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

	void incrementAvailableCopies(){
		_availableCopies++;
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

	void addUserReq(Observer o){
		_reqUsers.add(o);
	}
	
	void removeUserReq(){
		_reqUsers.clear();
	}
	
	/**
	 * Updates the Delivery Notifications
	 */
	void notificationReq(){
		for (Observer o : _reqUsers) {
			o.update(new Notification(("ENTREGA: " + toString())));
		}
	}
	
	void addUserRet(Observer o){
		_retUsers.add(o);
	}
	
	void removeUserRet(Observer o){
		_retUsers.remove(o);
	}
	
	/**
	 * Updates the Requesitions Notifications
	 */
	void notificationRet(){
		for (Observer o : _retUsers) {
			o.update(new Notification(("REQUISIÇÃO: " + toString())));
		}
	}
	
	abstract String getType();

	abstract String getTypeId();

	abstract String getCreator();

	public abstract String toString();
}