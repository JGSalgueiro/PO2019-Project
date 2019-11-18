package m19.core;

import java.util.*;
import m19.core.User;
/** 
 * Compares the Name of 2 Users by alphabetic order
 * In case of same Name orders by UserId
 */
public class ComparatorName implements Comparator<User>{
	public int compare(User u, User u1){
		if(u.getName().equals(u1.getName())){
			return u1.getName().compareTo(u.getName());
		}
		else{
			return u.getName().compareTo(u1.getName());
		}
	}
}
