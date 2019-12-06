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
		_deliveredOnTime++;
	}

	void workNotDeliveredOnTime(){
		_deliveredOnTime--;
	}

	void checkStreak(){
		if(_deliveredOnTime == -3){
			_behaviour = new Faulty();
		}
		if(_deliveredOnTime == 5){
			_behaviour = new Abiding();
		}
		if(_deliveredOnTime == 3){
			_behaviour = new Default();
		}
	}

	void pay(){
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
		if(_isSuspended == false){
			return(_userID + " - " + _name + " - " + _email + " - " + _behaviour.getBehaviour() + " - ACTIVO");
		}
		else if(_isSuspended == true){
			return(_userID + " - " + _name + " - " + _email + " - " + _behaviour.getBehaviour() + " - SUSPENSO" + " - EUR " + _fine);
		}
	return null;
	}

	void makeRequest(Request req){
		_requests.add(req);
	}

	void removeRequest(Request req){
		_requests.remove(req);
	}

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

	@Override
	public void update(Notification n){
		_notification.add(n);
	}

	List<Notification> getNotification(){
		return _notification;
	}
}