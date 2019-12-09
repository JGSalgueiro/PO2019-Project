package m19.core;

import java.io.Serializable;

/**
 * Class that Represents a generic Notification
 */
public class Notification implements Serializable{
	private static final Long serialVersionUID = 201901101348L;

	private String _notification;

	Notification(String n){
		_notification = n;
	}

	public String toString(){
		return _notification;
	}
}