package m19.core;

/**
 * Rule that checks if User exceeds the amount of available Requests
 */
public class CheckNumMaxReqs extends Rules{
	@Override
	public Boolean rule(User user, Work work){
		if(user.getMaxRequests() - user.getNumRequests() == 0){
			return false;
		}
		return true;
	}
}

