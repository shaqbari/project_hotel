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

	// Ŭ���̾�Ʈ�� �޽����������� �����غ����� �޼ҵ�
	public void responseSend() {
		String content = json.get("content").toString();
		serverThread.area.append(content + "\n");
		serverThread.chatBox.area.append(content + "\n");

		responseJson.put("responseType", "chat");
		responseJson.put("content", content);
		serverThread.send(responseJson.toJSONString());

	}


}
