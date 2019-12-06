package m19.core;
import m19.core.User;

public class CheckPrice extends Rules{

	@Override
	public Boolean rule(User u, Work w){
		if(w.getPrice() > 25){
			return false;
		}

		return true;
	}
}