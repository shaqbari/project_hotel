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
	
	/*//id�ߺ�Ȯ�� ��û
		var msgExcheck1={
			"requestType":"idCheck",
			"room_number":204,
			"requestTime":"2017-04-17-18-19-23",
			"id_to_nick":"jsklsk",
		}*/
	
	
	//hoteluser�� ���� ����ϰ� membership�� ����Ѵ�.
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
			
			//last�� 0�̸� �ߺ����� ���°��̴�.
			if (last!=0) {
				overlap=false;
			}else {
				overlap=true;
			}
			
			System.out.println("overlap ���"+overlap);			
			
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
	
/*//id�ߺ�Ȯ�� ����
	var msgExCheck2{		//�ߺ��ȵ�
		"responseType":"idCheck",
		"result":"yes",
		
	}	
	var msgExCheck3{		//�ߺ���
		"responseType":"idCheck",
		"result":"no"
	}*/
	
	public void response(){
		if (!overlap) {//�ߺ����� �ʾ�����
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
