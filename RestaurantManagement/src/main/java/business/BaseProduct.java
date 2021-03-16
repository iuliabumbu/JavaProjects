package business;

public class BaseProduct extends MenuItem{
	
	private String nume;
	private float pret;
	
	public BaseProduct(String nume, float pret) {
		super();
		this.nume = nume;
		this.pret = pret;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public float getPret() {
		return pret;
	}

	public void setPret(float pret) {
		this.pret = pret;
	}
	
	
	 public float computePrice(MenuItem m) {
		 return ((BaseProduct) m).getPret();
	 }
	 @Override
	public boolean equals(Object o) {
		  if (!(o instanceof BaseProduct)) {
		    return false;
		  }
		  BaseProduct other = (BaseProduct) o;
		  return nume.equals(other.nume) && pret == (other.pret);
		}

}
