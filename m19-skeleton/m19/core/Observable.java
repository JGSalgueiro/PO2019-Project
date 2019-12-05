package m19.core;

import m19.core.*;

public abstract class Observable{
	abstract void addUserReq(Observer o);
	abstract void removeUserReq(Observer o);
	abstract void notificationReq();
	abstract void addUserRet(Observer o);
	abstract void removeUserRet(Observer o);
	abstract void notificationRet();
}