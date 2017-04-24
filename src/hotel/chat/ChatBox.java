package hotel.chat;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hotel.server.ServerThread;

public class ChatBox extends JPanel{	
	ServerThread serverThread;
	
	JLabel la_room_number;
	public JTextArea area;
	JScrollPane scroll;
	JTextField txt_input;
	
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
					String msg=txt_input.getText();
					serverThread.chatSend(msg);
					ChatBox.this.area.append(msg+"\n");
					txt_input.setText("");
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
