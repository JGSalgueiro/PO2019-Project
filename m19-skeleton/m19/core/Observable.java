package m19.core;

/**
 * Observable Class
 */
public abstract class Observable{
	abstract void addUserReq(Observer o);
	abstract void removeUserReq();
	abstract void notificationReq();
	abstract void addUserRet(Observer o);
	abstract void removeUserRet(Observer o);
	abstract void notificationRet();
}