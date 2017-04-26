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
	Room_Item roomItem;
	int i;

	String membership_name="";
	String guest_name="";
	String resv_time="";
	String end_time="";
	
	public Room_Detail(NowPanel nowPanel,Room_Item roomItem,int i) {
		this.nowPanel = nowPanel;
		this.roomItem = roomItem;
		this.i=i;
		p_north = new JPanel();
		p_center = new JPanel();
		p_east = new JPanel();
		la_north = new JLabel("선택한 객실 호수/객실명");
		la_center = new JLabel("객실 정보");
		this.setTitle("상세정보");

		p_east.setLayout(new BorderLayout());		//사진 여백없이 붙게
		p_center.setLayout(new BorderLayout());		//텍스트 왼쪽정렬위해
		
		p_north.setBackground(Color.DARK_GRAY);
		p_center.setBackground(Color.WHITE);
		la_north.setForeground(Color.WHITE);
		
		la_north.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		la_center.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		
		//방 생성
		view();
		
		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 530, 400, this);			
			}
		};

		p_north.setPreferredSize(new Dimension(600, 50));
		can.setPreferredSize(new Dimension(530, 400));

		p_north.add(la_north);
		p_center.add(la_center);
		p_east.add(can);

		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_east, BorderLayout.EAST);
		
	
		
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
				
				//---
				for(int i=0; i<nowPanel.resv.size();i++){
					if(Integer.parseInt(roomItem.la_number.getText()) == nowPanel.resv.get(i).getRoom_number()){
						Room_Option resv = nowPanel.resv.get(i);
						membership_name = resv.getMembership_name();
						guest_name = resv.getGuest_name();
						resv_time = resv.getResv_time();
						end_time = resv.getEnd_time();	
						p_north.setBackground(Color.RED);
					}
				}
				
				if(membership_name==null){
					membership_name = "";
				} else if(guest_name==null){
					guest_name = "";
				} else if(resv_time==null){
					resv_time = "";
				} else if(end_time==null){
					end_time = "";
				}
				
				la_north.setText(number+"호");			
				la_center.setText("<html><br> ㆍ등급 : "+name+"<br> ㆍ평형 : "+size+"평 <br> ㆍ침대 : " +bed+"<br> ㆍ객실 뷰 : "+view+"<br> ㆍ최대 인원수 : "+max+"명 <br> ㆍ가격 : "+price+"원 "
						+ "<br>ㆍ예약자 : "+membership_name+guest_name+"<br>ㆍ입실일 <br> &nbsp; &nbsp;"+resv_time+"<br>ㆍ퇴실일 <br> &nbsp; &nbsp;"+end_time+"</html>");
				
				
			} catch (IOException e) {
				e.printStackTrace();
			
		}
	}
	
}
