package hotel.guest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReservationTable extends JFrame{
	JPanel p_up,p_down,p_upup,p_downdown;
	JTable table_up,table_down;
	Connection con;
	ReservationGuestTable guestreservationTable;
	ReservationMemberTable membereservationTable;
	ServiceGuestTable serviceGuestTable;
	ServiceMemberTable serviceMemberTable;
	JScrollPane scroll_up,scroll_down;
	boolean flag;
	String value;
	JLabel la_upup,la_downdown;
	public ReservationTable(Connection con,String value,boolean flag) {
		this.con=con;
		this.value=value;
		this.flag=flag;
		p_up=new JPanel();
		p_upup=new JPanel();
		p_down=new JPanel();
		p_downdown=new JPanel();
		la_upup=new JLabel("예약현황");
		la_downdown=new JLabel("서비스현황");
		if(flag==false){
			//table_up.setName("예약현황");
			//table_down.setName("서비스현황");
			table_up=new JTable(guestreservationTable=new ReservationGuestTable(con,value));
			table_down=new JTable(serviceGuestTable=new ServiceGuestTable(con, value));
		}else if(flag==true){
			//table_up.setName("예약현황");
			//table_down.setName("서비스현황");
			table_up=new JTable(membereservationTable=new ReservationMemberTable(con,value));
			table_down=new JTable(serviceMemberTable= new ServiceMemberTable(con, value));
		}
		p_upup.add(la_upup);
		p_downdown.add(la_downdown);
		scroll_up=new JScrollPane(table_up);
		scroll_down=new JScrollPane(table_down);
		setLayout(new GridLayout(4, 1));
		add(p_upup);
		add(p_up);
		add(p_downdown);
		add(p_down);
		p_up.setLayout(new BorderLayout());
		p_down.setLayout(new BorderLayout());
		p_up.add(scroll_up);
		p_down.add(scroll_down);
		setSize(1100,300);
		setBackground(Color.YELLOW);
	}
}
