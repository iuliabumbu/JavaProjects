package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;
import business.Restaurant;



public class WaiterGUI extends JPanel  {
	private Restaurant res;
	private int idCount = 1;
	
	public WaiterGUI(Restaurant res) {
		super();
		this.res = res;
	}

	static MenuItem parse(String sir) {
		MenuItem rez = null;
		if(!sir.contains(";") && !sir.contains("/") && sir.contains(",")){
			String part[] = sir.split(",");
			rez = new BaseProduct(part[0], Float.valueOf(part[1]));
		} else if(sir.contains(";") && !sir.contains("/") && sir.contains(",")) {
			String parts[] = sir.split(";");
			CompositeProduct c = new CompositeProduct();
			for(String s : parts) {
				String part[] = s.split(",");
				c.addProduct(new BaseProduct(part[0], Float.valueOf(part[1])));	}
			rez = c;
		} else if(!sir.contains(";") && sir.contains("/") && sir.contains(",")) {
			String parts[] = sir.split("/");
			CompositeProduct c = new CompositeProduct();
			for(String s : parts) {
				String part[] = s.split(",");
				c.addProduct(new BaseProduct(part[0], Float.valueOf(part[1])));	}
			rez = c;
		} else if(sir.contains(";") && sir.contains("/") && sir.contains(",")) {
			String parts[] = sir.split("/");
			CompositeProduct comp = new CompositeProduct();
			for(String s : parts) {
				if(!s.contains(";")) {
					String part[] = s.split(",");
					comp.addProduct(new BaseProduct(part[0], Float.valueOf(part[1])));}
				else {
					String part[] = s.split(";");
					CompositeProduct c = new CompositeProduct();
					for(String s1 : part) {
						String p[] = s1.split(",");
						c.addProduct(new BaseProduct(p[0], Float.valueOf(p[1])));}
					comp.addProduct(c);	}	}
			rez = comp;	}	
		return rez;	
	}

	public void createGUI(final Restaurant res) {

		JFrame frame = new JFrame ("Waiter:");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 300);

		JPanel panel1 = new JPanel(); 
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		JLabel l = new JLabel ("Table Number: ");
		final JTextField tf = new JTextField("?");
		JButton b1 = new JButton("Add Order");
		JButton bt1 = new JButton("Refresh Menu");
		JLabel l1 = new JLabel ("Selectati produsele dorite din meniu folosind CTRL+click: ");
		JLabel l2 = new JLabel ("Id Order pentru care se face factura:");
		final JTextField tf2 = new JTextField("?");
		JButton bt2 = new JButton("Create Bill");
		JButton bt3 = new JButton("View All Orders");
		
		final DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		}; 
		
		model.addColumn("Product");
		model.addColumn("Price");
		final JTable table = new JTable(model); 
		final JScrollPane scrollPane = new JScrollPane(table);
		
		final DefaultTableModel model3 = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		}; 
		model3.addColumn("IdOrder");
		model3.addColumn("Date");
		model3.addColumn("Table");
		model3.addColumn("Products");
		model3.addColumn("Total");
		final JTable table3 = new JTable(model3); 
		final JScrollPane scrollPane3 = new JScrollPane(table3);
		
		b1.addActionListener  (new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{  
			String masa=tf.getText();
			Order o = new Order(idCount, Integer.valueOf(masa));
			idCount++;
			int[] selected = table.getSelectedRows();
			ArrayList<MenuItem> sir = new ArrayList<MenuItem>();
	
			
			for(int i = 0; i < selected.length; i++) {
				sir.add(res.getAllItems().get((selected[i])));
			}
			
			res.createNewOrder(o, sir);		
			
			};	
		});
		
		bt1.addActionListener  (new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				scrollPane.setVisible(true);
				for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
					model.removeRow(i);
				}
				String sirProduse ="";
				float pret = 0;
				for(MenuItem x: res.getAllItems()) {

					pret = x.computePrice(x);
					if(x instanceof BaseProduct) {
						sirProduse =((BaseProduct)x).getNume();
						model.addRow(new Object[] {sirProduse, pret +"" });		
					}
					else if(x instanceof CompositeProduct) {

						sirProduse = ((CompositeProduct)x).getProductsName(x);
						model.addRow(new Object[] {sirProduse, pret +"" });		
					}				

				}

			};

		});
		
		bt2.addActionListener  (new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{ 
			String idO=tf2.getText();
			Order o = null;
			ArrayList<MenuItem> produse = null;
			int ok = 0;
			
			Map<Order, ArrayList<MenuItem>> allOrders = res.getMap();
			for(Map.Entry<Order, ArrayList<MenuItem>> x : allOrders.entrySet()) {
				int id = x.getKey().getOrderId();
				if(id == Integer.parseInt(idO)) {
					o = x.getKey();
					res.generateBill(o);
					ok = 1;
					break;		
					
				}	
			}
			
			if(ok == 0) {
				JOptionPane.showMessageDialog(null, "Comanda cu id-ul introdus nu exista!");
			}
			
			
			};	
		});
		
		bt3.addActionListener  (new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				scrollPane3.setVisible(true);
				for( int i = model3.getRowCount() - 1; i >= 0; i-- ) {
					model3.removeRow(i);
				}
				
				Map<Order, ArrayList<MenuItem>> allOrders = res.getMap();
				SimpleDateFormat ft =   new SimpleDateFormat ("E yyyy.MM.dd, hh:mm a");
				
				for(Map.Entry<Order, ArrayList<MenuItem>> x : allOrders.entrySet()) {
					int id = x.getKey().getOrderId();
					String data = ft.format(x.getKey().getDate())+"";
					int table = x.getKey().getTable();
					String sirProduse ="";
					float total = res.computeOrderPrice(x.getValue());
					
					for(MenuItem m: x.getValue()) {
						if(m instanceof BaseProduct) {
							sirProduse = sirProduse + " * " + ((BaseProduct)m).getNume() + " (" + ((BaseProduct)m).getPret() + ") ";
						}
						else if(m instanceof CompositeProduct) {
							sirProduse = sirProduse + " * " + ((CompositeProduct)m).getProductsName(m);
						}				
					}			
					model3.addRow(new Object[] {id+"", data, table+"", sirProduse, total+"" });		
		
				}
			};
		});

		panel1.add(l);
		panel1.add(tf);
		panel1.add(b1);
		panel1.add(bt1);
		panel2.add(l1);
		panel2.add(scrollPane);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel3.add(l2);
		panel3.add(tf2);
		panel3.add(bt2);
		panel4.add(bt3);
		panel4.add(scrollPane3);  
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		JPanel panou = new JPanel();
		panou.add(panel1);
		panou.add(panel2);
		panou.add(panel3);
		panou.add(panel4);
		panou.setLayout(new BoxLayout(panou, BoxLayout.Y_AXIS));
		frame.setContentPane(panou);
		frame.setVisible(true); 


	}
}
