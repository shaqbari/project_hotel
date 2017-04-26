package hotel.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import hotel.HotelMain;

public class ResponseMemberResist {
	ServerThread serverThread;
	HotelMain main;
	Connection con;
	JSONObject json;
	
	int membership_id=0;
	
	public ResponseMemberResist(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		main=serverThread.main;
		con=main.con;
		
		this.json=json;
		
		dbCheck();
				
	}
	
	/*//비회원 방예약의 경우		
	//클라이언트 회원가입 요청
	var msgExResgist={
		"room_number":204,
		"requestType":"membership_regist",
		"requestTime":"2017-04-17-18-19-23",
		"member_nick":"jsklsk",
		"member_pw":"1234",
		"member_name":"김성현",
		"member_phone":"010-2322-1111"",
		"member_email":"syssk@aewr.com",
		"member_gender":"남",
		"member_birthday":"1987-05-03"
	}*/
	
	
	//hoteluser에 먼저 등록하고 membership에 등록한다.
	public boolean dbCheck(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("insert into HOTEL_USER (HOTEL_USER_ID, IS_GUEST) VALUES (seq_hotel_user.nextVal, 0)");
				
		try {
			pstmt=con.prepareStatement(sql.toString());
			int result1=pstmt.executeUpdate();
			
			if (result1==1) {
				sql.delete(0, sql.length());
				sql.append("insert into MEMBERSHIP (MEMBERSHIP_ID, HOTEL_USER_ID, MEMBERSHIP_NAME, MEMBERSHIP_NICK, MEMBERSHIP_PW, MEMBERSHIP_REGDATE, MEMBERSHIP_PHONE, MEMBERSHIP_EMAIL, MEMBERSHIP_GENDER, MEMBERSHIP_BIRTHDAY)");
				sql.append(" VALUES (seq_membership.nextVal, seq_hotel_user.currVal, ?, ?, ?, to_date(?, 'yyyy-mm-dd-hh24-mi-ss'), ?, ?, ?, to_date(?, 'yyyy-mm-dd'))");
				System.out.println(sql.toString());
				pstmt=con.prepareStatement(sql.toString());
				pstmt.setString(1, json.get("member_name").toString());
				pstmt.setString(2, json.get("member_nick").toString());
				pstmt.setInt(3, Integer.parseInt(json.get("member_pw").toString()));
				pstmt.setString(4, json.get("requestTime").toString());
				pstmt.setString(5, json.get("member_phone").toString());
				pstmt.setString(6, json.get("member_email").toString());
				pstmt.setString(7, json.get("member_gender").toString());
				pstmt.setString(8, json.get("member_birthday").toString());				
				int result2=pstmt.executeUpdate();
				
				if (result2==1) {
					sql.delete(0, sql.length());
					// sequence를 알기위해 바로 붙인다.
					sql.append("select seq_membership.currVal from dual");
					pstmt = con.prepareStatement(sql.toString());
					rs = pstmt.executeQuery();

					rs.next();
					membership_id = rs.getInt("currVal");										
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
	
/*	//회원가입 응답
	var msgExMemberResv2{		
		"responseType":"membership_regist",
		"result":"yes"		,
		"membership_id":23
		
	}	
	var msgExMemberResv2{		
		"responseType":"membership_regist",
		"result":"no"
	}*/
	
	public void response(){
		if (membership_id!=0) {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "membership_regist");
			responseJSON.put("result", "yes");
			responseJSON.put("membership_id", membership_id);
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}else {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "membership_regist");
			responseJSON.put("result", "no");			
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}		
	}
}
