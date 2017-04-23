package hotel.server;

import org.json.simple.JSONObject;

public class ResponseChat {
	ServerThread serverThread;
	JSONObject json, responseJson;

	public ResponseChat(ServerThread serverThread, JSONObject json) {
		this.serverThread = serverThread;
		this.json = json;

		responseJson = new JSONObject();
	}

	// 클라이언트가 메시지보냈을때 응답해보내는 메소드
	public void responseSend() {
		String content = json.get("content").toString();
		serverThread.area.append(content + "\n");
		serverThread.chatBox.area.append(content + "\n");

		responseJson.put("responseType", "chat");
		responseJson.put("content", content);
		serverThread.send(responseJson.toJSONString());

	}


}
