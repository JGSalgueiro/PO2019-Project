package m19.core;
public class Request{
	private final int _reqID;
	private int _deliveryDate;
	private final int _requestedWorkID;
	private final int _userIDWaiting;
	private Boolean _isAtended;

	public Request(int reqID,int reqWorkId, int uIDwait){
		_reqID = reqID;
		_deliveryDate = 0;
		_requestedWorkID = reqWorkId;
		_userIDWaiting = uIDwait;
		_isAtended = false;
	}

	int getReqID(){
		return _reqID;
	}

	int getDeliveryDate(){
		return _deliveryDate;
	}

	int getRequestedWorkID(){
		return _requestedWorkID;
	}

	int getUserWaiting(){
		return _userIDWaiting;
	}

	Boolean getIsAtended(){
		return _isAtended;
	}

	void attendRequest(int dDate){
		_deliveryDate = dDate;
		_isAtended = true;
	}
}