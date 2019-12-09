package m19.core;

import java.io.Serializable;

/**
 * Class that represents the implementation of a Request Rule
 */
public abstract class Rules implements Serializable{
	/** Serial number for serialization. */
	private static final Long serialVersionUID = 201901101348L;

	abstract Boolean rule(User user, Work work);
}