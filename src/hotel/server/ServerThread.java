package hotel.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

import hotel.HotelMain;

public class ServerThread extends Thread{
	HotelMain main;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	JTextArea area;
	
	Boolean flag=true;
	
	public ServerThread(HotelMain main, Socket socket) {
		this.main=main;
		this.socket=socket;
		area=main.p_home.area;
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
			main.p_home.area.append(msg+"\n");			
			
			
			
			
			send(msg);
						
		} catch (IOException e) {//client가 나갈경우 이예외에 들어간다.
			e.printStackTrace();
			flag=false;//현재의 쓰레드를 죽인다.
			main.serverThreadList.remove(this);//벡터에서 이 스레드를 제거
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
	
	public void run() {
		while (flag) {
			listen();
		}
	}
}
