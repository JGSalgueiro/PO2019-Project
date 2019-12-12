package m19.core;

/**
 * Rule that states a User cant Request a Work with Price above 25 EUROS
 */
public class CheckPrice extends Rules{
	@Override
	public Boolean rule(User u, Work w){
		if(w.getPrice() > 25 && !u.getBehaviour().equals("CUMPRIDOR")){
			return false;
		}

		return true;
	}
}