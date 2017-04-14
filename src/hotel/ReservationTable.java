package hotel;

import java.awt.Color;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReservationTable extends JFrame{
	JTable table;
	Connection con;
	ReservationGuestTable guestTable;
	JScrollPane scroll;
	int index;
	public ReservationTable(Connection con,int index) {
		this.con=con;
		this.index=index;
		table=new JTable(guestTable=new ReservationGuestTable(con,index));
		scroll=new JScrollPane(table);
		add(scroll);
		setSize(1100,200);
		setBackground(Color.YELLOW);
	}
}
