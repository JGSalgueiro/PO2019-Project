package m19.core;
import java.util.*;
import m19.core.*;
import java.io.Serializable;

/**
 * Class that represents a User of the Library.
 */
public class User implements Serializable{
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

	public User(int uID, String uName, String uEmail){
		_userID = uID;
		_name = uName;
		_email = uEmail;
		_deliveredOnTime = 0;
		_isSuspended = false;  
		_fine = 0;
		_behaviour = new Default();
		_requests = new ArrayList<Request>();
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

	String getBehaviour(){
		return _behaviour.getBehaviour();
	}

	void workDeliveredOnTime(){
		_deliveredOnTime++;
	}

	void payFine(){
		_fine = 0;
	}

	Boolean getIsSuspended(){
		return _isSuspended;
	}

	void suspendUser(){
		_isSuspended = true;
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


}