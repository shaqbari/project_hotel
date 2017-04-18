package hotel.resv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import hotel.HotelMain;

public class Resv_Open extends JFrame{
	JPanel p_date;
	JLabel la_date;
	JPanel p_in,p_out;
	JLabel la_in,la_out;
	JTable table_in,table_out;
	
	Calendar cal=Calendar.getInstance();
	HotelMain main;
	Connection con;
	ResvModel rm;
	ResvPanel rp;
	
	//ResvModel 과 날짜가 늘 일치하여야하므로, 수정필요
	//지금은 테스트상태
	int yy;
	int mm;
	int dd;
	int col;
	
	Resv_InModel newModel_in;
	Resv_OutModel newModel_out;
	
	public Resv_Open(HotelMain main,Connection con,int col,Calendar cal){
		this.main=main;
		this.con=con;
		this.col=col;
		this.cal=cal;
		
		
		this.setLayout(new BorderLayout());
		
		yy=cal.get(Calendar.YEAR);
		mm=cal.get(Calendar.MONTH);
		dd=col;
		
		//north
		p_date = new JPanel();
		//**선택된 테이블 셀의 날짜에 맞게 ....
		la_date = new JLabel(yy+"-"+(mm+1)+"-"+dd);
		//west
		p_in = new JPanel();
		la_in = new JLabel("Check-In");
		table_in = new JTable();
		//east
		p_out = new JPanel();
		la_out = new JLabel("Check-Out");
		table_out = new JTable();
		
		p_date.setLayout(new BorderLayout());
		p_in.setLayout(new BorderLayout());
		p_out.setLayout(new BorderLayout());
		
		//table
		table_in.setPreferredSize(new Dimension(400, 400));
		table_out.setPreferredSize(new Dimension(400, 400));
		
		//north
		p_date.add(la_date,BorderLayout.NORTH);
		add(p_date,BorderLayout.NORTH);
		
		//west
		p_in.add(la_in,BorderLayout.NORTH);
		p_in.add(table_in);
		add(p_in,BorderLayout.WEST);
		
		//east
		p_out.add(la_out,BorderLayout.NORTH);
		p_out.add(table_out);
		add(p_out,BorderLayout.EAST);
		
		table_in.setModel(newModel_in = new Resv_InModel(main,con,col));
		table_in.updateUI();
		table_out.setModel(newModel_out = new Resv_OutModel(main,con,col));
		table_out.updateUI();
	
		setVisible(true);
		setSize(new Dimension(820, 400));
		
	}

}
