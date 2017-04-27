package hotel.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import hotel.HotelMain;
import hotel.chat.ServiceBox;

public class ResponseService {
	ServerThread serverThread;
	HotelMain main;
	Connection con;
	JSONObject json;
	int service_use_id=0;
	public ServiceBox servBox;//ChatPanel의 serviceBox를 담을예정
	
	public ResponseService(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.main=serverThread.main;
		this.json=json;
		con=main.con;
		
		servBox = new ServiceBox();
		main.p_chat.add(servBox);
		servBox.getServerThread(serverThread);
		//responseJson = new JSONObject();
		//System.out.println("HotelClient 의 ResponseService 실행");
		dbCheck();
	}
	
	public void dbCheck(){
	
		String hotel_user_id=json.get("hotel_user_id").toString();
		String service_id=json.get("service_id").toString();
		String time=json.get("requestTime").toString();
		
		System.out.println("service_use 에 서비스주문 넣는다.");
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("insert into service_use (service_use_id,hotel_user_id,service_id,service_use_time)");
		sql.append(" values(seq_service_use.nextVal,"+ hotel_user_id+","+service_id+",to_date('"+time+"', 'yyyy-mm-dd-hh24-mi-ss'))");
		try {
			//requestTime도 두자리가 되야한다 DateUtil이용!! 조심하자
			pstmt=con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//pstmt.setInt(1, Integer.parseInt(json.get("hotel_user_id").toString()));
			//pstmt.setInt(2, Integer.parseInt(json.get("service_id").toString()));
			//pstmt.setString(3, json.get("requestTime").toString());
			
			//System.out.println(sql.toString());
			int result=pstmt.executeUpdate();
			
			if (result==1) {
				sql.delete(0, sql.length());
				sql.append("select seq_service_use.currVal from dual");//sequence를 알기위해 바로 붙인다.
				
				pstmt=con.prepareStatement(sql.toString());
				rs=pstmt.executeQuery();
				
				rs.next();
				service_use_id=rs.getInt("currVal");	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}				
	}	
	
	 public void send(){
		String content=json.get("content").toString();
		String requestTime=json.get("requestTime").toString();
		String room_number=json.get("room_number").toString();
		String order=(room_number+"호에서 "+requestTime+" 시간에 "+content+" 주문하셨습니다"+"\n");
		//serverThread.send(content);
	
		if (service_use_id!=0) {
			serverThread.area.append(order);
			this.servBox.area.append(order);	
			
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "service");
			responseJSON.put("content", content);
			responseJSON.put("result", "yes");
			responseJSON.put("service_use_id", service_use_id);
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}else {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "service");
			responseJSON.put("content", content);
			responseJSON.put("result", "no");			
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}	
	}
	 	
}
