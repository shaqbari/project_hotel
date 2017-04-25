package hotel.server;

import org.json.simple.JSONObject;

public class ResponseService {
	ServerThread serverThread;
	JSONObject json,responseJson;
	
	
	public ResponseService(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.json=json;
		
		responseJson = new JSONObject();
		System.out.println("HotelClient 의 ResponseService 실행");
	}
	
	 public void send(){
		String content=json.get("content").toString();
		String requestTime=json.get("requestTime").toString();
		String room_number=json.get("room_number").toString();
		String order=(room_number+"호에서 "+requestTime+" 시간에 "+content+" 주문하셨습니다"+"\n");
		serverThread.area.append(order);
		serverThread.servBox.area.append(order);	
		//serverThread.send(content);
		
		responseJson.put("responseType", "service");
		responseJson.put("content", content);
		System.out.println(responseJson.toString());
		serverThread.send(responseJson.toJSONString());		
	}
}
