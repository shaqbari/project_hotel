package hotel.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hotel.HotelMain;

public class ResponseRoomSearch {
	ServerThread serverThread;
	HotelMain main;
	Connection con;
	JSONObject json;
	ArrayList<String> resvInfo;
		
	public ResponseRoomSearch(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.main=serverThread.main;
		con=main.con;
		
		this.json=json;
		
		dbCheck();
				
	}
	
	/*//방검색요청의 경우
		var msgExRoomSearch={
			"requestType":"room_search",
			"room_number":403,
			"request_time":"2017-05-03-18-19-23",
			"start":"2017-05-04-14-00-00",
			"end":"2017-05-05-12-00-00",
			"option":"vip"				
		}*/
	
	//db에서 남는 방 검색
	public boolean dbCheck(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//충돌검사로 안되는방을 검색하고 이방이 아닌 방을 보내준다.
		//any를 이용하면 충돌나는 방이 없는 경우 결과가 없다.
		StringBuffer sql=new StringBuffer();
		sql.append("select r.ROOM_Number");
		sql.append(" from ROOM r, ROOM_OPTION o");
		sql.append(" where r.ROOM_OPTION_ID=o.ROOM_OPTION_ID");		
		sql.append(" and o.ROOM_OPTION_NAME=?");
		sql.append(" and r.ROOM_NUMBER!=all(select r.ROOM_NUMBER");
		sql.append(" from ROOM r,  ROOM_OPTION o,  RESV re");
		sql.append(" where o.ROOM_OPTION_ID=r.ROOM_OPTION_ID and r.ROOM_NUMBER = re.ROOM_NUMBER");
		sql.append(" and o.ROOM_OPTION_NAME=?");
		sql.append(" and (to_Char(re.RESV_TIME, 'yyyy-mm-dd-hh24-mi-ss')<=?");
		sql.append(" and to_Char(re.END_TIME, 'yyyy-mm-dd-hh24-mi-ss')>=?))");
		sql.append(" order by r.ROOM_Number");
		System.out.println(sql.toString());
		try {
			pstmt=con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, json.get("option").toString());
			pstmt.setString(2, json.get("option").toString());
			pstmt.setString(3, json.get("end").toString());
			pstmt.setString(4, json.get("start").toString());			
			rs=pstmt.executeQuery();
			rs.last();
			int last=rs.getRow();
			rs.beforeFirst();			
			
			if (last>=1) {
				resvInfo=new ArrayList();
				while(rs.next()){
					resvInfo.add(rs.getString("ROOM_Number"));					
				};
					
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
	
	/*//방검색응답의 경우
		var msgExRommSearchResponse={
			"responseType":"room_search",
			"result":"yes",
			"available_room":[
				"203", "303", "403"
			]
		}
		
		//방이 없는경우
		var msgExRommSearchResponse2={
				"responseType":"room_search",
				"result":"no",			
			}*/
	
	public void response(){
		if (resvInfo!=null) {
			System.out.println("방있어");
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "room_search");
			responseJSON.put("result", "yes");
			JSONArray available_room=new JSONArray();
			for (int i = 0; i < resvInfo.size(); i++) {
				available_room.add(resvInfo.get(i));				
			}
			responseJSON.put("available_room", available_room);
			
			String msg=responseJSON.toJSONString();
			System.out.println(msg);
			serverThread.send(msg);
		}else {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "room_search");
			responseJSON.put("result", "no");			
			String msg=responseJSON.toJSONString();
			System.out.println(msg);
			serverThread.send(msg);
		}		
	}
}
