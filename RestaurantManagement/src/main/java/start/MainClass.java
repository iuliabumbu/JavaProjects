package start;

import java.util.ArrayList;

import business.MenuItem;
import business.Restaurant;
import data.RestaurantSerializator;
import presentation.AdministratorGUI;
import presentation.ChefGUI;
import presentation.WaiterGUI;

public class MainClass {
	
public static void main(String[] args) {
	
		final Restaurant res = new Restaurant();
		AdministratorGUI a = new AdministratorGUI(res);
		a.createGUI();
		WaiterGUI w = new WaiterGUI(res);
		w.createGUI(res);
		ChefGUI chef = new ChefGUI();
		chef.createGUI();
		res.addObserver(chef);
		RestaurantSerializator.setPath(args[0]);
		ArrayList<MenuItem> sir = RestaurantSerializator.deseralization();
		res.setAllItems(sir);
}

}
