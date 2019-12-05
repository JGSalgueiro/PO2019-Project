package m19.core;

import java.io.Serializable;

/**
 * Class that represents the Current Date in the running Program.
 */
public class Date implements Serializable{

	private static final long serialVersionUID = 201901101348L;

	private int _currentTime;

	public Date(){
		_currentTime = 0;
	}

	public int getTime(){
		return _currentTime;
	}

	public void advanceTime(int dateDif){
		if(dateDif > 0){
			_currentTime += dateDif;
		}
	}
}
