package m19.core;

/** 
 * Abiding User Behaviour
 */
public class Abiding extends UserBehaviour{
	@Override
	int maxRequests(){
		return 5;
	}

	@Override
	String getBehaviour(){
		return "CUMPRIDOR";
	}

	@Override
	int atributeReturnDate(int numCopies){
		if(numCopies == 1){
			return 8;
		}
		else if(numCopies <= 5){
			return 15;
		}
		else{
			return 30;
		}
	}
}