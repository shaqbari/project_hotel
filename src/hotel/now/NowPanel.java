/*DBProject 2 참고*/
package hotel.now;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hotel.HotelMain;

/*이패널을 그리드레이아웃으로 만들고 room_item을 원하는곳에 배치시키면
 * 맨아래쪽이 1층 맨 위쪽에 6층이 나오게 할 수 있지 않을까?*/
public class NowPanel extends JPanel implements ActionListener{
	HotelMain main;
	Connection con;
	JPanel p_north, p_center;
	JButton bt_prev, bt_next;
	JLabel la_date;
	ArrayList<Room_Option> list = new ArrayList<Room_Option>();
	ArrayList<Room_Option> resvNumber = new ArrayList<Room_Option>();
	Calendar cal = Calendar.getInstance();
	Thread thread;
	
	//날짜 받아올 변수
	int yy;
	int mm;
	int dd;
	
	public NowPanel(HotelMain main) {
		this.main = main;
		con = main.con;
		
		this.setLayout(new BorderLayout());
		p_north = new JPanel();
		p_center = new JPanel();
		bt_prev = new JButton("◀");
		la_date = new JLabel();
		bt_next = new JButton("▶");
		
		p_north.add(bt_prev);
		p_north.add(la_date);
		p_north.add(bt_next);
		
		p_center.setBackground(Color.LIGHT_GRAY);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		//날짜설정
		yy = cal.get(Calendar.YEAR);
		mm = cal.get(Calendar.MONTH);
		dd = cal.get(Calendar.DATE);
			
		//날짜 불러오기
		setDate();
		
		// 객실 현황 불러오기
		loadData();
		
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);

		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1100, 900));
		setVisible(true);
	}

	// 객실 현황 불러오기 메서드
	public void loadData() {
		// room 테이블과 room_option 테이블의 자료를 조인.
		String sql = "select * from room_option o left outer join room r on o.room_option_id = r.room_option_id order by r.room_number asc";

		//원하는 날의 예약된 방 구하기
		String revday = "select re.ROOM_NUMBER from ROOM_OPTION o, ROOM r, RESV re"
				+ " where o.ROOM_OPTION_ID=r.ROOM_OPTION_ID and r.ROOM_NUMBER=re.ROOM_NUMBER"
				+ " and to_char(re.RESV_TIME, 'yyyy')=2017 and to_char(re.RESV_TIME, 'mm')=4 and to_char(re.RESV_TIME, 'dd')=19";
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Room_Option dto = new Room_Option();
				dto.setRoom_option_id(rs.getInt("room_option_id"));
				dto.setRoom_option_name(rs.getString("room_option_name"));
				dto.setRoom_option_size(rs.getInt("room_option_size"));
				dto.setRoom_option_bed(rs.getString("room_option_bed"));
				dto.setRoom_option_view(rs.getString("room_option_view"));
				dto.setRoom_option_max(rs.getInt("room_option_max"));
				dto.setRoom_option_img(rs.getString("room_option_img"));
				dto.setRoom_option_price(rs.getInt("room_option_price"));
				dto.setRoom_number(rs.getInt("room_number"));

				list.add(dto);
			}
					
			//String yyyy="2017";
			//바인드 변수 두기
			
			//예약된 방 담기
			pstmt2 = con.prepareStatement(revday);
			//pstmt2.setString(1, yyyy);
			//pstmt2.setString(2, mm);
			//pstmt2.setString(3, dd);
			
			rs2 = pstmt2.executeQuery();
			
			while (rs2.next()){
				Room_Option vo = new Room_Option();
				vo.setRoom_number(rs2.getInt("room_number"));
				
				resvNumber.add(vo);
				
			}
			
			/*
			//rs2 테스트
			for(int i=0; i<resvNumber.size();i++){
				int nn = resvNumber.get(i).getRoom_number();
				System.out.println("예약된 방은? "+nn);
			}
			*/
			
			// 가져온 데이터베이스를 디자인에 반영
			init();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void init() {
		//변경된 Room_item 생성법 (실제 건물 형식으로 배치)	
		for (int i = 30; i >= 0; i -= 6) {
			for (int j = 0; j < 6; j++) {
				if (i + j > 31) {
					JPanel vacant = new JPanel();
					vacant.setPreferredSize(new Dimension(165, 120));
					vacant.setBackground(Color.LIGHT_GRAY);
					p_center.add(vacant);
					
				} else {
					Room_Option room = list.get(i + j);
					try {
						String number = Integer.toString(room.getRoom_number());
						String name = room.getRoom_option_name();

						// 이미지 res 폴더에서 불러오기
						URL url = this.getClass().getResource("/" + room.getRoom_option_img());
						Image img = ImageIO.read(url);
						
						Room_Item item = new Room_Item(number, name, img, this);
						p_center.add(item);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	
	public void setDate(){
		la_date.setText(yy+"-"+(mm+1)+"-"+dd);
		
	}
	
	
	public void prev(){
		dd--;
		if(dd<0){
			mm--;
			dd=31;
			if(mm<0){
				yy--;
				mm=11;
				dd=31;
			}
		}
		cal.set(yy, mm, dd);
		setDate();
	}
	
	public void next(){
		dd++;
		if(dd>31){
			mm++;
			dd=1;
			if(mm>12){
				yy++;
				mm=1;
				dd=1;
			}
		}
		cal.set(yy, mm, dd);
		setDate();
		
	}
	
	//버튼에 적용할 액션리스너
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_prev){
			prev();
		} else if (obj == bt_next){
			next();
		}
	}
	
}
