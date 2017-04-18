/*
 	객실 현황에서 객실 선택시 나올 상세정보
 	1. 선택한 방에 따라 다른 정보 나오기
 	2. 이를 위해 각각의 정보를 배열에 담기

*/

package hotel.now;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Room_Detail extends JFrame {
	JPanel p_north, p_center, p_east;
	JLabel la_north, la_center;
	Canvas can;
	Image img;
	NowPanel nowPanel;
	int i;
	Font font=new Font("맑은 고딕", Font.PLAIN, 15);
	
	public Room_Detail(NowPanel nowPanel,int i) {
		this.nowPanel = nowPanel;
		this.i=i;
		p_north = new JPanel();
		p_center = new JPanel();
		p_east = new JPanel();
		la_north = new JLabel("선택한 객실 호수/객실명");
		la_center = new JLabel("객실 정보");
		this.setTitle("상세정보");
		
		view();

		p_east.setLayout(new BorderLayout());		//사진 여백없이 붙게
		
		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 530, 400, this);
				
			}
		};

		p_north.setBackground(Color.LIGHT_GRAY);
		p_center.setBackground(Color.WHITE);
		//p_east.setBackground(Color.PINK);

		p_north.setPreferredSize(new Dimension(600, 50));
		can.setPreferredSize(new Dimension(530, 400));

		p_north.add(la_north);
		p_center.add(la_center);
		p_east.add(can);

		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_east, BorderLayout.EAST);
		
		la_north.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		la_center.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		
		setSize(750, 450);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public void view() {	
			Room_Option room = nowPanel.list.get(i);
			
			try {
				String number = Integer.toString(room.getRoom_number());
				String name = room.getRoom_option_name();
				String size = Integer.toString(room.getRoom_option_size());
				String bed = room.getRoom_option_bed();
				String view = room.getRoom_option_view();
				String max = Integer.toString(room.getRoom_option_max());
				String price = Integer.toString(room.getRoom_option_price());
				
				URL url=this.getClass().getResource("/"+room.getRoom_option_img());
				img = ImageIO.read(url);
				
				
				
				la_north.setText(number+"호");			
				la_center.setText("<html>"+"<br> ㆍ이름 : "+name+"<br> ㆍ평형 : "+size+"평 <br> ㆍ침대 : " +bed+"<br> ㆍ객실 뷰 : "+view+"<br> ㆍ최대 인원수 : "+max+"명 <br> ㆍ가격 : "+price+"원 </html>");
				
					
			} catch (IOException e) {
				e.printStackTrace();
			
		}
	}
	
}
