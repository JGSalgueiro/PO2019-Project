package m19.core;

/**
 * Rule that checks if a User is Suspended
 */
public class CheckUserSuspended extends Rules{
	@Override
	public Boolean rule(User user, Work work){
		return !user.getIsSuspended();
	}
}