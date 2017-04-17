/*싱글톤으로 만들어보자*/

package hotel.main;

import java.util.Calendar;

import hotel.HotelMain;

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
				Calendar cal=Calendar.getInstance();//멤버변수로 만들어버리면 instance를 얻어오는 순간 값이 고정되어 버린다.
				main.la_time.setText(cal.getTime().toString());
				main.p_north.updateUI();				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
