package m19.core;

public class Dvd extends Work{
	private static final Long serialVersionUID = 201901101348L;
	private final String _director;
	private final String _igac;

	public Dvd(int wID, String wTitle ,int wPrice ,int wCopies ,String wDirector ,String wIGAC, String cat){
		super(wID, wTitle, wPrice, wCopies, cat);
		_director = wDirector;
		_igac = wIGAC;
	}

	public String getCreator(){
		return _director;
	}

	public String getTypeId(){
		return _igac;	
	}

	public String getType(){
		return "DVD";
	}

}