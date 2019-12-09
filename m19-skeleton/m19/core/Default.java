package m19.core;

/**
 * Default User Behaviour
 */
public class Default extends UserBehaviour{
	int maxRequests(){
		return 3;
	}

	String getBehaviour(){
		return "NORMAL";
	}
}