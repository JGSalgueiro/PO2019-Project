package m19.core;
import m19.core.User;
import m19.core.Work;

public class CheckAvailableCopies extends Rules{
	@Override
	public Boolean rule(User u, Work w){
		if(w.getAvailableCopies() == 0){
			return false;
		}
		return true;
	}	
}