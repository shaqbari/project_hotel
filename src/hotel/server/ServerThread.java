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
import hotel.chat.ServiceBox;

public class ServerThread extends Thread{
	HotelMain main;
	Socket socket;

	public JTextArea area;//HomePanel�� area�� ��������
	public ChatBox chatBox;//ChatPanel�� chatBox�� ��������
	public ServiceBox servBox;//ChatPanel�� serviceBox�� ��������
	BufferedReader buffr;
	BufferedWriter buffw;
		
	Boolean flag=true;
	public Vector<ServerThread>serviceThreadList=new Vector<ServerThread>();
	
	public ServerThread(HotelMain main, Socket socket, ChatBox chatBox) {
		this.main=main;
		this.socket=socket;
		
		this.area=main.p_home.area;
		this.chatBox=chatBox;
		
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
			
			//���� json msg�� parsing�Ѵ�.
			try {
				json=(JSONObject)parser.parse(msg);					
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			String requestType=json.get("requestType").toString();
			
			//�Ľ̰�� requestType������ �ٸ� ������ �Ѵ�.
			if (requestType.equalsIgnoreCase("chat")) {
				ResponseChat responseChat=new ResponseChat(this, json);	
				responseChat.responseSend();
			
			}else if (requestType.equalsIgnoreCase("service")) {
				servBox = new ServiceBox();
				main.p_chat.p_serv.add(servBox);
				servBox.getServerThread(this);
				//serviceThreadList.addElement(this);
				ResponseService responseService=new ResponseService(this, json);
				responseService.send();
				
			}else if (requestType.equalsIgnoreCase("room_search")) {
				System.out.println("���¹�˻����Ծ�");
				ResponseRoomSearch roomSearch=new ResponseRoomSearch(this, json);
				roomSearch.response();				
				
			}else if (requestType.equalsIgnoreCase("guest_resv")) {
				
				
			}else if (requestType.equalsIgnoreCase("member_resv")) {
				System.out.println("������Ծ�");
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
			
						
		} catch (IOException e) {//client�� ������� �̿��ܿ� ����.
			e.printStackTrace();
			
			
			main.p_chat.p_chat1.remove(this.chatBox);//chat�гο��� chatBox�� �����.
			main.p_chat.p_chat1.updateUI();
			
			flag=false;//������ �����带 ���δ�.
			main.serverThreadList.remove(this);//���Ϳ��� �� �����带 ����
			area.append("1�� ������ ���� ������: "+main.serverThreadList.size()+"\n");
			//���⼭ stream �ݳ�?
		}
	};
	
	//���ϱ�
	public void send(String msg){
		try {
			buffw.write(msg+"\n"); //������ ���ٳ����� ������ �ȴ�.
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	//�����ڰ� ä�� ������ �޼ҵ�
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
