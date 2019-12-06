package m19.core;
import m19.core.User;

public class CheckReferenceWork extends Rules{

	@Override
	public Boolean rule(User u, Work w){
		if(w.getWorkCategory().equals("Referência")){
			return false;
		}

		return true;
	}
}