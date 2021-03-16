package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;

public class RestaurantSerializator {
	private static String path;

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		RestaurantSerializator.path = path;
	}

	public static void serialization(ArrayList<MenuItem> sir) {  

		if(path != null) {
			try {
				FileOutputStream file = new FileOutputStream(path); 
				ObjectOutputStream out = new ObjectOutputStream(file); 

				for(MenuItem object : sir) {
					out.writeObject(object); 
				}
				out.close(); 
				file.close(); 
				System.out.println("Object has been serialized"); 
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	} 

	public static ArrayList<MenuItem> deseralization(){
		ArrayList<MenuItem> sir = new ArrayList<MenuItem>();
		MenuItem m = null;
		if(path != null) {
			try{    
				FileInputStream file = new FileInputStream(path);
				if(file.available() > 2) {	
					
				ObjectInputStream in = new ObjectInputStream(file); 
				while( file.available() > 0) 
				{
					m = (MenuItem)in.readObject();    
					sir.add(m);
					System.out.println("Object has been deserialized"); 

				}
				in.close(); 
				file.close(); 
				}
			} catch(IOException ex) 
			{ 
				System.out.println("IOException is caught"); 
			} 

			catch(ClassNotFoundException ex) 
			{ 
				System.out.println("ClassNotFoundException is caught"); 
			} 

		}
		return sir;  
	}

}
