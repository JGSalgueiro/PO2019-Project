package m19.core;

/**
 * Rule that checks if a User trys to Request the same Work twice
 */
public class CheckSameReqTwice extends Rules{
	@Override
	public Boolean rule(User user, Work work){
		return !user.workIsRequested(work.getWorkID());
	}
}