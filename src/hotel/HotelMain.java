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
	CheckAdminPanel checkAdminPanel; //�α����г�
	RegAdminPanel regAdminPanel;	//������ ����г�
	JPanel p_container;
	JPanel[] page=new JPanel[3];
	
	public JPanel p_north; //p_conatainerdp �� ��� �гε�	
	JPanel p_west;
	JPanel p_center;
	JPanel p_north_west, p_north_east;//p_north�� ��� �гε�
	JLabel la_hotel; //p_north�� ��� label
	public JLabel la_time;
	JLabel la_admin;
	public JLabel la_user;
	Font font_north, font_content;
	JButton bt_logout;	
	JButton bt_home, bt_now, bt_resv, bt_member, bt_chat; //p_west�� ��� button
	JButton[] buttons=new JButton[5];
	
	String[][] imgName={
			{"home.png", "Ȩ"},
			{"room.png", "���ǰ���"},
			{"resv.png", "�������"},
			{"membership.png", "������"},
			{"chat.png", "ä��"}
	};//res�������� ����� �̹���		
	public MyButton[] myButtons=new MyButton[imgName.length];
	
	
	HomePanel p_home;
	NowPanel p_now;
	ResvPanel p_resv;	
	MemberPanel p_member;
	ChatPanel p_chat;	
	JPanel[] p_menus=new JPanel[5];	
	
	ClockThread clock; //�ð�
		
	public HotelMain() {
		manager=manager.getInstance();
		con=manager.getConnection();
				 
		page[0]=checkAdminPanel=new CheckAdminPanel(this);//�α������� Ȯ�� �г�
		page[1]=regAdminPanel=new RegAdminPanel(this);//�α������� Ȯ�� �г�
		page[2]=p_container=new JPanel();//�Ʒ� �г��� ���� �г�
		p_north=new JPanel();
		p_west=new JPanel();
		p_center=new JPanel();		
		p_north_west=new JPanel();
		p_north_east=new JPanel();
		
		//p_north�� ���� ����
		la_hotel=new JLabel("4��ȣ��                ");
		la_time=new JLabel();
		la_admin=new JLabel("ADMINISTRATOR: ");
		la_user=new JLabel("������");
		font_north=new Font("���� ���", Font.BOLD, 30);
		font_content=new Font("�������", Font.PLAIN, 20);
		bt_logout=new JButton("LOGOUT");
		
		//���⼭���ʹ� MyButton�� ��� p_west�� ���Ͽ���, �����ϱ� ���� �迭�� ��´�.
		buttons[0]=bt_home=new JButton();
		buttons[1]=bt_now=new JButton();
		buttons[2]=bt_resv=new JButton();
		buttons[3]=bt_member=new JButton();
		buttons[4]=bt_chat=new JButton();
		
		//mybuttons����
		for (int i = 0; i < imgName.length; i++) {
			URL url=this.getClass().getResource("/"+imgName[i][0]); //�տ� /�� �־�� �Ѵ�.
			myButtons[i]=new MyButton(this, buttons[i], url, imgName[i][1]);
		}		
		myButtons[0].flag=false;//ó���� home��ư ���õǾ��ְ� �ʱ�ȭ
		myButtons[0].setBackground(Color.LIGHT_GRAY);		
				
		//�޴����� �г��� ������. ���߿� �����ϱ⽱�� �迭�� ��´�.
		p_menus[0]=p_home=new HomePanel(this);
		p_menus[1]=p_now=new NowPanel(this);
		p_menus[2]=p_resv=new ResvPanel(this);
		p_menus[3]=p_member=new MemberPanel(this);
		p_menus[4]=p_chat=new ChatPanel(this);
		
		clock=new ClockThread(this); //�ð����
		
		//���̾ƿ�����
		this.setLayout(new FlowLayout()); //pack�̿��ϰ� flowlayout����
		p_container.setLayout(new BorderLayout());
		p_north.setLayout(new GridLayout(1, 2));
		
		//size����
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
		setPage(0);//ó�������������� �α����гκ��̰� �Ѵ�.
		
		//�����ʿ��� ����
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
		
		//setSize(1280, 960); //pack()�� �̿��� �����̹Ƿ� ���⼭������ �����ϸ� �ȵȴ�.
		setLocationRelativeTo(null);
		setVisible(true);	
		
	}
	
	//���ڿ� ���� �α����������� ���������� ��ü
	public void setPage(int index){
		for (int i = 0; i < page.length; i++) {
			if (i==index) {
				page[i].setVisible(true);
			}else{
				page[i].setVisible(false);
			}			
		}
		this.pack();//���빰�� ũ�⸸ŭ ������ ũ�⸦ ����, �ϳ��� �����찡 �������� ����� ���ϼ� �ִ�.
		this.setLocationRelativeTo(null); //ȭ���߾ӿ� ��ġ, ���⼭ �� ���ϸ� �����ִ����� ũ�� �ٲ��.
	}
	
	//�޴����ÿ� ���� �г��� �����ִ� �޼ҵ�
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
		
		//�α׾ƿ���ư ������ checkAdminPanel���̰� ����
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
