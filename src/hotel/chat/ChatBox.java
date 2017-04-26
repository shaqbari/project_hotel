package hotel.chat;

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

public class ChatBox extends JFrame{	
	ServerThread serverThread;
	HotelMain main;
	JLabel la_room_number;
	public JTextArea area;
	JScrollPane scroll;
	JTextField txt_input;
	Calendar cal;
	String yyyy, mm, dd, hh24, mi, ss;
	
	public ChatBox() {		
		la_room_number=new JLabel("203ȣ");
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
					mm=Integer.toString(cal.get(Calendar.MONTH));
					dd=Integer.toString(cal.get(Calendar.DATE));
					hh24=Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
					mi=Integer.toString(cal.get(Calendar.MINUTE));
					ss=Integer.toString(cal.get(Calendar.SECOND));
					
					String msg=txt_input.getText();
					serverThread.chatSend(msg);
					area.append(msg+yyyy+"-"+mm+"-"+dd+" "+hh24+":"+mi+":"+ss+"\n");
					txt_input.setText("");
				}
			}			
		});
		
		this.addWindowListener(new WindowAdapter() {
		@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}	
		});
		
		setSize(350, 500);
		setVisible(true);		
	}
	
	public void getServerThread(ServerThread serverThread){
		this.serverThread=serverThread;		
	}
	
}
