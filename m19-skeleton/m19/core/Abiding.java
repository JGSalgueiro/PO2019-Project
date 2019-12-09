package m19.core;

/** 
 * Abiding User Behaviour
 */
public class Abiding extends UserBehaviour{
	int maxRequests(){
		return 5;
	}

	String getBehaviour(){
		return "CUMPRIDOR";
	}
}