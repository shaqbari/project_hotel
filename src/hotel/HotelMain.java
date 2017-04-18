package hotel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import hotel.main.CheckAdminPanel;
import hotel.main.ClockThread;
import hotel.main.DBManager;
import hotel.main.MyButton;
import hotel.main.RegAdminPanel;
import hotel.now.NowPanel;
import hotel.resv.ResvPanel;

public class HotelMain extends JFrame implements ActionListener{
	DBManager manager;
	public Connection con;
	CheckAdminPanel checkAdminPanel; //로그인패널
	RegAdminPanel regAdminPanel;	//관리자 등록패널
	JPanel p_container;
	JPanel[] page=new JPanel[3];
	
	public JPanel p_north; //p_conatainerdp 에 담길 패널들	
	JPanel p_west;
	JPanel p_center;
	JPanel p_north_west, p_north_east;//p_north에 담길 패널들
	JLabel la_hotel; //p_north에 담길 label
	public JLabel la_time;
	JLabel la_admin;
	public JLabel la_user;
	Font font_north, font_content;
	JButton bt_logout;	
	JButton bt_home, bt_now, bt_resv, bt_member, bt_chat; //p_west에 담길 button
	JButton[] buttons=new JButton[5];
	
	String[][] imgName={
			{"home.png", "홈"},
			{"room.png", "객실관리"},
			{"resv.png", "예약관리"},
			{"membership.png", "고객관리"},
			{"chat.png", "채팅"}
	};//res폴더에서 사용할 이미지		
	public MyButton[] myButtons=new MyButton[imgName.length];
	
	
	HomePanel p_home;
	NowPanel p_now;
	ResvPanel p_resv;	
	MemberPanel p_member;
	ChatPanel p_chat;	
	JPanel[] p_menus=new JPanel[5];	
	
	ClockThread clock; //시계
		
	public HotelMain() {
		manager=manager.getInstance();
		con=manager.getConnection();
				 
		page[0]=checkAdminPanel=new CheckAdminPanel(this);//로그인정보 확인 패널
		page[1]=regAdminPanel=new RegAdminPanel(this);//로그인정보 확인 패널
		page[2]=p_container=new JPanel();//아래 패널을 담을 패널
		p_north=new JPanel();
		p_west=new JPanel();
		p_center=new JPanel();		
		p_north_west=new JPanel();
		p_north_east=new JPanel();
		
		//p_north에 붙일 예정
		la_hotel=new JLabel("4조호텔                ");
		la_time=new JLabel();
		la_admin=new JLabel("ADMINISTRATOR: ");
		la_user=new JLabel("관리자");
		font_north=new Font("맑은 고딕", Font.BOLD, 30);
		font_content=new Font("맑은고딕", Font.PLAIN, 20);
		bt_logout=new JButton("LOGOUT");
		
		//여기서부터는 MyButton에 담겨 p_west에 붙일예정, 관리하기 쉽게 배열에 담는다.
		buttons[0]=bt_home=new JButton();
		buttons[1]=bt_now=new JButton();
		buttons[2]=bt_resv=new JButton();
		buttons[3]=bt_member=new JButton();
		buttons[4]=bt_chat=new JButton();
		
		//mybuttons생성
		for (int i = 0; i < imgName.length; i++) {
			URL url=this.getClass().getResource("/"+imgName[i][0]); //앞에 /가 있어야 한다.
			myButtons[i]=new MyButton(this, buttons[i], url, imgName[i][1]);
		}		
		myButtons[0].flag=false;//처음에 home버튼 선택되어있게 초기화
		myButtons[0].setBackground(Color.LIGHT_GRAY);		
				
		//메뉴별로 패널을 나눈다. 나중에 관리하기쉽게 배열에 담는다.
		p_menus[0]=p_home=new HomePanel(this);
		p_menus[1]=p_now=new NowPanel(this);
		p_menus[2]=p_resv=new ResvPanel(this);
		p_menus[3]=p_member=new MemberPanel(this);
		p_menus[4]=p_chat=new ChatPanel(this);
		
		clock=new ClockThread(this); //시계생성
		
		//레이아웃조정
		this.setLayout(new FlowLayout()); //pack이용하게 flowlayout으로
		p_container.setLayout(new BorderLayout());
		p_north.setLayout(new GridLayout(1, 2));
		
		//size조정
		p_container.setPreferredSize(new Dimension(1280, 960));
		p_north.setPreferredSize(new Dimension(1280, 60));
		p_west.setPreferredSize(new Dimension(180, 900));
		p_center.setPreferredSize(new Dimension(1100, 900));
		
		la_hotel.setFont(font_north);
		la_time.setFont(font_content);
		la_admin.setFont(font_content);
		la_user.setFont(font_content);
		bt_logout.setFont(font_content);
		
		p_north_west.setAlignmentX(LEFT_ALIGNMENT);
		la_hotel.setAlignmentX(LEFT_ALIGNMENT);
		
		p_north_west.add(la_hotel);
		p_north_west.add(la_time);
		p_north_east.add(la_admin);
		p_north_east.add(la_user);
		p_north_east.add(bt_logout);
		
		p_north.add(p_north_west);		
		p_north.add(p_north_east);		
		
		for (int i = 0; i < myButtons.length; i++) {
			p_west.add(myButtons[i]);
			p_center.add(p_menus[i]);
		}			
		
		p_container.add(p_north, BorderLayout.NORTH);
		p_container.add(p_west, BorderLayout.WEST);
		p_container.add(p_center);
		
		add(checkAdminPanel);
		add(regAdminPanel);
		add(p_container);
		setPage(0);//처음실행했을때는 로그인패널보이게 한다.
		
		//리스너와의 연결
		bt_logout.addActionListener(this);
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
		
		//setSize(1280, 960); //pack()을 이용할 예정이므로 여기서사이즈 지정하면 안된다.
		setLocationRelativeTo(null);
		setVisible(true);	
		
	}
	
	//인자에 따라 로그인페이지와 메인페이지 교체
	public void setPage(int index){
		for (int i = 0; i < page.length; i++) {
			if (i==index) {
				page[i].setVisible(true);
			}else{
				page[i].setVisible(false);
			}			
		}
		this.pack();//내용물의 크기만큼 윈도우 크기를 설정, 하나의 윈도우가 여러가지 모습을 보일수 있다.
		this.setLocationRelativeTo(null); //화면중앙에 배치, 여기서 또 안하면 원래있던데서 크기 바뀐다.
	}
	
	//메뉴선택에 따라 패널을 보여주는 메소드
	public void menuVisible(JPanel p){
		for (int i = 0; i < p_menus.length; i++) {			
			if (p==p_menus[i]) {
				p_menus[i].setVisible(true);
			}else {
				p_menus[i].setVisible(false);
			}
		}
		p_center.updateUI();		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=(Object)e.getSource();
		
		//로그아웃버튼 누르면 checkAdminPanel보이게 설정
		if (obj==bt_logout) {
			setPage(0);
		}
		
		if (obj==bt_home) {			
			menuVisible(p_home);
		}else if (obj==bt_now) {
			menuVisible(p_now);
		}else if (obj==bt_resv) {
			menuVisible(p_resv);
			p_resv.setMonth();
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
