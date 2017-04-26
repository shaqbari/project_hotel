package hotel.server;

import org.json.simple.JSONObject;

import hotel.HotelMain;
import hotel.chat.ChatBox;

public class ResponseChat {
	ServerThread serverThread;
	HotelMain main;
	JSONObject json, responseJson;
	public ChatBox chatBox;//ChatPanel�� chatBox�� ��������
	
	int count =0;
	
	public ResponseChat(ServerThread serverThread, JSONObject json) {
		this.serverThread = serverThread;
		this.main=serverThread.main;
		this.json = json;
		if(count==0){
		ChatBox chatBox=new ChatBox();//1:1ä�ÿ� ���� �г�
		//main.p_chat.p_chat1.add(chatBox);
		chatBox.getServerThread(serverThread);//chatBox�� serverThread����
		
		this.chatBox=chatBox;
		}
		
		responseJson = new JSONObject();
	}

	// Ŭ���̾�Ʈ�� �޽����������� �����غ����� �޼ҵ�
	public void responseSend() {
		String content = json.get("content").toString();
		System.out.println(content);
		serverThread.area.append(content + "\n");
		chatBox.area.append(content + "\n");

		responseJson.put("responseType", "chat");
		responseJson.put("content", content);
		serverThread.send(responseJson.toJSONString());

	}


}
