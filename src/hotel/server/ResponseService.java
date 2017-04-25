package hotel.server;

import org.json.simple.JSONObject;

public class ResponseService {
	ServerThread serverThread;
	JSONObject json,responseJson;
	
	
	public ResponseService(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.json=json;
		
		responseJson = new JSONObject();
		System.out.println("HotelClient �� ResponseService ����");
	}
	
	 public void send(){
		String content=json.get("content").toString();
		String requestTime=json.get("requestTime").toString();
		String room_number=json.get("room_number").toString();
		String order=(room_number+"ȣ���� "+requestTime+" �ð��� "+content+" �ֹ��ϼ̽��ϴ�"+"\n");
		serverThread.area.append(order);
		serverThread.servBox.area.append(order);	
		//serverThread.send(content);
		
		responseJson.put("responseType", "service");
		responseJson.put("content", content);
		System.out.println(responseJson.toString());
		serverThread.send(responseJson.toJSONString());		
	}
}
