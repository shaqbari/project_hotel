package hotel.server;

import org.json.simple.JSONObject;

import hotel.HotelMain;
import hotel.service.ChatBox;

public class ResponseChat {
	ServerThread serverThread;
	HotelMain main;
	JSONObject json;
	public ChatBox chatBox;//ChatPanel의 chatBox를 담을예정
	
	int count =0;
	
	public ResponseChat(ServerThread serverThread, JSONObject json) {
		this.serverThread = serverThread;
		this.main=serverThread.main;
		this.json = json;
		this.chatBox=serverThread.chatBox;
				
	}

	// 클라이언트가 메시지보냈을때 응답해보내는 메소드
	public void responseSend() {
		String content = json.get("content").toString();
		serverThread.area.append(content + "\n");//home패널의 area에 붙인다.
		chatBox.area.append(content + "\n");//chatbox에 붙인다.

		JSONObject responseJson = new JSONObject();
		responseJson.put("responseType", "chat");
		responseJson.put("content", content);
		serverThread.send(responseJson.toJSONString());

	}


}
