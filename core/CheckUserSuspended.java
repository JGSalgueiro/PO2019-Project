package m19.core;
import m19.core.User;

public class CheckUserSuspended implements Rules{

	@Override
	public Boolean rule(User u, Work w){
		return !u.getIsSuspended();
	}
}