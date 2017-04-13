/*DBProject 2 참고*/
package hotel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class NowPanel extends JPanel{
	HotelMain main;
	Connection con;
	String path = "C:/java_workspace2/project_hotel/image/room/";
	ArrayList<Room_Option> list = new ArrayList<Room_Option>();
	
	public NowPanel(HotelMain main) {
		this.main=main;
		con=main.con;
				
		//객실 현황 불러오기
		loadData();	
		
		setBackground(Color.LIGHT_GRAY);
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(true);	
	}
	
	//객실 현황 불러오기 메서드
	public void loadData(){
		String sql = "select * from room_option order by room_option_id asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Room_Option dto = new Room_Option();
				dto.setRoom_option_id(rs.getInt("room_option_id"));
				dto.setRoom_option_name(rs.getString("room_option_name"));
				dto.setRoom_option_size(rs.getInt("room_option_size"));
				dto.setRoom_option_bed(rs.getString("room_option_bed"));
				dto.setRoom_option_view(rs.getString("room_option_view"));
				dto.setRoom_option_max(rs.getInt("room_option_max"));
				dto.setRoom_option_img(rs.getString("room_option_img"));
				dto.setRoom_option_price(rs.getInt("room_option_price"));
				
				list.add(dto);
			}
			//가져온 데이터베이스를 디자인에 반영
			init();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}				
	}
	
	public void init(){
		for(int i=0;i<list.size();i++){
			Room_Option room = list.get(i);
			try {
				String number = Integer.toString(room.getRoom_option_id());
				//ㄴ임시로 room_option_id 넣음. 추후에 join 통해 rooom_number 구해와야 함.
				String name = room.getRoom_option_name();
				Image img = ImageIO.read(new File(path+room.getRoom_option_img()));
				
				Room_Item item = new Room_Item(number, name, img);
				add(item);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}













