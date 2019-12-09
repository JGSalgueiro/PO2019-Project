package m19.core;

/** 
 * Faulty User Behaviour
 */
public class Faulty extends UserBehaviour{
	int maxRequests(){
		return 1;
	}

	String getBehaviour(){
		return "FALTOSO";
	}
}