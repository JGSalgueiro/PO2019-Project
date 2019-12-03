package m19.core;
import java.util.*;
import m19.core.*;
import java.io.Serializable;

public class Request{
	private int _reqId;
	private int _deadline;
	private int _userId;
	private int _workId;

	public Request(int reqId, int r_deadline, int r_userId, int r_workId){
		_reqId = reqId;
		_deadline = r_deadline;
		_userId = r_userId;
		_workId = r_workId;
	}

	int getUserId(){
		return _userId;
	}

	int getWorkId(){
		return _workId;
	}

	int getDeadline(){
		return _deadline;
	}
}