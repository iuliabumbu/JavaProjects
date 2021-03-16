package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {
	
	public static  void scriereFisier( String path, ArrayList<String> sir) {
		
		 try {
		      FileWriter myWriter = new FileWriter(path);
			 for(String s : sir) {
		      myWriter.write(String.format("%s\r\n", s));
			 }
			 System.out.println("Successfully wrote to the file.");
		      myWriter.close();
		      
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }

	}


}
