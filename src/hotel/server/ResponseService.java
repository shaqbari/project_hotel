package hotel.server;

import org.json.simple.JSONObject;

public class ResponseService {
	ServerThread serverThread;
	JSONObject json;
	
	
	public ResponseService(ServerThread serverThread, JSONObject json) {
		this.serverThread=serverThread;
		this.json=json;
	}

}
