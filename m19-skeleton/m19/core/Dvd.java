package m19.core;

import java.io.Serializable;

/**
 * Class that represents a DVD.
 */
public class Dvd extends Work{
	/** Serial number for serialization. */
	private static final Long serialVersionUID = 201901101348L;

	private final String _director;
	private final String _igac;

	public Dvd(int wID, String wTitle ,int wPrice ,int wCopies ,String wDirector ,String wIGAC, String cat){
		super(wID, wTitle, wPrice, wCopies, cat);
		_director = wDirector;
		_igac = wIGAC;
	}

	String getCreator(){
		return _director;
	}

	String getTypeId(){
		return _igac;	
	}

	String getType(){
		return "DVD";
	}

	public String toString(){
      	return (getWorkID() + " - " + getAvailableCopies()+ " de " + getCopies() + " - " + getType() + " - " + getTitle() + " - " + getPrice() + " - " + getWorkCategory() + " - "+ getCreator() + " - " + getTypeId());
	}

}