package m19.core;

/**
 * Rule that states a Reference Work cant be Requested by a User
 */
public class CheckReferenceWork extends Rules{
	@Override
	public Boolean rule(User user, Work work){
		if(work.getWorkCategory().equals("Referência")){
			return false;
		}
		return true;
	}
}