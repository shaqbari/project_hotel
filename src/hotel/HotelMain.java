package hotel;

public class HotelMain {

<<<<<<< HEAD
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
	
	JPanel[] menu=new JPanel[5];
	HomePanel p_home;
	NowPanel p_now;
	ResvPanel p_resv;
	MemberPanel p_member;
	ChatPanel p_chat;	
	
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
		
		p_home=new HomePanel(this);
		p_now=new NowPanel(this);
		p_resv=new ResvPanel(this);
		p_member=new MemberPanel(this);
		p_chat=new ChatPanel(this);
		
		//p_center.setLayout(new BorderLayout());
		
		p_north.setBackground(Color.YELLOW);
		p_west.setBackground(Color.ORANGE);
		p_center.setBackground(Color.GREEN);
		
		p_north.setPreferredSize(new Dimension(1280, 60));
		p_west.setPreferredSize(new Dimension(180, 900));
		p_center.setPreferredSize(new Dimension(1100, 900));
		
		p_north.add(la_hotel);
		p_north.add(la_time);
		p_north.add(la_user);
		p_north.add(bt_logout);
		
		p_west.add(bt_home);
		p_west.add(bt_now);
		p_west.add(bt_resv);
		p_west.add(bt_member);
		p_west.add(bt_chat);
		
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
	
	public void actionPerformed(ActionEvent e) {
		Object obj=(Object)e.getSource();
		if (obj==bt_home) {			
			p_home.setVisible(true);
			p_center.updateUI();
		}else if (obj==bt_now) {
			p_now.setVisible(true);
			p_center.updateUI();
		}else if (obj==bt_resv) {
			p_resv.setVisible(true);
			p_center.updateUI();
		}else if (obj==bt_member){
			p_member.setVisible(true);
			p_center.updateUI();
		}else if (obj==bt_chat) {
			p_chat.setVisible(true);
			p_center.updateUI();
		}
	}

	public static void main(String[] args) {
		new HotelMain();
	}

	
=======
>>>>>>> 8b15ba522a79c3d914e73f5eb586ac0c8eac1122
}
