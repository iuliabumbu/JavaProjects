package business;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.JOptionPane;

import data.WriteFile;

/**
 * @invariant isWellFormed()
 */
@SuppressWarnings("deprecation")
public class Restaurant extends Observable implements IRestaurantProcessing{

	private Map< Order, ArrayList<MenuItem>> map =  new HashMap< Order, ArrayList<MenuItem>>();
	private ArrayList<MenuItem> allItems = new ArrayList<MenuItem>();

	public Map<Order, ArrayList<MenuItem>> getMap() {
		return map;
	}

	public ArrayList<MenuItem> getAllItems() {
		return allItems;
	}

	public void setAllItems(ArrayList<MenuItem> allItems) {
		this.allItems = allItems;
	}

	public void createMenuItem(MenuItem m) {
		assert m != null;
		assert isWellFormed();
		int sizePre = allItems.size();

		allItems.add(m);

		int sizePost = allItems.size();
		assert sizePost == sizePre + 1;
		assert allItems.get(sizePost - 1).equals(m);
		assert isWellFormed();
	}

	public void deleteMenuItem(MenuItem m) {
		assert m != null;
		int sizePre = allItems.size();
		assert sizePre > 0;
		assert isWellFormed();
		ArrayList<MenuItem> delete = new ArrayList<MenuItem>();


		for(MenuItem y: allItems) {
			if(y instanceof BaseProduct && y.equals(m)) {
				delete.add(y);
			}
			else if(y instanceof CompositeProduct) {
				if(((CompositeProduct) y).containsProducts(y, m) || y.equals(m)) {
					delete.add(y);		
				}
			}				
		}
		allItems.removeAll(delete);

		assert isWellFormed();

	}

	public void editMenuItem(MenuItem m1, MenuItem m2) {
		assert m1 != null;
		assert m2 != null;
		int sizePre = allItems.size();
		assert sizePre > 0;
		assert isWellFormed();

		int poz = allItems.indexOf(m1);
		allItems.set(poz, m2);

		int sizePost = allItems.size();
		assert sizePost == sizePre;
		assert isWellFormed();

	}

	public void createNewOrder(Order o, ArrayList<MenuItem> arr) {
		assert o != null;
		assert arr != null;
		assert ! arr.isEmpty();
		assert isWellFormed();
		int sizePre= map.size();

		map.put(o, arr);
		String sirProduse ="";

		for(MenuItem x: arr) {

			if(x instanceof BaseProduct) {
				sirProduse = sirProduse + " " + ((BaseProduct)x).getNume();	
			}
			else if(x instanceof CompositeProduct) {
				sirProduse = sirProduse + " " + ((CompositeProduct)x).getProductsName(x);
			}				
		}
		setChanged();
		notifyObservers(sirProduse + "\n");

		int sizePost = map.size();
		assert sizePost == sizePre + 1;
		assert isWellFormed();

	}

	public float computeOrderPrice(ArrayList<MenuItem> arr) {
		assert arr != null;
		assert ! arr.isEmpty();
		assert isWellFormed();
		float rez = 0;

		for(MenuItem x: arr) {
			rez = rez + x.computePrice(x);
		}

		assert isWellFormed();

		return rez;	
	}

	public void generateBill(Order o){
		assert o != null;
		assert isWellFormed();

		ArrayList<String> bill = new ArrayList<String>();
		ArrayList<MenuItem> arr = map.get(o);
		SimpleDateFormat ft =   new SimpleDateFormat ("E yyyy.MM.dd, hh:mm a");
		bill.add("Factura:\n");
		bill.add("IdOrder: " + o.getOrderId() + "\n");
		bill.add("Data: " + ft.format(o.getDate()) + "\n");
		bill.add("Table: " + o.getTable() + "\n");

		String sirProduse ="";
		float pret = this.computeOrderPrice(arr);
		for(MenuItem x: arr) {

			if(x instanceof BaseProduct) {
				sirProduse = sirProduse + " " + ((BaseProduct)x).getNume();	
			}
			else if(x instanceof CompositeProduct) {
				sirProduse = sirProduse + " " + ((CompositeProduct)x).getProductsName(x);
			}				
		}
		
		bill.add("Produse: ");
		bill.add(sirProduse + "\n");
		bill.add("Pret Total: ");
		bill.add(pret + "\n");
		String path = "Factura" + o.getOrderId() + ".txt";
		WriteFile.scriereFisier(path, bill);

		assert isWellFormed();

	}


	protected boolean isWellFormed(){
		boolean rez = true;

		rez = rez && !(allItems.isEmpty() && ! map.isEmpty());
		rez = rez && (allItems != null);

		return rez;
	}

}
