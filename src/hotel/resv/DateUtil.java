package hotel.resv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	//날짜가 2자 미만일경우 0앞에 붙이기 
	public static String getDateString(String n){
		String str=null;
		
		if(n.length()<2){
			str="0"+n;
		}else{
			str=n;
		}
		return str;
	}
	

	//특정 날짜 두개에서 그 차이값을 알고 싶을 때 사용할수 있는메소드
	public static int getDiffDate(String start, String end){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		//SimpleDateFormat을 이용해 Date객체로 바꾸고
		//getTime메소드를 이용하면 시간을 long값으로 계산해 리턴해준다.
		long diffDays=0;
		
		try {
			Date startDate= formatter.parse(start);
			Date endDate= formatter.parse(end);
			
			//시간차이를 시간, 분, 초를 곱한 값으로 나누면 하루단위가 나온다.
			long diff=endDate.getTime()-startDate.getTime();
			diffDays=diff/(24*60*60*1000);			
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		return (int) diffDays;
	}
	
	
	//특정날짜에서 특정날짜를 더한 날을 알고 싶을 때 사용한다.
	public static String getPlusDate(String dateString, int plus){
		//HH가 0~24시간이다.
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREA);
		
		String result=null;
		
		try {
			Date date=formatter.parse(dateString);
			Long plusDate=date.getTime()+(plus*24*60*60*1000);
			Date plusDate2=new Date(plusDate);
			
			//format을 이용하면 문자열로 만들 수 있다.
			result=formatter.format(plusDate2);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
/*	public static void main(String[] args) {
		for(int i=1;i<=12;i++){
			System.out.println(getDateString(Integer.toString(i)));
		}
	}
*/	
}
