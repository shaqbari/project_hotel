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
						
		} catch (IOException e) {//client�� ������� �̿��ܿ� ����.
			e.printStackTrace();
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
	
	public void run() {
		while (flag) {
			listen();
		}
	}
}
