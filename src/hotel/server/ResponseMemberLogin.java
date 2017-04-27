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
	int result;
	int last;
	String hotel_user_id;
	String member_name;
	
	public ResponseMemberLogin(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		main=serverThread.main;
		con=main.con;
		
		this.json=json;
		
		dbCheck();
				
	}
	/*//회원로그인
		var msgEx2={
			"requestType":"membership_login",
			"room_number":204,
			"requestTime":"2017-04-17-18-19-23",
			"id_to_nick":"minjung",
			"password":1234
		}
		*/
		
	//회원로그인 서버 응답
	/*	var msgEx2={
			"responseType":"membership_login",
			"result":"yes",
			"hotel_user_id":1,
			"member_name":"김민정",
			"resv_id":3
			"resv_time":"2017-04-17-18-19-23",
			"stay:1		
		}*/
	
	//회원이 맞는지 확인
	public boolean dbCheck(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select * FROM MEMBERSHIP m where m.MEMBERSHIP_NICK=?");
		sql.append(" and m.MEMBERSHIP_PW=?");
		
		try {
			pstmt=con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, json.get("id_to_nick").toString());
			pstmt.setInt(2, Integer.parseInt(json.get("password").toString()));
			rs=pstmt.executeQuery();
			rs.last();
			result=rs.getRow();
			rs.beforeFirst();
			
			rs.next();
			hotel_user_id=rs.getString("HOTEL_USER_ID");
			member_name=rs.getString("MEMBERSHIP_NAME");
			
			//db에서 resv_id와 전화번호 확인					
			if (result>=1) {
				sql.delete(0, sql.length());
				sql.append("select  *");
				sql.append(" from RESV r, HOTEL_USER h, MEMBERSHIP m");
				sql.append(" where r.HOTEL_USER_ID=h.HOTEL_USER_ID and h.HOTEL_USER_ID=m.HOTEL_USER_ID");
				sql.append(" and  m.MEMBERSHIP_NICK=? and m.MEMBERSHIP_PW=?");
				pstmt = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				pstmt.setString(1, json.get("id_to_nick").toString());
				pstmt.setInt(2, Integer.parseInt(json.get("password").toString()));
				rs = pstmt.executeQuery();
				rs.last();
				last = rs.getRow();
				rs.beforeFirst();
				
				//마지막으로 예약한 정보를 받는다.
				if (last >= 1) {
					
					rs.next();
					resvInfo = new HashMap<String, String>();
					resvInfo.put("hotel_user_id", rs.getString("hotel_user_id"));
					resvInfo.put("member_name", rs.getString("MEMBERSHIP_NAME"));
					resvInfo.put("resv_time", rs.getString("resv_time"));
					resvInfo.put("resv_id", rs.getString("resv_id"));
					resvInfo.put("stay", rs.getString("stay"));
				} 
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
	
	/*
	//게스트로그인 서버 응답
	var msgEx2={
		"responseType":"membership_login",
		"result":"yes",
		"hotel_user_id":1,
		"member_name":"김민정",
		
		//아래는 예약했을때만 보낸다.
		"resv_id":3
		"resv_time":"2017-04-17-18-19-23",
		"stay:1		
	}*/
	
	public void response(){
		if (result>=1) {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "membership_login");
			responseJSON.put("result", "yes");
			if (last>=1) {
				responseJSON.put("hotel_user_id", resvInfo.get("hotel_user_id"));
				responseJSON.put("member_name", resvInfo.get("member_name"));
				responseJSON.put("resv_id", resvInfo.get("resv_id"));
				responseJSON.put("resv_time", resvInfo.get("resv_time"));
				responseJSON.put("stay", resvInfo.get("stay"));				
			}else{
				responseJSON.put("hotel_user_id", hotel_user_id);
				responseJSON.put("member_name", member_name);
			}
			
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}else {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "membership_login");
			responseJSON.put("result", "no");			
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}		
	}
}
