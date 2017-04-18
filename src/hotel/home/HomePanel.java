package hotel.home;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import hotel.HotelMain;
import javafx.scene.image.Image;

public class HomePanel extends JPanel{
	HotelMain main;
	Connection con;
	
	JPanel p_info, p_img; //그리드레이아웃에 붙일 패널
	JPanel p_info_admin, p_info_log; //p_info에 붙일 패널
	
	JTextArea area;
	JScrollPane scroll;	
	Canvas can; 

	
	public HomePanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		p_info=new JPanel();
		p_img=new JPanel();
		p_info_admin=new JPanel();
		p_info_log=new JPanel();
		
		area=new JTextArea();
		scroll=new JScrollPane(area);
				
		can=new Canvas(){
			
		/*	public void paintComponent(Graphics g){
				URL url=this.getClass().getResource("/hotel.jpg");
				try {
					BufferedImage img=ImageIO.read(url);					
					g.drawImage(img, 0, 0, 500, 800, HomePanel.this);
					System.out.println("그려지나?");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
			
			public void paint(Graphics g) {
				URL url=this.getClass().getResource("/hotel.jpg");
				try {
					BufferedImage img=ImageIO.read(url);					
					g.drawImage(img, 0, 0, 500, 850, HomePanel.this);
					System.out.println("그려지나?");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		scroll.setPreferredSize(new Dimension(500, 800));
		can.setPreferredSize(new Dimension(500, 850));//크기를 지정해야 이미지가 나온다.
		
		this.setLayout(new GridLayout(1, 2));
		p_info.setLayout(new BorderLayout());
		
		
		p_info_log.add(scroll);
		
		p_info.add(p_info_admin, BorderLayout.NORTH);
		p_info.add(p_info_log);
		p_img.add(can);
		
		add(p_info);
		add(p_img);		
		
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(true);		
	}
	
}
