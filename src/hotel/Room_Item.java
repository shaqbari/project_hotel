package hotel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Room_Item extends JPanel {
	Canvas can;
	JLabel la_number, la_name;
	Image img;
	
	public Room_Item(String number, String name, Image img) {
		la_number = new JLabel(number);
		la_name = new JLabel(name);
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 130, 100, this);
			}
		};
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DetailView();
			}
		});
		
		add(la_number);
		add(la_name);
		add(can);
		
		can.setPreferredSize(new Dimension(130, 100));
		setPreferredSize(new Dimension(130, 150));
		setBackground(Color.WHITE);
	}
	
	public void DetailView(){
		JFrame frame = new JFrame("상세정보");
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		frame.setSize(500, 300);
		frame.setLocation(400, 500);
	}
	
}







