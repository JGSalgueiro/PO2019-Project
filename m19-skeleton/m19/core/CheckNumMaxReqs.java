package m19.core;
import m19.core.User;

public class CheckNumMaxReqs implements Rules{

	@Override
	public Boolean rule(User u, Work w){
		if(u.getNumRequests() - u.getMaxRequests() == 0){
			return false;
		}

		return true;
	}
}

