package business;

import java.util.ArrayList;

public interface IRestaurantProcessing {
	
	/**
	 * Adauga un nou element m in lista de MenuItem
	 * @param m produsul pe care dorim sa il inseram
	 * @pre m != null
	 * @post getAllItems().size() == getAllItems().size()@pre + 1
	 * @post m@pre == getAllItems().get(size() - 1)
	 * @post  @forall k : [0 .. getAllItems().size() - 2] @
	 *        (getAllItems().get(k)@pre == getAllItems().get(k))
	 */
	void createMenuItem(MenuItem m);
	
	/**
	 * Sterge elementul m din lista de MenuItem
	 * @param m produsul pe care dorim sa il stergem
	 * @pre m != null
	 * @pre getAllItems().size() &gt; 0
	 */
	void deleteMenuItem(MenuItem m);
	
	/**
	 * Inlocuieste elementul m1 cu elementul m2 in lista de MenuItem
	 * @param m1 produsul vechi pe care il editam
	 * @param m2 produsul nou cu care inlocuim produsul vechi
	 * @pre m1 != null
	 * @pre m2 != null
	 * @pre getAllItems().size() &gt; 0
	 * @post getAllItems().size() == getAllItems().size()@pre
	 */
	void editMenuItem(MenuItem m1, MenuItem m2);
	
	/**
	 * Un nou element este adaugat in map-ul de comenzi
	 * @param o comanda pe care dorim sa o cream
	 * @param arr sirul de produse pe care le dorim in comanda
	 * @pre o != null
	 * @pre arr != null
	 * @pre !arr.isEmpty()
	 * @post getMap().size() == getMap().size()@pre + 1
	 */
	void createNewOrder(Order o, ArrayList<MenuItem> arr);
	
	/**
	 * Calculeaza pretul total al produselor din sirul trimis ca parametru
	 * @param arr produsele al caror pret total dorim sa il calculam
	 * @return se returneaza pretul total
	 * @pre arr != null
	 * @pre !arr.isEmpty()
	 * @post @nochange
	 */
	float computeOrderPrice(ArrayList<MenuItem> arr);
	
	/**
	 * Se genereaza o factura in format txt pentru comanda trimisa ca parametru
	 * @param o comanda pentru care generam factura
	 * @pre o != null
	 * @post @nochange
	 */
	void generateBill(Order o);
	
}
