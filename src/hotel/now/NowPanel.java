/*DBProject 2 참고*/
package hotel.now;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import hotel.HotelMain;

/*이패널을 그리드레이아웃으로 만들고 room_item을 원하는곳에 배치시키면
 * 맨아래쪽이 1층 맨 위쪽에 6층이 나오게 할 수 있지 않을까?*/
public class NowPanel extends JPanel {
	HotelMain main;
	Connection con;
	ArrayList<Room_Option> list = new ArrayList<Room_Option>();

	public NowPanel(HotelMain main) {
		this.main = main;
		con = main.con;

		// 객실 현황 불러오기
		loadData();

		setBackground(Color.LIGHT_GRAY);

		setPreferredSize(new Dimension(1100, 900));
		setVisible(true);
	}

	// 객실 현황 불러오기 메서드
	public void loadData() {
		// room 테이블과 room_option 테이블의 자료를 조인.
		String sql = "select * from room_option o left outer join room r on o.room_option_id = r.room_option_id order by r.room_number asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

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

		/*
		 * for(int i=list.size()-1; i>=0; i--){ Room_Option room = list.get(i);
		 * try { String number = Integer.toString(room.getRoom_number()); String
		 * name = room.getRoom_option_name();
		 * 
		 * //이미지 res 폴더에서 불러오기 URL
		 * url=this.getClass().getResource("/"+room.getRoom_option_img()); Image
		 * img = ImageIO.read(url);
		 * 
		 * Room_Item item = new Room_Item(number, name, img, this);
		 * if(i==list.size()-2){ add(item); add(vacant); }else{ add(item); } }
		 * catch (IOException e) { e.printStackTrace(); } }
		 */

		for (int i = 30; i >= 0; i -= 6) {
			for (int j = 0; j < 6; j++) {
				if (i + j > 31) {
					JPanel vacant = new JPanel();
					vacant.setPreferredSize(new Dimension(165, 120));
					vacant.setBackground(Color.LIGHT_GRAY);
					add(vacant);
					
				} else {
					Room_Option room = list.get(i + j);
					try {
						String number = Integer.toString(room.getRoom_number());
						String name = room.getRoom_option_name();

						// 이미지 res 폴더에서 불러오기
						URL url = this.getClass().getResource("/" + room.getRoom_option_img());
						Image img = ImageIO.read(url);

						Room_Item item = new Room_Item(number, name, img, this);
						add(item);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
