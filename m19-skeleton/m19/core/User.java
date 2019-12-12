package m19.core;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Class that represents a User of the Library.
 */
public class User implements Serializable, Observer{
	/** Serial number for serialization. */
	private static final Long serialVersionUID = 201901101348L;

	private final int _userID;
	private final String _name;
	private String _email;
	private UserBehaviour _behaviour;
	private boolean _isSuspended;
	private int _deliveredOnTime;
	private int _fine;
	private List<Request> _requests;
	private List<Notification> _notification;

	public User(int uID, String uName, String uEmail){
		_userID = uID;
		_name = uName;
		_email = uEmail;
		_deliveredOnTime = 0;
		_isSuspended = false;  
		_fine = 0;
		_behaviour = new Default();
		_requests = new ArrayList<Request>();
		_notification = new ArrayList<Notification>();
	}

	public int getUserID(){
		return _userID;
	}

	public String getName(){
		return _name;
	}

	String getEmail(){
		return _email;
	}

	int getFine(){
		return _fine;
	}

	int getNumRequests(){
		return _requests.size();
	}

	int getMaxRequests(){
		return _behaviour.maxRequests();
	}

	void deleteRequests(Request r){
		_requests.remove(r);
	}

	String getBehaviour(){
		return _behaviour.getBehaviour();
	}

	void workDeliveredOnTime(){
		if(_deliveredOnTime < 0){
			_deliveredOnTime = 1;
		}
		else{
			_deliveredOnTime++;
		}
	}

	void workNotDeliveredOnTime(){
		if(_deliveredOnTime > 0){
			_deliveredOnTime = -1;
		}
		else{
			_deliveredOnTime--;
		}
	}



	/**
	 * Updates the User Behaviour acording to its last delivers
	 */
	void checkStreak(){
		if(_deliveredOnTime == -3){
			_behaviour = new Faulty();
		}
		if(_deliveredOnTime == 5){
			_behaviour = new Abiding();
		}
		if(_behaviour.getBehaviour().equalsIgnoreCase("CUMPRIDOR")){
			if(_deliveredOnTime == -1){
				_behaviour = new Default();
			}
		}
		if(_deliveredOnTime == 3){
			_behaviour = new Default();
		}
	}

	void pay(int payedAmount){
		_fine = _fine - payedAmount;
	}

	void payAll(){
		_fine = 0;
	}

	void setFine(int f){
		_fine += f;
	}

	Boolean getIsSuspended(){
		return _isSuspended;
	}

	void setSuspension(Boolean b){
		_isSuspended = b;
	}

	public String toString(){
		if(!_isSuspended){
			return(_userID + " - " + _name + " - " + _email + " - " + _behaviour.getBehaviour() + " - ACTIVO");
		}
		else{
			return(_userID + " - " + _name + " - " + _email + " - " + _behaviour.getBehaviour() + " - SUSPENSO" + " - EUR " + _fine);
		}
	}

	void makeRequest(Request req){
		_requests.add(req);
	}

	void removeRequest(Request req){
		_requests.remove(req);
	}

	/**
	 * Checks if set work is requested
	 */
	Boolean workIsRequested(int wId){
		for(Request r : _requests){
			if(r.getWork().getWorkID() == wId){
				return true;
			}
		}
		return false;
	}

	int checkPassedDeadline(){
		int i = 0;
		for(Request r : _requests){
			if(r.getIsFaulty()){
				i++;
			}
		}
		return i;
	}

	/**
	 * Acording to the works number of Copies atributes a return Date
	 */
	int atributeReturnDate(int numCopies){
		return _behaviour.atributeReturnDate(numCopies);
	}

	@Override
	public void update(Notification n){
		_notification.add(n);
	}

	void clearNotifications(){
		_notification.clear();
	}

	List<Notification> getNotification(){
		return _notification;
	}
}