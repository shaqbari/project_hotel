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
	Font font=new Font("맑은 고딕", Font.BOLD,16);
	
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
		
		/*원래코드
		//예약된 방 표시하기
		for(int i=0; i<nowPanel.resvNumber.size();i++){
			if(Integer.parseInt(la_number.getText()) == nowPanel.resvNumber.get(i).getRoom_number()){
				setBackground(Color.RED);
			} else{
				//setBackground(Color.LIGHT_GRAY);
			}
		}
		*/
		
		//스테이 횟수가 2이상일때 스테이와 같은 숫자만큼 방번호를 배열에 저장하고 해당 숫자를 더한 날까지 방번호가 나오게.
		//체크인 날짜와 체크아웃 날짜를 지정하여 체크아웃 날짜의 -1만큼 숫자를 갖고있게
		//스테이가 1이상일때 반복 횟수
	
		for(int i=0; i<nowPanel.resvNumber.size();i++){
			if(Integer.parseInt(la_number.getText()) == nowPanel.resvNumber.get(i).getRoom_number()){
				setBackground(Color.RED);
			} else{
				//setBackground(Color.LIGHT_GRAY);
			}
		}
		//---------------------------
		
		
		
		la_number.setFont(font);
		la_name.setFont(font);
		la_number.setForeground(Color.BLACK);
		la_name.setForeground(Color.BLACK);
		
		//캔버스에 마우스 이벤트 추가
		can.addMouseListener(new MouseAdapter() {
			//클릭시 해당 방의 번호 비교하여 맞는 정보의 Room_Detail 생성하기
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
			//마우스 올렸을때와 내렸을때 글씨색변화
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







