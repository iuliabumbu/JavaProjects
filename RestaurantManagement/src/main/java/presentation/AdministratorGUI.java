package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
import business.Restaurant;
import data.RestaurantSerializator;


public class AdministratorGUI extends JPanel  {
	private Restaurant res;

	public AdministratorGUI(Restaurant res) {
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

	public void createGUI() {

		JFrame frame = new JFrame ("Administrator:");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 300);

		frame.addWindowListener(new WindowListener(){
			public void windowOpened(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {}

			public void windowClosed(WindowEvent e) {
				System.out.println("Window Closed!");
				RestaurantSerializator.serialization(res.getAllItems());
			}

			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}	
		});

		JPanel panel1 = new JPanel(); 
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		JLabel l = new JLabel ("Tipul produsului: ");
		final JTextField tf = new JTextField("?Produs?");
		JButton b1 = new JButton("Add MenuItem");
		JLabel l2 = new JLabel ("Tipul produsului: ");
		final JTextField tf2 = new JTextField("?Produs?");
		JButton b2 = new JButton("Delete MenuItem");
		JLabel l3 = new JLabel ("Tipul produselor: ");
		final JTextField tf3 = new JTextField("?Produs vechi?");
		final JTextField tff3 = new JTextField("?Produs nou?");
		JButton b3 = new JButton("Edit MenuItem");
		JButton b4 = new JButton("View All Menu");

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
	
		b1.addActionListener  (new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{  
				String sir=tf.getText();
				scrollPane.setVisible(false);
				MenuItem nou = parse(sir);
	
				if(!(res.getAllItems().contains(nou))) {
					if(nou instanceof BaseProduct) {
						res.createMenuItem(nou);
					}
					else if(nou instanceof CompositeProduct){
						boolean ok = true;
						for(MenuItem x: ((CompositeProduct) nou).getProducts()) {
							ok = ok && res.getAllItems().contains(x);
						}

						if(ok == true) {
							res.createMenuItem(nou);		
						}
						else {
							JOptionPane.showMessageDialog(null, "Produsul compus introdus nu contine doar produse existente in meniu!");
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Produsul exista deja!");
				}
			};	
		});


		b2.addActionListener  (new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{ 
				String sir=tf2.getText();
				scrollPane.setVisible(false);
				res.deleteMenuItem(parse(sir));

			};	
		});

		b3.addActionListener  (new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{ 
				String sir1=tf3.getText();
				String sir2=tff3.getText();
				scrollPane.setVisible(false);
				MenuItem vechi = parse(sir1);
				MenuItem nou = parse(sir2);
				if(res.getAllItems().contains(vechi)) {
					if(!(res.getAllItems().contains(nou))) {		
						if(nou instanceof BaseProduct) res.editMenuItem(vechi, nou);	
						else if(nou instanceof CompositeProduct){
							boolean ok = true;
							for(MenuItem x: ((CompositeProduct) nou).getProducts()) {
								ok = ok && res.getAllItems().contains(x);
							}
							if(ok == true) {
								res.editMenuItem(vechi, nou);		
							}
							else JOptionPane.showMessageDialog(null, "Produsul nou compus introdus nu contine doar produse existente in meniu!");
						}
					}
					else JOptionPane.showMessageDialog(null, "Produsul nou exista deja in meniu!");
				}
				else JOptionPane.showMessageDialog(null, "Produsul pe care doriti sa il modificati nu exista in meniu!");
			};	
		});

		b4.addActionListener  (new ActionListener(){
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
						sirProduse =((BaseProduct)x).getNume() + " (" + ((BaseProduct)x).getPret() + ") ";
						model.addRow(new Object[] {sirProduse, pret +"" });		
					}
					else if(x instanceof CompositeProduct) {
						sirProduse = ((CompositeProduct)x).getProductsName(x);
						model.addRow(new Object[] {sirProduse, pret +"" });		
					}				
				}
			};
		});

		panel1.add(l);
		panel1.add(tf);
		panel1.add(b1);
		panel2.add(l2);
		panel2.add(tf2);
		panel2.add(b2);
		panel3.add(l3);
		panel3.add(tf3);
		panel3.add(tff3);
		panel3.add(b3);
		panel4.add(b4);
		panel4.add(scrollPane);  
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
