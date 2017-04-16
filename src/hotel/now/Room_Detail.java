/*
 	���� ��Ȳ���� ���� ���ý� ���� ������
 	1. ������ �濡 ���� �ٸ� ���� ������
 	2. �̸� ���� ������ ������ �迭�� ���
*/

package hotel.now;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Room_Detail extends JFrame{
	JPanel p_north, p_center, p_east;
	JLabel la_north, la_center;
	Canvas can;
	ArrayList list = new ArrayList();
	URL[] url=new URL[7];
	Image img;
	
	public Room_Detail() {
		p_north = new JPanel();
		p_center = new JPanel();
		p_east = new JPanel();
		la_north = new JLabel("������ ���� ȣ��/���Ǹ�");
		la_center = new JLabel("���� ����");
				
		url[0]=this.getClass().getResource("/deluxe.jpg");
		url[1]=this.getClass().getResource("/business.jpg");
		url[2]=this.getClass().getResource("/grand.jpg");
		url[3]=this.getClass().getResource("/first.jpg");
		url[4]=this.getClass().getResource("/vip.jpg");
		url[5]=this.getClass().getResource("/vvip.jpg");
		url[6]=this.getClass().getResource("/sweet.jpg");
		
		//img = url[0];
		
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 165, 120, this);
			}
		};
		
		p_north.setBackground(Color.CYAN);
		p_center.setBackground(Color.YELLOW);
		p_east.setBackground(Color.PINK);
		
		p_north.setPreferredSize(new Dimension(600, 70));
		can.setPreferredSize(new Dimension(320, 480));
		
		p_north.add(la_north);
		p_center.add(la_center);
		p_east.add(can);
		
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_east, BorderLayout.EAST);
		
		setSize(700,550);
		setVisible(true);
		setLocation(null);
		
	}
	
	public static void main(String[] args) {
		new Room_Detail();
	}

}
