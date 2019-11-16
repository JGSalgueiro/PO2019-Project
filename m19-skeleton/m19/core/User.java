package m19.core;
import java.util.*;
import m19.core.*;


public class User{
	private final int _userID;
	private final String _name;
	private String _email;
	private UserBehaviour _behaviour;
	private int _numReqWorks;
	private ArrayList<Work> _reqWorks;
	private ArrayList<Request> _requests;
	private boolean _isSuspended;
	private int _deliveredOnTime;
	private int _fine;

	public User(int uID, String uName, String uEmail){
		_userID = uID;
		_name = uName;
		_email = uEmail;
		_numReqWorks = 0;
		_deliveredOnTime = 0;
		_isSuspended = false;
		_fine = 0;
		_behaviour = UserBehaviour.DEFAULT;
		_reqWorks = new ArrayList<Work>();	
		_requests = new ArrayList<Request>();	
	}

	public int getUserID(){
		return _userID;
	}

	public String getName(){
		return _name;
	}

	public String getEmail(){
		return _email;
	}

	public int getFine(){
		return _fine;
	}

	public int getNumReqWorks(){
		return _reqWorks.size();
	}

	int canReqNum(){
		if(_behaviour == UserBehaviour.DEFAULT){
			return 3;
		}
		else if (_behaviour == UserBehaviour.FAULTY){
			return 1;	
		}
		else if (_behaviour == UserBehaviour.ABIDING){
			return 5;
		}

		return 0;
	}

	public String getUserBehaviour(){
		if(_behaviour == UserBehaviour.DEFAULT){
			return "NORMAL";
		}
		else if (_behaviour == UserBehaviour.FAULTY){
			return "FALTOSO";	
		}
		else if (_behaviour == UserBehaviour.ABIDING){
			return "CUMPRIDOR";
		}

		return null;
	}

	public ArrayList<Work> getReqWorks(){
		return _reqWorks;
	}

	public Boolean getIsSuspended(){
		return _isSuspended;
	}

	void pickUpWork(Work w){
		_reqWorks.add(w);
	}

	void returnWork(Work w){
		_reqWorks.remove(w);
	}

	void lateReturn(int fine){
		_fine = _fine + fine;
	}

	void payFine(){
		if(_fine > 0){
			_fine = 0;
			_isSuspended = false;
		}
	}

	void suspendUser(){
		_isSuspended = true;
	}
}