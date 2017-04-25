package hotel.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import hotel.HotelMain;

public class ResponseMemberResv {
	ServerThread serverThread;
	HotelMain main;
	Connection con;
	JSONObject json;
	int resv_id=0;
	boolean flag=false;
	
	/*//회원 방예약의 경우
	var msgExResv={
		"room_number":303,
		"reqeustType":"member_resv",
		"requestTime":"2017-04-17-18-19-23",
		"hotel_user_id":7,
		"resv_room_number": 202,
		"resv_time":"2017-04-20-14-00-00",
		"end_time":"2017-04-23-12-00-00",
		"stay":3
	}*/	
	
	public ResponseMemberResv(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.main=serverThread.main;
		con=main.con;
		
		this.json=json;
		
		dbCheck();
				
	}
	
/*	//회원 방예약의 경우
		var msgExResv={
			"room_number":303,
			"reqeustType":"member_resv",
			"requestTime":"2017-04-17-18-19-23",
			"hotel_user_id":8,
			"resv_room_number": 202,
			"resv_time":"2017-04-20-14-00-00",
			"end_time":"2017-04-23-12-00-00",
			"stay":3
		}*/
	
	//db에서 체크
	public void dbCheck(){
		System.out.println("dbchech들어간다.");
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("insert into RESV (RESV_ID, HOTEL_USER_ID, ROOM_NUMBER, RESV_TIME, END_TIME, STAY, RESV_REGTIME)");
		sql.append(" values(seq_resv.nextVal, ?, ?, to_date(?, 'yyyy-mm-dd-hh24-mi-ss'), to_date(?, 'yyyy-mm-dd-hh24-mi-ss'), ?, to_date(?, 'yyyy-mm-dd-hh24-mi-ss'))");
		
		try {
			
			//requestTime도 두자리가 되야한다 DateUtil이용!! 조심하자
			pstmt=con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, Integer.parseInt(json.get("hotel_user_id").toString()));
			pstmt.setInt(2, Integer.parseInt(json.get("resv_room_number").toString()));
			pstmt.setString(3, json.get("resv_time").toString());
			pstmt.setString(4, json.get("end_time").toString());
			pstmt.setInt(5, Integer.parseInt(json.get("stay").toString()));
			pstmt.setString(6, json.get("requestTime").toString());
			int result=pstmt.executeUpdate();
			
			if (result==1) {
				sql.delete(0, sql.length());
				sql.append("select seq_resv.currVal from dual");//sequence를 알기위해 바로 붙인다.
				
				pstmt=con.prepareStatement(sql.toString());
				rs=pstmt.executeQuery();
				
				rs.next();
				resv_id=rs.getInt("currVal");
				
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
	
/*	//회원 방예약 응답
		var msgExMemberResv2{		
			"responseType":"member_resv",
			"result":"yes",
			"resv_id":"24"
			
		}	
		var msgExMemberResv2{		
			"responseType":"member_resv",
			"result":"no"
		}
	*/
	public void response(){
		if (resv_id!=0) {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "member_resv");
			responseJSON.put("result", "yes");
			responseJSON.put("resv_id", resv_id);
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}else {
			JSONObject responseJSON=new JSONObject();
			responseJSON.put("responseType", "member_resv");
			responseJSON.put("result", "no");			
			String msg=responseJSON.toJSONString();
			serverThread.send(msg);
		}		
	}
}
