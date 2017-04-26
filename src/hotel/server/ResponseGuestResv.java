package hotel.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import hotel.HotelMain;
import hotel.resv.DateUtil;

public class ResponseGuestResv {
	ServerThread serverThread;
	HotelMain main;
	Connection con;
	JSONObject json;
	int resv_id = 0;// �����ȣ�� ���� ����
	boolean flag = false;

	/*
	 * //ȸ�� �濹���� ��� var msgExResv={ "room_number":303,
	 * "reqeustType":"member_resv", "requestTime":"2017-04-17-18-19-23",
	 * "hotel_user_id":7, "resv_room_number": 202,
	 * "resv_time":"2017-04-20-14-00-00", "end_time":"2017-04-23-12-00-00",
	 * "stay":3 }
	 */

	public ResponseGuestResv(ServerThread serverThread, JSONObject json) {
		this.serverThread = serverThread;
		this.main = serverThread.main;
		con = main.con;

		this.json = json;

		dbCheck();

	}

	/*
	 * //��ȸ�� �濹���� ��� var msgExResv={
	 *  "room_number":303,
	 * "reqeustType":"guest_resv",
	 *  "requestTime":"2017-04-17-18-19-23",
	 * "guest_name":"������",
	 *  "guest_phone":"010-2222-3333",
	 *   "resv_room_number": 602,
	 *    "resv_time":"2017-04-20-14-00-00",
	 *     "end_time":"2017-04-23-12-00-00",
	 * "stay":3 }
	 */

	// dbüũ �� �Է� hoteluser��� -> guest ��� -> resv��� -> resv_detail���
	public void dbCheck() {
		System.out.println("dbchech����.");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		//1. hotel_user ���̺� �߰�
		sql.append("insert into HOTEL_USER (HOTEL_USER_ID, IS_GUEST)");
		sql.append(" VALUES (seq_hotel_user.nextVal, 1)");

		try {
			pstmt = con.prepareStatement(sql.toString());
			int result1 = pstmt.executeUpdate();
			
			//2. guest���̺� �߰�
			if (result1 == 1) {						
				sql.delete(0, sql.length());
				sql.append(
						"insert into GUEST (GUEST_ID, HOTEL_USER_ID, GUEST_NAME, GUEST_PHONE)");
				sql.append(
						" VALUES (seq_guest_id.nextVal, seq_hotel_user.currVal, ?, ?)");
				pstmt=con.prepareStatement(sql.toString());
				pstmt.setString(1, json.get("guest_name").toString());
				pstmt.setString(2, json.get("guest_phone").toString());
				int result2=pstmt.executeUpdate();				
				
				//3.resv ���̺� �߰�
				if (result2==1) {
					// requestTime�� ���ڸ��� �Ǿ��Ѵ� DateUtil�̿�!! ��������
					sql.delete(0, sql.length());
					sql.append("insert into RESV (RESV_ID, HOTEL_USER_ID, ROOM_NUMBER, RESV_TIME, END_TIME, STAY, RESV_REGTIME)");
					sql.append(" values(seq_resv.nextVal, seq_hotel_user.currVal, ?, to_date(?, 'yyyy-mm-dd-hh24-mi-ss'), to_date(?, 'yyyy-mm-dd-hh24-mi-ss'), ?, to_date(?, 'yyyy-mm-dd-hh24-mi-ss'))");
					
					
					pstmt = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					pstmt.setInt(1, Integer.parseInt(json.get("resv_room_number").toString()));
					pstmt.setString(2, json.get("resv_time").toString());
					pstmt.setString(3, json.get("end_time").toString());
					pstmt.setInt(4, Integer.parseInt(json.get("stay").toString()));
					pstmt.setString(5, json.get("requestTime").toString());
					int result3 = pstmt.executeUpdate();
					
					//resv_id���
					if (result3 == 1) {
						sql.delete(0, sql.length());
						// sequence�� �˱����� �ٷ� ���δ�.
						sql.append("select seq_resv.currVal from dual");
						pstmt = con.prepareStatement(sql.toString());
						rs = pstmt.executeQuery();

						rs.next();
						resv_id = rs.getInt("currVal");

						//4.resv_detail ���̺� �߰�
						// resv_detail���� ��¥�� �Ϸ羿 ���ϸ鼭 �߰�����.
						String stay_date = json.get("resv_time").toString();
						for (int i = 0; i < Integer.parseInt(json.get("stay").toString()); i++) {
							sql.delete(0, sql.length());
							sql.append("INSERT INTO RESV_DETAIL (RESV_DETAIL_ID, STAY_DATE, RESV_ID)");
							sql.append(
									"VALUES (seq_resv_detail.nextVal, to_date(?, 'yyyy-mm-dd-hh24-mi-ss'), seq_resv.currVal)");
							pstmt = con.prepareStatement(sql.toString());

							if (i == 0) {
								pstmt.setString(1, stay_date);
							} else {
								stay_date = DateUtil.getPlusDate(stay_date, 1);
								pstmt.setString(1, stay_date);
							}
							pstmt.executeUpdate();

						}
					} 
				}

			}

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

	/*
	 * //��ȸ�� �濹�� ���� 
	 * var msgExMemberResv2={
	 * "responseType":"guest_resv",
	 * "result":"yes", "resv_id":"24" 
	 * }
	 * 
	 *  var msgExMemberResv2{ "responseType":"guest_resv", "result":"no" }
	 */
	public void response() {
		if (resv_id != 0) {
			JSONObject responseJSON = new JSONObject();
			responseJSON.put("responseType", "guest_resv");
			responseJSON.put("result", "yes");
			responseJSON.put("resv_id", resv_id);
			String msg = responseJSON.toJSONString();
			serverThread.send(msg);
		} else {
			JSONObject responseJSON = new JSONObject();
			responseJSON.put("responseType", "guest_resv");
			responseJSON.put("result", "no");
			String msg = responseJSON.toJSONString();
			serverThread.send(msg);
		}
	}
}
