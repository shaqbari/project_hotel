package hotel.server;

import org.json.simple.JSONObject;

import hotel.chat.ChatPanel;

public class ResponseService {
	ServerThread serverThread;
	JSONObject json;
	
	
	public ResponseService(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.json=json;
	}
	
	 public void send(){			
		String content=json.get("content").toString();
		String requestTime=json.get("requestTime").toString();
		String room_number=json.get("room_number").toString();
		String order=(room_number+"호에서 "+requestTime+" 시간에 "+content+" 주문하셨습니다"+"\n");
		serverThread.area.append(order);
		serverThread.chatBox.area.append(order+"\n");	
		serverThread.send(order);		
	}
}
