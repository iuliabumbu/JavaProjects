package business;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {

	private ArrayList<MenuItem> products = new ArrayList<MenuItem>();

	public void addProduct(MenuItem m) {
		products.add(m);
	}

	public void removeProduct(MenuItem m) {
		products.remove(m);
	}


	public ArrayList<MenuItem> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<MenuItem> products) {
		this.products = products;
	}

	public float computePrice(MenuItem m) {
		float rez = 0;
		for(MenuItem x: ((CompositeProduct)m).getProducts()) {
			if(x instanceof BaseProduct) {
				rez = rez + ((BaseProduct)x).getPret();
			}
			else if(x instanceof CompositeProduct) {
				rez = rez + this.computePrice(x);	
			}
		} 
		return rez;
	}

	public String getProductsName(MenuItem m) {
		String rez = "";
		for(MenuItem x: ((CompositeProduct)m).getProducts()) {
			if(x instanceof BaseProduct) {
				rez = rez + ((BaseProduct)x).getNume() + " (" + ((BaseProduct)x).getPret() + ") " + " * ";
			}
			else if(x instanceof CompositeProduct) {
				rez = rez + this.getProductsName(x);	
			}
		} 
		return rez;
	}

	public boolean containsProducts(MenuItem y, MenuItem m) {
		boolean rez = false;
		
		for(MenuItem x: ((CompositeProduct)y).getProducts()) {
			if(x.equals(m)) {
				return true;
			}
			else {
				 if(x instanceof CompositeProduct) {
					 rez = rez || this.containsProducts(x, m);
				}
			}
		} 
	
	return rez;
	}
	
	@Override
	public boolean equals(Object m) {
		
		if (!(m instanceof CompositeProduct)) {
			return false;
		}
		boolean rez = true;
		if(this.products.size() == ((CompositeProduct)m).products.size()) {

			for(int i = 0; i < ((CompositeProduct)m).products.size(); i++) {
				rez = rez && this.products.get(i).equals(((CompositeProduct)m).products.get(i));
				
			}
		}
		
		return rez;

	}

}
