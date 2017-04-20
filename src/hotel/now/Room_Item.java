package hotel.now;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	Font font=new Font("���� ���", Font.BOLD,16);
	
	public Room_Item(String number, String name, Image img, NowPanel nowPanel) {
		this.nowPanel = nowPanel;
		la_number = new JLabel(number);
		la_name = new JLabel("/ "+name);
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 165, 120, this);
			}
		};
		
		int r = 96;
		int g = 96;
		int b = 96;
		setBackground(new Color(r,g,b));
		la_number.setForeground(Color.BLACK);
		
		//����� �� ǥ���ϱ�
		for(int i=0; i<nowPanel.resvNumber.size();i++){
			if(Integer.parseInt(la_number.getText()) == nowPanel.resvNumber.get(i).getRoom_number()){
				setBackground(Color.RED);
			} else{
				//setBackground(Color.LIGHT_GRAY);
			}
		}
		
		la_number.setFont(font);
		la_name.setFont(font);
		la_number.setForeground(Color.BLACK);
		la_name.setForeground(Color.BLACK);
		
		//ĵ������ ���콺 �̺�Ʈ �߰�
		can.addMouseListener(new MouseAdapter() {
			//Ŭ���� �ش� ���� ��ȣ ���Ͽ� �´� ������ Room_Detail �����ϱ�
			public void mouseClicked(MouseEvent e) {
				Object obj=e.getSource();
				Canvas can=(Canvas)obj;
				Room_Item container=(Room_Item)can.getParent();
				for(int i=0; i<nowPanel.list.size(); i++){
					if(Integer.parseInt(container.la_number.getText())==nowPanel.list.get(i).getRoom_number()){
						new Room_Detail(nowPanel,i);
					}
				}
			}
			//���콺 �÷������� �������� �۾�����ȭ
			public void mouseEntered(MouseEvent e) {
				la_number.setForeground(Color.WHITE);
				la_name.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				la_number.setForeground(Color.BLACK);
				la_name.setForeground(Color.BLACK);
			}
		});
			
		add(la_number);
		add(la_name);
		add(can);
		
		can.setPreferredSize(new Dimension(165, 120));
		setPreferredSize(new Dimension(165, 125));
		
	}
	
}







