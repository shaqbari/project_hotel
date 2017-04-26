package hotel.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JTextArea;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import hotel.HotelMain;
import hotel.chat.ChatBox;

public class ServerThread extends Thread{
	HotelMain main;
	Socket socket;

	public JTextArea area;//HomePanel의 area를 담을예정
	//public ChatBox chatBox;//ChatPanel의 chatBox를 담을예정
	
	BufferedReader buffr;
	BufferedWriter buffw;
		
	Boolean flag=true;
	public Vector<ServerThread>serviceThreadList=new Vector<ServerThread>();
	
	ChatBox chatBox;
	public boolean chatOff=true;
	
	
	public ServerThread(HotelMain main, Socket socket) {
		this.main=main;
		this.socket=socket;
		
		this.area=main.p_home.area;
		//this.chatBox=chatBox;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		this.start();
	}
	
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			JSONObject json=null;		
			JSONParser parser=new JSONParser();
			
			//받은 json msg를 parsing한다.
			try {
				json=(JSONObject)parser.parse(msg);					
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			String requestType=json.get("requestType").toString();
			
			//파싱결과 requestType에따라 다른 반응을 한다.
			if (requestType.equalsIgnoreCase("chat")) {

				//serverThreadList.addElement(this);
				if(chatOff){
					chatOff=false;
					chatBox=new ChatBox();
					chatBox.getServerThread(this);
				}
				ResponseChat responseChat=new ResponseChat(this, json);	
				responseChat.responseSend();
			
			}else if (requestType.equalsIgnoreCase("service")) {
				
				//serviceThreadList.addElement(this);
				ResponseService responseService=new ResponseService(this, json);
				responseService.send();
				
			}else if (requestType.equalsIgnoreCase("room_search")) {
				System.out.println("남는방검색들어왔어");
				ResponseRoomSearch roomSearch=new ResponseRoomSearch(this, json);
				roomSearch.response();				
				
			}else if (requestType.equalsIgnoreCase("guest_resv")) {
				System.out.println("비회원 예약들어왔어");
				ResponseGuestResv guestResv=new ResponseGuestResv(this, json);
				guestResv.response();
				
			}else if (requestType.equalsIgnoreCase("member_resv")) {
				ResponseMemberResv memberResv=new ResponseMemberResv(this, json);
				memberResv.response();
				
			}else if (requestType.equalsIgnoreCase("guest_login")) {
				ResponseGuestLogin guestLogin=new ResponseGuestLogin(this, json);
				guestLogin.response();
								
			}else if (requestType.equalsIgnoreCase("membership_login")) {
				ResponseMemberLogin memberLogin=new ResponseMemberLogin(this, json);
				memberLogin.response();
				
			}else if(requestType.equalsIgnoreCase("membership_regist")){
				ResponseMemberResist memberResist=new ResponseMemberResist(this, json);
				memberResist.response();
				
			}
			
						
		} catch (IOException e) {//client가 나갈경우 이예외에 들어간다.
			//e.printStackTrace();
			
			
			//main.p_chat.p_chat1.remove(responseChat.chatBox);//chat패널에서 chatBox를 지운다.
			//main.p_chat.p_chat1.updateUI();
			
			flag=false;//현재의 쓰레드를 죽인다.
			main.serverThreadList.remove(this);//벡터에서 이 스레드를 제거
			//serviceThreadList.remove(this);
			area.append("1명 퇴장후 현재 접속자: "+main.serverThreadList.size()+"\n");
			//여기서 stream 닫나?
		}
	};
	
	//말하기
	public void send(String msg){
		try {
			buffw.write(msg+"\n"); //보낼때 한줄내려야 끝난줄 안다.
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	//관리자가 채팅 보내는 메소드
		public void chatSend(String msg) {
			JSONObject sendJSON=new JSONObject();
			sendJSON.put("responseType", "chat");
			sendJSON.put("content", msg);
			send(sendJSON.toJSONString());
		}
	
	public void run() {
		while (flag) {
			listen();
		}
	}
}
