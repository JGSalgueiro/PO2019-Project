package m19.core;

/**
 * Default User Behaviour
 */
public class Default extends UserBehaviour{
	@Override
	int maxRequests(){
		return 3;
	}

	@Override
	String getBehaviour(){
		return "NORMAL";
	}

	@Override
	int atributeReturnDate(int numCopies){
		if(numCopies == 1){
			return 3;
		}
		else if(numCopies <= 5){
			return 8;
		}
		else{
			return 15;
		}
	}
}