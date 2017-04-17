package hotel.guest;

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
	ReservationMemberTable memberTable;
	JScrollPane scroll;
	boolean flag;
	String value;
	public ReservationTable(Connection con,String value,boolean flag) {
		this.con=con;
		this.value=value;
		this.flag=flag;
		if(flag==false){
			table=new JTable(guestTable=new ReservationGuestTable(con,value));
		}else if(flag==true){
			table=new JTable(memberTable=new ReservationMemberTable(con,value));
		}
		scroll=new JScrollPane(table);
		add(scroll);
		setSize(1100,200);
		setBackground(Color.YELLOW);
	}
}
