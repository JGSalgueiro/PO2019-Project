package m19.core;
import java.util.*;
import m19.core.*;
import java.io.Serializable;

public class Request{
	private int _deadline;
	private User _user;
	private Work _work;
	private Boolean _isAtended;

	public Request(int r_deadline, User r_user, Work r_work){
		_deadline = r_deadline;
		_user = r_user;
		_work = r_work;
		_isAtended = false;
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
}