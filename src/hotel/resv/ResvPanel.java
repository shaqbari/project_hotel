package hotel.resv;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import hotel.HotelMain;

public class ResvPanel extends JPanel implements ActionListener{
	String TAG=this.getClass().getName();
	HotelMain main;
	Connection con;
	JPanel p_north, p_center;
	JButton bt_prev, bt_next;
	JLabel la_date;
	JScrollPane scroll;
	JTable table;
	Calendar cal=Calendar.getInstance();
	ResvModel rm;
	Resv_Open newOpen;
	
	//현재 날짜 받아오는 변수
	int yy;
	int mm;
	int dd;
	
	public ResvPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		this.setLayout(new BorderLayout());
		p_north=new JPanel();
		p_center=new JPanel();
		bt_prev=new JButton("◀");
		la_date=new JLabel();
		bt_next=new JButton("▶");
		table=new JTable();
		scroll=new JScrollPane(table);
		
		table.setPreferredScrollableViewportSize(new Dimension(1000, 750));
		
		p_north.add(bt_prev);
		p_north.add(la_date);
		p_north.add(bt_next);
		p_center.add(scroll);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center,BorderLayout.CENTER);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//System.out.println("예약관리 달력테이블 클릭시 이벤트발생");
				newOpen = new Resv_Open(main,con);
			}
		});
		
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);
		
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);
	}
	
	public void setMonth(){
		yy=cal.get(Calendar.YEAR);
		mm=cal.get(Calendar.MONTH);
		
		la_date.setText(yy+"-"+(mm+1));
		
		//공통사항 옮겨서 부착
		table.setModel(rm=new ResvModel(con, cal));
		table.updateUI();
		p_center.updateUI();
	}
	public void prev(){
		mm--;
		if(mm<0){
			yy--;
			mm=11;
		}
		cal.set(yy, mm, 1);
		setMonth();
	}
	
	public void next(){
		mm++;
		if(mm>12){
			yy++;
			mm=1;
		}
		cal.set(yy, mm, 1);
		setMonth();
	}
	//이전 과 다음 버튼을 눌러 이전달, 다음달로 이동
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_prev){
			prev();
		}else if(obj==bt_next){
			next();
		}
	}
	
}
