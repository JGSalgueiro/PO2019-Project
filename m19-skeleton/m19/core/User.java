package m19.core;
import java.util.*;
import m19.core.*;
import java.io.Serializable;

public class User implements Serializable{
	private static final Long serialVersionUID = 201901101348L;
	private final int _userID;
	private final String _name;
	private String _email;
	private UserBehaviour _behaviour;
	private boolean _isSuspended;
	private int _deliveredOnTime;
	private int _fine;

	public User(int uID, String uName, String uEmail){
		_userID = uID;
		_name = uName;
		_email = uEmail;
		_deliveredOnTime = 0;
		_isSuspended = false;
		_fine = 0;
		_behaviour = UserBehaviour.DEFAULT;
	}

	int getUserID(){
		return _userID;
	}

	String getName(){
		return _name;
	}

	String getEmail(){
		return _email;
	}

	int getFine(){
		return _fine;
	}

	/**
	* Returns the max num of Reqs possible
	**/
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

	String getUserBehaviour(){
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


	Boolean getIsSuspended(){
		return _isSuspended;
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

	public String toString(){
		if(_isSuspended == false){
			return(_userID + " - " + _name + " - " + _email + " - " + getUserBehaviour() + " - ACTIVO");
		}
		else if(_isSuspended == true){
			return(_userID + " - " + _name + " - " + _email + " - " + getUserBehaviour() + " - SUSPENSO" + " - EUR " + _fine);
		}
	return null;
	}
}