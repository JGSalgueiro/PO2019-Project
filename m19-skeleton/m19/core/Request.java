package m19.core;
import java.util.*;
import m19.core.*;
import java.io.Serializable;

public class Request{
	private int _deadline;
	private User _user;
	private Work _work;
	private Boolean _isFaulty;

	public Request(int r_deadline, User r_user, Work r_work){
		_deadline = r_deadline;
		_user = r_user;
		_work = r_work;
		
		_isFaulty = false;
	}

	User getUser(){
		return _user;
	}

	Work getWork(){
		return _work;
	}

	int getDeadline(){
		return _deadline;
	}

	Boolean getIsFaulty(){
		return _isFaulty;
	}

	void updateReq(int date){
		if(_deadline < date){
			_user.setSuspension(true);
			_isFaulty = true;
			_user.setFine((date - _deadline)*5);
		}
	}
}