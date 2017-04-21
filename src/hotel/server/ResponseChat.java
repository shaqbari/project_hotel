package hotel.server;

import org.json.simple.JSONObject;

public class ResponseChat {
	ServerThread serverThread;
	JSONObject json;
	
	
	public ResponseChat(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.json=json;
	}
	
	 public void send(){			
		String content=json.get("content").toString();
		serverThread.area.append(content+"\n");
		serverThread.chatBox.area.append(content+"\n");	
		serverThread.send(content);		
	}
	 
	 
}
