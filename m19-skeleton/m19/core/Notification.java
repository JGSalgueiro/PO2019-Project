package m19.core;
public class Notification{
	private final String _message;

	public Notification(String message){
		_message = message;
	}

	String getNotification(){
		return _message;
	}
}