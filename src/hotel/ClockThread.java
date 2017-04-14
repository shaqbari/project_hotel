package hotel;

import java.util.Calendar;

public class ClockThread extends Thread{
	HotelMain main;
	
	
	public ClockThread(HotelMain main) {
		this.main=main;
				
		start();
	}
	
	
	
	public void run() {
		while (true) {
			try {
				this.sleep(1000);
				Calendar cal=Calendar.getInstance();//���������� ���������� instance�� ������ ���� ���� �����Ǿ� ������.
				main.la_time.setText(cal.getTime().toString());
				main.p_north.updateUI();				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
