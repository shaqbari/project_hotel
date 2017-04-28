package hotel.service;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hotel.HotelMain;
import hotel.server.ServerThread;
import oracle.net.ano.SupervisorService;

public class ChatBox extends JFrame{	
	ServerThread serverThread;
	HotelMain main;
	JLabel la_room_number;
	public JTextArea area;
	JScrollPane scroll;
	JTextField txt_input;
	Calendar cal;
	String yyyy, mm, dd, hh24, mi, ss;
	
	public ChatBox(String title) {
		super(title);
		area=new JTextArea(15, 20);
		scroll=new JScrollPane(area);
		txt_input=new JTextField(20);
		
		setLayout(new BorderLayout());
		
		add(la_room_number, BorderLayout.NORTH);
		add(scroll);
		add(txt_input, BorderLayout.SOUTH);
		
		txt_input.requestFocus();
		
		txt_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if (key==KeyEvent.VK_ENTER) {
					cal=Calendar.getInstance();
					yyyy=Integer.toString(cal.get(Calendar.YEAR));
					int month=cal.get(Calendar.MONTH)+1;
					mm=Integer.toString(month);
					dd=Integer.toString(cal.get(Calendar.DATE));
					hh24=Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
					mi=Integer.toString(cal.get(Calendar.MINUTE));
					ss=Integer.toString(cal.get(Calendar.SECOND));
					
					String msg=txt_input.getText();
					String requestTime=yyyy+"-"+mm+"-"+dd+" "+hh24+":"+mi+":"+ss;
					serverThread.chatSend(msg+requestTime);
					area.append(msg+requestTime+"\n");
					txt_input.setText("");
				}
			}			
		});
		
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				System.out.println("들어가나?");
				ChatBox.this.serverThread.chatOff=true;
				System.out.println(ChatBox.this.serverThread.chatOff);
			}	
		});
		
		setBounds(0, 200, 350, 500);
		
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);새로 띄우는 jframe은 이거 쓰면 안된다.
	}

	//chatbox에서 생성하고 정보넘겨주자.
	public void getServerThread(ServerThread serverThread){
		this.serverThread=serverThread;		
	}
	
}
