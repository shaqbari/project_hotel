package hotel.home;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hotel.HotelMain;

public class HomePanel extends JPanel implements ActionListener{
	HotelMain main;
	Connection con;
	
	JPanel p_info, p_img; //�׸��巹�̾ƿ��� ���� �г�
	JPanel p_info_admin, p_info_log; //p_info�� ���� �г�
	
	JButton bt_startServer;
	public JTextArea area;
	JLabel la_all;
	JTextField txt;
	JScrollPane scroll;	
	Canvas can; 

	
	public HomePanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		p_info=new JPanel();
		p_img=new JPanel();
		p_info_admin=new JPanel();
		p_info_log=new JPanel();
		
		bt_startServer=new JButton("���� ����");
		area=new JTextArea();
		scroll=new JScrollPane(area);
		la_all=new JLabel("��üȣ�ǿ� ���ϱ�");
		txt=new JTextField(35);
				
		can=new Canvas(){
			
		/*	public void paintComponent(Graphics g){
				URL url=this.getClass().getResource("/hotel.jpg");
				try {
					BufferedImage img=ImageIO.read(url);					
					g.drawImage(img, 0, 0, 500, 800, HomePanel.this);
					System.out.println("�׷�����?");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
			
			public void paint(Graphics g) {
				URL url=this.getClass().getResource("/hotel.jpg");
				try {
					BufferedImage img=ImageIO.read(url);					
					g.drawImage(img, 0, 0, 500, 850, HomePanel.this);
					System.out.println("�׷�����?");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		scroll.setPreferredSize(new Dimension(500, 800));
		can.setPreferredSize(new Dimension(500, 850));//ũ�⸦ �����ؾ� �̹����� ���´�.
		
		this.setLayout(new GridLayout(1, 2));
		p_info.setLayout(new BorderLayout());
		
		p_info_admin.add(bt_startServer);		
		p_info_log.add(scroll);
		p_info_log.add(la_all);		
		p_info_log.add(txt);
		
		p_info.add(p_info_admin, BorderLayout.NORTH);
		p_info.add(p_info_log);		
		p_img.add(can);
		
		//��üȣ�ǿ� ���ϴ� �޼ҵ�
		txt.addKeyListener(new KeyAdapter() {			
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if (key==KeyEvent.VK_ENTER) {
					for (int i = 0; i < main.serverThreadList.size(); i++) {
						main.serverThreadList.get(i).send(txt.getText());
					}					
					area.append("��üȣ�ǿ� ���ϱ�: "+txt.getText());
					txt.setText("");
				}
			}
		});
		
		bt_startServer.addActionListener(this);
		
		add(p_info);
		add(p_img);		
		
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(true);		
	}


	public void actionPerformed(ActionEvent e) {
		System.out.println("��������");
		main.thread.start();
		bt_startServer.setEnabled(false);
	}
	
}
