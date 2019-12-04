package m19.core;

public class Faulty extends UserBehaviour{
	int maxRequests(){
		return 1;
	}

	String getBehaviour(){
		return "FALTOSO";
	}
}