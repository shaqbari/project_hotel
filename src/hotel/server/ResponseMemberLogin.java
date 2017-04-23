package hotel.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import hotel.HotelMain;

public class ResponseMemberLogin {
	ServerThread serverThread;
	HotelMain main;
	Connection con;
	JSONObject json;
	Map<String, String> resvInfo;
		
	public ResponseMemberLogin(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		main=serverThread.main;
		con=main.con;
		
		this.json=json;
		
		dbCheck();
				
	}
	
	//db에서 resv_id와 전화번호 확인
	public boolean dbCheck(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select  r.HOTEL_USER_ID, r.RESV_TIME, r.STAY, g.GUEST_NAME");
		sql.append(" from guest g, HOTEL_USER h, RESV r");
		sql.append(" where g.HOTEL_USER_ID=h.HOTEL_USER_ID and h.HOTEL_USER_ID = r.HOTEL_USER_ID");
		sql.append(" and g.GUEST_PHONE=? and r.RESV_ID=?");
		
		try {
			pstmt=con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, json.get("phone").toString());
			pstmt.setInt(2, Integer.parseInt(json.get("resv_id").toString()));
			rs=pstmt.executeQuery();
			rs.last();
			int last=rs.getRow();
			rs.beforeFirst();
			
			if (last==1) {
				rs.next();
				resvInfo=new HashMap<String, String>();
				resvInfo.put("hotel_user_id", rs.getString("hotel_user_id"));
				resvInfo.put("guest_name", rs.getString("guest_name"));
				resvInfo.put("resv_time", rs.getString("resv_time"));
				resvInfo.put("stay", rs.getString("stay"));	
				resvInfo.put("guest_name", rs.getString("guest_name"));	
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
		
		return false;
	}	
	
	public void response(){
		if (resvInfo!=null) {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "guest_login");
			responseJSON.put("result", "yes");
			responseJSON.put("hotel_user_id", resvInfo.get("hotel_user_id"));
			responseJSON.put("guest_name", resvInfo.get("guest_name"));
			responseJSON.put("resv_time", resvInfo.get("resv_time"));
			responseJSON.put("stay", resvInfo.get("stay"));
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}else {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "guest_login");
			responseJSON.put("result", "no");			
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}		
	}
}
