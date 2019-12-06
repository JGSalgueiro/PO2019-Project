package m19.core;
import m19.core.User;

public class CheckNumMaxReqs extends Rules{

	@Override
	public Boolean rule(User u, Work w){
		if(u.getMaxRequests() - u.getNumRequests() == 0){
			return false;
		}

		return true;
	}
}

