package m19.core;

import java.io.Serializable;

/**
 * Class that represents the Behaviour of a set User.
 */
public abstract class UserBehaviour implements Serializable{
	/** Serial number for serialization. */
	private static final Long serialVersionUID = 201901101348L;

	abstract int maxRequests();
	abstract String getBehaviour();
}