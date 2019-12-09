package m19.core;

/**
 * Rule that checks if there are Available Copies of a set Work
 */
public class CheckAvailableCopies extends Rules{
	@Override
	public Boolean rule(User user, Work work){
		if(work.getAvailableCopies() == 0){
			return false;
		}
		return true;
	}	
}