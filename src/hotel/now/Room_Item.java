package hotel.now;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Room_Item extends JPanel {
	Canvas can;
	JLabel la_number, la_name;
	Image img;
	NowPanel nowPanel;
	
	public Room_Item(String number, String name, Image img, NowPanel nowPanel) {
		this.nowPanel = nowPanel;
		la_number = new JLabel(number);
		la_name = new JLabel(name);
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 165, 120, this);
			}
		};
		
		//ĵ������ ���콺 Ŭ���̺�Ʈ �߰�
		can.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Object obj=e.getSource();
				Canvas can=(Canvas)obj;
				Room_Item container=(Room_Item)can.getParent();
				for(int i=0; i<nowPanel.list.size(); i++){
					if(Integer.parseInt(container.la_number.getText())==nowPanel.list.get(i).getRoom_number()){
						new Room_Detail(nowPanel,i);
					}
				}
				//DetailView();
				System.out.println("Ŭ��");
				
			}
		});
		
	
		add(la_number);
		add(la_name);
		add(can);
		
		can.setPreferredSize(new Dimension(165, 120));
		setPreferredSize(new Dimension(165, 125));
		setBackground(Color.WHITE);
	}
	
	
	//���� ������ ����
	/*
	public void DetailView(){
		JFrame frame = new JFrame("������");
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
		
		frame.setSize(500, 300);
		frame.setLocation(400, 500);
	}
	*/
}







