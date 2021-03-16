package business;

import java.util.Date;

public class Order {
	
	private int orderId;
	private Date date;
	private int table;
	
	public Order(int orderId, int table) {
		this.orderId = orderId;
		this.date = new Date();
		this.table = table;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}
	
	public int hashCode() {
		int hash = 0;
		hash = hash * 13 + orderId; 
		hash = hash * 13 + (int) date.getTime()/1000; 
		hash = hash * 13 + table; 
		
		return hash;
	}
	
	public boolean equals(Object obj) {
		return obj instanceof Order && this.orderId == ((Order)obj).getOrderId()
				&& this.date == ((Order)obj).getDate() && this.table == ((Order)obj).getTable();
	}
	

}
