package hotel;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hotel.chat.ChatPanel;
import hotel.guest.MemberPanel;
import hotel.home.HomePanel;
import hotel.now.NowPanel;
import hotel.resv.ResvPanel;

public class HotelMain extends JFrame implements ActionListener{
	JPanel p_north, p_west, p_center;
	JLabel la_hotel, la_time, la_user;
	JButton bt_logout, bt_home, bt_now, bt_resv, bt_member, bt_chat;
	
	public Connection con;
	DBManager manager;
	
	JPanel[] menu=new JPanel[5];
	HomePanel p_home;
	NowPanel p_now;
	ResvPanel p_resv;
	
	MemberPanel p_member;
	ChatPanel p_chat;	
	
	MyButton[] myButtons=new MyButton[5];	
	URL[] url=new URL[5];
	
	ClockThread clock; //시계
		
	public HotelMain() {
		manager=manager.getInstance();
		con=manager.getConnection();
				 
		//System.out.println(url.toString());
		url[0]=this.getClass().getResource("/home.png");
		url[1]=this.getClass().getResource("/room.png");
		url[2]=this.getClass().getResource("/resv.png");
		url[3]=this.getClass().getResource("/membership.png");
		url[4]=this.getClass().getResource("/chat.png");
		
		p_north=new JPanel();
		p_west=new JPanel();
		p_center=new JPanel();
		
		la_hotel=new JLabel("신라호텔");
		la_time=new JLabel("2017년 4월 13일");
		la_user=new JLabel("관리자");
				
		bt_logout=new JButton("로그아웃");
		bt_home=new JButton();
		bt_now=new JButton();
		bt_resv=new JButton();
		bt_member=new JButton();
		bt_chat=new JButton();
			
		myButtons[0]=new MyButton(this, bt_home, url[0], "홈");
		myButtons[1]=new MyButton(this, bt_now, url[1], "객실관리");
		myButtons[2]=new MyButton(this, bt_resv, url[2], "예약관리");
		myButtons[3]=new MyButton(this, bt_member, url[3], "고객관리");
		myButtons[4]=new MyButton(this, bt_chat, url[4], "채팅");
				
		p_home=new HomePanel(this);
		p_now=new NowPanel(this);
		p_resv=new ResvPanel(this);
		p_member=new MemberPanel(this);
		p_chat=new ChatPanel(this);
		
		clock=new ClockThread(this);
		
		menu[0]=p_home;
		menu[1]=p_now;
		menu[2]=p_resv;
		menu[3]=p_member;
		menu[4]=p_chat;
	
		p_north.setPreferredSize(new Dimension(1280, 60));
		p_west.setPreferredSize(new Dimension(180, 900));
		p_center.setPreferredSize(new Dimension(1100, 900));
			
		p_north.add(la_hotel);
		p_north.add(la_time);
		p_north.add(la_user);
		p_north.add(bt_logout);
				
		for (int i = 0; i < myButtons.length; i++) {
			p_west.add(myButtons[i]);
		}		
		
		p_center.add(p_home);
		p_center.add(p_now);
		p_center.add(p_resv);
		p_center.add(p_member);
		p_center.add(p_chat);		
		
		add(p_north, BorderLayout.NORTH);
		add(p_west, BorderLayout.WEST);
		add(p_center);
		
		bt_home.addActionListener(this);
		bt_now.addActionListener(this);
		bt_resv.addActionListener(this);
		bt_member.addActionListener(this);
		bt_chat.addActionListener(this);
				
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
	
	public void menuVisible(JPanel p){
		for (int i = 0; i < menu.length; i++) {			
			if (p==menu[i]) {
				menu[i].setVisible(true);
			}else {
				menu[i].setVisible(false);
			}
		}
		p_center.updateUI();		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=(Object)e.getSource();
		if (obj==bt_home) {			
			menuVisible(p_home);
		}else if (obj==bt_now) {
			menuVisible(p_now);
		}else if (obj==bt_resv) {
			menuVisible(p_resv);
		}else if (obj==bt_member){
			menuVisible(p_member);
		}else if (obj==bt_chat) {
			menuVisible(p_chat);
		}		
		
	}

	public static void main(String[] args) {
		new HotelMain();
	}

}
