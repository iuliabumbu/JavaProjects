package business;

import java.io.Serializable;

public abstract class MenuItem  implements Serializable{

	private static final long serialVersionUID = 1L;

	public abstract float computePrice(MenuItem m);
	
	public  boolean equals(Object obj) {
		return false;
	}

}
