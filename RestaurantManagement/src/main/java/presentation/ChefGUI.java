package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;
import business.Restaurant;

public class ChefGUI extends JPanel implements Observer{
	  JTextArea text = new JTextArea();

	public void update(Observable o, Object arg) {
		text.append((String) arg);
	}
	
	public void createGUI() {

		JFrame frame = new JFrame ("Chef:");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 300);
		JPanel panel = new JPanel(); 
		JLabel l = new JLabel ("The chef is notified about:");
		panel.add(l);
		panel.add(text);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		frame.setContentPane(panel);
		frame.setVisible(true); 

	}
}



