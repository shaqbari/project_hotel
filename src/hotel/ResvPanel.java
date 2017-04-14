package hotel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ResvPanel extends JPanel implements ActionListener{
	HotelMain main;
	Connection con;
	JPanel p_north, p_center;
	JButton b_prev, b_next;
	JLabel la_date;
	JScrollPane scroll;
	JTable table;
	Calendar cal=Calendar.getInstance();
	ResvModel resvModel;
	
	//현재 날짜 받아오는 변수
	int yy;
	int mm;
	int dd;
	
	public ResvPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		yy=cal.get(cal.YEAR);
		mm=cal.get(cal.MONTH);
		dd=cal.get(cal.DAY_OF_MONTH);
		System.out.println("실제 월값은 "+mm);
		this.setLayout(new BorderLayout());
		p_north=new JPanel();
		p_center=new JPanel();
		b_prev=new JButton("◀");
		la_date=new JLabel(yy+"-"+(mm+1)+"-"+dd);
		b_next=new JButton("▶");
		table=new JTable();
		scroll=new JScrollPane(table);
		
		table.setPreferredScrollableViewportSize(new Dimension(1000, 750));
		
		p_north.add(b_prev);
		p_north.add(la_date);
		p_north.add(b_next);
		p_center.add(scroll);
		add(p_north, BorderLayout.NORTH);
		add(p_center,BorderLayout.CENTER);
		
		b_prev.addActionListener(this);
		b_next.addActionListener(this);
		
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);			
	}

	public void prev(){
		la_date.setText(yy+"-"+(mm-1)+"-"+dd);
		mm--;
		
		if(mm<0){
			mm=11;
			yy--;
		}
		p_center.updateUI();

		cal.set(yy, mm, 0);
		ResvModel rm=new ResvModel(con,cal);
		table.setModel(rm);
		table.updateUI();
		
	}
	
	public void next(){
		la_date.setText(yy+"-"+(mm+1)+"-"+dd);
		
		mm++;
		if(mm>11){
			mm=0;
			yy++;
		}
		ResvModel rm=new ResvModel(con,cal);
		p_center.updateUI();
		cal.set(yy, mm, 0);
		table.setModel(rm);
		table.updateUI();		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==b_prev){
			prev();
		}else if(obj==b_next){
			next();
		}
	}
}
