package hotel.chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import hotel.HotelMain;
import hotel.server.ServerThread;

public class ServiceBox extends JPanel{	
	ServiceBox servBox;
	ServerThread serverThread;
	ChatPanel p_chat;
	HotelMain main;
	JLabel la_room_number;
	public JTextArea area;
	JScrollPane scroll;
	//JTextField txt_input;
	JButton bt_complete;
	
	public ServiceBox() {
		la_room_number=new JLabel("203호");
		area=new JTextArea(15, 20);
		scroll=new JScrollPane(area);
		//txt_input=new JTextField(20);
		bt_complete = new JButton("서비스준비완료");
		
		setLayout(new BorderLayout());
		
		add(la_room_number, BorderLayout.NORTH);
		add(scroll);
		add(bt_complete, BorderLayout.SOUTH);
		
		//txt_input.requestFocus();
		
		bt_complete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj=e.getSource();
				if(obj==bt_complete){
					String msg="주문하신서비스가 준비 완료되어, 5분내로 도착합니다. 감사합니다.";
					serverThread.chatSend(msg);
					ServiceBox.this.removeAll();
					//ServiceBox.this.area.append(msg+"\n");
					ServiceBox.this.updateUI();
					//System.out.println("서비스완료 메세지 전송 후 이용된 서비스 창 삭제됨");
				}
			}
		});
		
		setSize(200, 100);
		setVisible(true);		
	}

	public void getServerThread(ServerThread serverThread){
		this.serverThread=serverThread;		
	}
	
}
