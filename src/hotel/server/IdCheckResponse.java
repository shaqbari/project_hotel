package hotel.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.sun.xml.internal.ws.api.addressing.WSEndpointReference.Metadata;

import hotel.HotelMain;

public class IdCheckResponse {
	ServerThread serverThread;
	HotelMain main;
	Connection con;
	JSONObject json;
	
	boolean overlap=true;
	
	public IdCheckResponse(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		main=serverThread.main;
		con=main.con;
		
		this.json=json;
		
		dbCheck();
				
	}
	
	/*//id중복확인 요청
		var msgExcheck1={
			"requestType":"idCheck",
			"room_number":204,
			"requestTime":"2017-04-17-18-19-23",
			"id_to_nick":"jsklsk",
		}*/
	
	
	//hoteluser에 먼저 등록하고 membership에 등록한다.
	public void dbCheck(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select m.MEMBERSHIP_NICK from MEMBERSHIP m where m.MEMBERSHIP_NICK=?");
				
		try {
			pstmt=con.prepareStatement(sql.toString(), ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE);
			pstmt.setString(1, json.get("id_to_nick").toString());
			rs=pstmt.executeQuery();
			
			rs.last();
			int last=rs.getRow();
			rs.beforeFirst();
			
			//last가 0이면 중복값이 없는것이다.
			if (last!=0) {
				overlap=false;
			}else {
				overlap=true;
			}
			
			System.out.println("overlap 결과"+overlap);			
			
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
	
/*//id중복확인 응답
	var msgExCheck2{		//중복안됨
		"responseType":"idCheck",
		"result":"yes",
		
	}	
	var msgExCheck3{		//중복됨
		"responseType":"idCheck",
		"result":"no"
	}*/
	
	public void response(){
		if (!overlap) {//중복되지 않았으면
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "idCheck");
			responseJSON.put("result", "yes");
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}else {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "idCheck");
			responseJSON.put("result", "no");			
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}		
	}
}
