package m19.core;

import java.util.*;

/** 
 *Compares the Name of 2 Users by alphabetic order
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
