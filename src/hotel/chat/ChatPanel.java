package hotel.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import hotel.HotelMain;

public class ChatPanel extends JPanel{
	HotelMain main;
	Connection con;
	public JPanel p_serv;
	//JTable serv_table;
	JScrollPane scroll;
	
	public ChatPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		setLayout(new FlowLayout());
		
		//p_chat= new JPanel();
		//p_chat1.setPreferredSize(new Dimension(550, 900));
		//p_chat2 = new JPanel();
		//p_serv = new JPanel();
		//p_serv.setPreferredSize(new Dimension(550, 900));
		//serv_table = new JTable(30,4);
		//scroll = new JScrollPane(serv_table);
		
		//p_chat2.setLayout(new FlowLayout());
		//p_chat1.add(p_chat2);
		//p_serv.add(scroll);
		
		//add(p_chat1,BorderLayout.WEST);
		//add(p_serv);
		
		setBackground(Color.GRAY);
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);		
	}
}
