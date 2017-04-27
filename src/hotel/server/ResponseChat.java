package hotel.server;

import org.json.simple.JSONObject;

import hotel.HotelMain;
import hotel.service.ChatBox;

public class ResponseChat {
	ServerThread serverThread;
	HotelMain main;
	JSONObject json;
	public ChatBox chatBox;//ChatPanel�� chatBox�� ��������
	
	int count =0;
	
	public ResponseChat(ServerThread serverThread, JSONObject json) {
		this.serverThread = serverThread;
		this.main=serverThread.main;
		this.json = json;
		this.chatBox=serverThread.chatBox;
				
	}

	// Ŭ���̾�Ʈ�� �޽����������� �����غ����� �޼ҵ�
	public void responseSend() {
		String content = json.get("content").toString();
		serverThread.area.append(content + "\n");//home�г��� area�� ���δ�.
		chatBox.area.append(content + "\n");//chatbox�� ���δ�.

		JSONObject responseJson = new JSONObject();
		responseJson.put("responseType", "chat");
		responseJson.put("content", content);
		serverThread.send(responseJson.toJSONString());

	}


}
