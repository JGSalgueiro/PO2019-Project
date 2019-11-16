package m19.core;

import java.io.Serializable;

public class Date implements Serializable{

	private static final long serialVersionUID = 201901101348L;

	private int _currentTime;

	public Date(int d){
		_currentTime = d;
	}

	public int getTime(){
		return _currentTime;
	}

	public void advanceTime(int dateDif){
		_currentTime += dateDif;
	}
}
