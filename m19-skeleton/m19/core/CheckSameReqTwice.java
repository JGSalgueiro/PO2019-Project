package m19.core;
import m19.core.User;
import m19.core.Work;

public class CheckSameReqTwice extends Rules{
	@Override
	public Boolean rule(User u, Work w){
		return !u.workIsRequested(w.getWorkID());
	}
}