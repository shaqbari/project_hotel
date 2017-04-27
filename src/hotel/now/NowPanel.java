/*DBProject 2 참고*/
package hotel.now;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JTextField;

import hotel.HotelMain;

/*이패널을 그리드레이아웃으로 만들고 room_item을 원하는곳에 배치시키면
 * 맨아래쪽이 1층 맨 위쪽에 6층이 나오게 할 수 있지 않을까?*/
public class NowPanel extends JPanel implements ActionListener{
	HotelMain main;
	Connection con;
	JPanel p_north, p_center, p_in, p_out, p_option_center, p_option_west, p_option_east;
	JButton bt_prevDay, bt_nextDay, bt_prevMonth, bt_nextMonth, bt_search;
	JLabel la_date, la_in, la_out, la_yy, la_mm, la_dd;
	JTextField t_yy, t_mm, t_dd;	
	ArrayList<Room_Option> list;
	ArrayList<Room_Option> resv; 
	Calendar cal = Calendar.getInstance();
	Thread thread;
	
	//날짜 받아올 변수
	int yy;
	int mm;
	int dd;
	
	//rgb 색상 값
	int r = 96;
	int g = 96;
	int b = 96;
	
	public NowPanel(HotelMain main) {
		this.main = main;
		con = main.con;
		
		this.setLayout(new BorderLayout());
		p_north = new JPanel();
		p_center = new JPanel();
		bt_prevMonth = new JButton("◀◀");
		bt_prevDay = new JButton("◀");
		la_date = new JLabel();
		bt_nextDay = new JButton("▶");
		bt_nextMonth = new JButton("▶▶");
		
		p_option_center = new JPanel();		
		p_option_west = new JPanel();
		p_option_east = new JPanel();
		
		t_yy = new JTextField(3);
		t_mm = new JTextField(3);
		t_dd = new JTextField(3);
		la_yy = new JLabel("년");
		la_mm = new JLabel("월");
		la_dd = new JLabel("일");
		bt_search = new JButton("검색");
		
		p_in = new JPanel();
		p_out = new JPanel();
		la_in = new JLabel("사용 중");
		la_out = new JLabel("비어있음");
	
		p_in.setBackground(Color.red);
		p_out.setBackground(new Color(r,g,b));	
		p_north.setLayout(new BorderLayout());
		p_option_west.setPreferredSize(new Dimension(280, 40));
		
		p_option_west.add(p_in);
		p_option_west.add(la_in);
		p_option_west.add(p_out);
		p_option_west.add(la_out);
		
		p_option_center.add(bt_prevMonth);
		p_option_center.add(bt_prevDay);
		p_option_center.add(la_date);
		p_option_center.add(bt_nextDay);
		p_option_center.add(bt_nextMonth);
		
		p_option_east.add(t_yy);
		p_option_east.add(la_yy);
		p_option_east.add(t_mm);
		p_option_east.add(la_mm);
		p_option_east.add(t_dd);
		p_option_east.add(la_dd);
		p_option_east.add(bt_search);
	
		p_north.add(p_option_west, BorderLayout.WEST);
		p_north.add(p_option_center);
		p_north.add(p_option_east, BorderLayout.EAST);
		
		p_center.setBackground(Color.LIGHT_GRAY);
		
		la_date.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		//날짜설정
		yy = cal.get(Calendar.YEAR);
		mm = cal.get(Calendar.MONTH);
		dd = cal.get(Calendar.DATE);

		//날짜 불러오기, 객실현황 불러오기 포함되어 있음
		setDate();
		
		bt_prevDay.addActionListener(this);
		bt_nextDay.addActionListener(this);
		bt_prevMonth.addActionListener(this);
		bt_nextMonth.addActionListener(this);
		bt_search.addActionListener(this);
		
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1100, 900));
		setVisible(true);
	}

	// 객실 현황 불러오기 메서드
	public void loadData() {
		// room 테이블과 room_option 테이블의 자료를 조인.
		String sql = "select * from room_option o left outer join room r on o.room_option_id = r.room_option_id order by r.room_number asc";
		
		//원하는 날의 예약된 방 구하기(만들어진 뷰를 통해서 추가로 예약자 이름,입퇴실 시간 정보 가져오기)
		String revday ="SELECT re.room_number, re.resv_time, re.END_TIME, v.GUEST_NAME, v.MEMBERSHIP_NAME"
				+ " FROM resv re, resv_detail red, view_hotel_user2 v"
				+ " WHERE re.resv_id = red.resv_id and re.HOTEL_USER_ID = v.HOTEL_USER_ID"
				+ " AND TO_CHAR (red.stay_date, 'yyyy') = ? AND TO_CHAR (red.stay_date, 'mm') = ? AND TO_CHAR (red.stay_date, 'dd') = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<Room_Option>();	//날짜변경시 계속해서 배열에 쌓여버리는 것을 방지하기 위해 매번 생성
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
					
			//예약된 방 담기, 바인드 변수 설정
			pstmt = con.prepareStatement(revday);
			pstmt.setInt(1, yy);
			pstmt.setInt(2, mm+1);	//표기되는 날보다 +1로 해야함.
			pstmt.setInt(3, dd);
			rs = pstmt.executeQuery();
			resv = new ArrayList<Room_Option>();	//날짜 변경시 마다 새롭게 예약된 방을 담기 위해 매번 생성 	
			while (rs.next()){
				Room_Option vo = new Room_Option();
				vo.setRoom_number(rs.getInt("room_number"));
				vo.setResv_time(rs.getString("resv_time"));
				vo.setEnd_time(rs.getString("end_time"));
				vo.setMembership_name(rs.getString("membership_name"));
				vo.setGuest_name(rs.getString("guest_name"));
				
				resv.add(vo);		
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

	//날짜 설정 메서드. 날짜 설정시 마다 예약현황 확인하고 패널리스트들 다시 그린다.
	public void setDate(){
		la_date.setText(yy+"-"+(mm+1)+"-"+dd);
		p_center.removeAll();
		//p_center.rem
		loadData();

	}
	
	public void prevDay(){
		dd--;
		if(dd<1){
			mm--;
			//마지막날 구해놓기
			cal.set(yy, mm+1, 0);
			int lastDay = cal.get(Calendar.DATE);
			dd=lastDay;
			if(mm<0){
				yy--;
				mm=11;
				//마지막날 구해놓기
				cal.set(yy, mm+1, 0);
				int last = cal.get(Calendar.DATE);
				dd=last;
			}
		}
		cal.set(yy, mm, dd);
		setDate();
	}
	
	public void nextDay(){
		dd++;
		//마지막날 구해놓기
		cal.set(yy, mm+1, 0);
		int lastDay = cal.get(Calendar.DATE);
		if(dd>lastDay){
			mm++;
			dd=1;	
			if(mm>11){
				yy++;
				dd=1;
				mm=0;
			}
		}
		cal.set(yy, mm, dd);
		setDate();
	}
	
	public void prevMonth(){
		mm--;
		if(mm<0){
			yy--;
			mm=11;
		}
		cal.set(yy, mm, dd);
		setDate();
	}
	
	public void nextMonth(){
		mm++;
		if(mm>11){
			yy++;
			mm=0;
		}
		cal.set(yy, mm, dd);
		setDate();
	}
	
	public void search(){
		yy = Integer.parseInt(t_yy.getText());
		mm = Integer.parseInt(t_mm.getText())-1;
		dd = Integer.parseInt(t_dd.getText());
		
		cal.set(yy, mm, dd);
		setDate();
		t_yy.setText("");
		t_mm.setText("");
		t_dd.setText("");
	}
	
	//버튼에 적용할 액션리스너
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_prevDay){
			prevDay();
		} else if (obj == bt_nextDay){
			nextDay();
		} else if (obj == bt_prevMonth){
			prevMonth();
		} else if (obj == bt_nextMonth){
			nextMonth();
		} else if (obj == bt_search){
			search();
		}
	}
	
}
