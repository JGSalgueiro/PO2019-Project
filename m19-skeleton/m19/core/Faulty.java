package m19.core;

/** 
 * Faulty User Behaviour
 */
public class Faulty extends UserBehaviour{
	@Override
	int maxRequests(){
		return 1;
	}

	@Override
	String getBehaviour(){
		return "FALTOSO";
	}

	@Override
	int atributeReturnDate(int numCopies){
		return 2;
	}
}