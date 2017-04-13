package hotel;


import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class HotelMain extends JFrame implements ActionListener{
	JPanel p_north, p_west, p_center;
	JLabel la_hotel, la_time, la_user;
	JButton bt_logout, bt_home, bt_now, bt_resv, bt_member, bt_chat;
	
	Connection con;
	DBManager manager;
	
	
	public HotelMain() {
		manager=manager.getInstance();
		con=manager.getConnection();
		
		p_north=new JPanel();
		p_west=new JPanel();
		p_center=new JPanel();
		
		la_hotel=new JLabel("신라호텔");
		la_time=new JLabel("2017년 4월 13일");
		la_user=new JLabel("관리자");
		
		
		bt_logout=new JButton("로그아웃");
		bt_home=new JButton("홈");
		bt_now=new JButton("객실관리");
		bt_resv=new JButton("예약관리");
		bt_member=new JButton("회원관리");
		bt_chat=new JButton("채팅");
				
		p_north.setBackground(Color.YELLOW);
		p_west.setBackground(Color.ORANGE);
		p_center.setBackground(Color.GREEN);
		
		p_west.setPreferredSize(new Dimension(150, 960));
		
		p_north.add(la_hotel);
		p_north.add(la_time);
		p_north.add(la_user);
		p_north.add(bt_logout);
		
		p_west.add(bt_home);
		p_west.add(bt_now);
		p_west.add(bt_resv);
		p_west.add(bt_member);
		p_west.add(bt_chat);
		
		add(p_north, BorderLayout.NORTH);
		add(p_west, BorderLayout.WEST);
		add(p_center);
		
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent arg0) {
			manager.disConnect(con);
			System.exit(0);
		}
		
		});
		
		setSize(1280, 960);
		setLocationRelativeTo(null);
		setVisible(true);	
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=(Object)e.getSource();
		if (obj==bt_home) {
			
		}else if (obj==bt_now) {
			
		}else if (obj==bt_resv) {
			
		}else if (obj==bt_member){
			
		}else if (obj==bt_chat) {
			
		}
	}

	public static void main(String[] args) {
		new HotelMain();
	}

	
}
