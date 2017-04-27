package hotel.service;

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
		la_room_number=new JLabel("203ȣ");
		area=new JTextArea(8, 40);
		scroll=new JScrollPane(area);
		//txt_input=new JTextField(20);
		bt_complete = new JButton("�����غ�Ϸ�");
		
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
					String msg="�ֹ��Ͻż��񽺰� �غ� �Ϸ�Ǿ�, 5�г��� �����մϴ�. �����մϴ�.";
					serverThread.chatSend(msg);
					ServiceBox.this.removeAll();
					//ServiceBox.this.area.append(msg+"\n");
					ServiceBox.this.updateUI();
					//serverThread.serviceThreadList.removeElement(serverThread); //���Ϳ��� �� �����带 ����
					//serverThread.area.append("���� �غ��ؾ��� ���� ���񽺴�?: "+serverThread.serviceThreadList.size()+"\n");
					//System.out.println("���񽺿Ϸ� �޼��� ���� �� �̿�� ���� â ������");
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
