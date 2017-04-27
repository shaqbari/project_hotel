package hotel.resv;
 
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;
 
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
 
import hotel.HotelMain;
 
public class ResvModel extends AbstractTableModel{
	String TAG=this.getClass().getName();
	Vector<String> columnName=new Vector<String>(); //�÷���:��¥ 
	Vector <Vector> data=new Vector<Vector>(); //ȣ��
	HotelMain main;
	Connection con;
	Calendar cal, temp_cal, resv_cal, stay_cal;
	int yy;
	int mm;
	int dd;
	int lastDay;
	JTable table;
	int date;
	int stay;
 
	
	
	public ResvModel(Connection con,Calendar cal,JTable table) {
		this.con=con; 
		//���� ��¥ �����Ͱ� ����
		this.cal=cal;
		this.table=table;
			
		yy=cal.get(Calendar.YEAR);
		mm=cal.get(Calendar.MONTH);
		
		temp_cal=Calendar.getInstance();
		resv_cal=Calendar.getInstance();
		stay_cal=Calendar.getInstance();
		
		//System.out.println(TAG+" ���� ����?"+(mm));
		
		//�� ���� ������ �� ���ϱ� 
		cal.set(yy, mm+1 ,0);//�����޷� �켱 �� ��, �� ������ -1���� ���� �ٷ� �ش� ���� ��������.. 
		lastDay=cal.get(Calendar.DATE);
		
		int num=0;
		
		columnName.add("R"+"\\"+"D");
		
		for(int i=num; i<lastDay; i++){
			num++;
			columnName.add(Integer.toString(num));
		}
	
		//��ũ���߱� ���� ���� �޷� ǥ�õڿ� mm�� +1�� �Ѵ�.
		mm=mm+1;
		getList();
		
	}
	
 
	/*-------------------------------------------------------
	 * Room ���̺��� room_number�� ù��° ���� ������ 
	 *-------------------------------------------------------*/
	 
	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
 
		/*
		String sql="select resv_id, hotel_user_id, room_number, to_char(resv_time, 'yy-mm-dd') as resv_time from resv where to_char(resv_time, 'yyyy')=?";
		
		sql+=" and to_char(resv_time,'mm')=?";
		*/
		StringBuffer sql=new StringBuffer();
		
		
		
		sql.append("select  room_number, nvl(v.resv_id,0) as resv_id, hotel_user_id,");
		sql.append(" resv_time,  nvl(resv_detail_id,0) as resv_detail_id,");
		sql.append(" nvl(to_char(stay_date,'yyyy-mm-dd'),'0000-00-00') as stay_date,   nvl(r.resv_id,0) as resv_id");
		sql.append(" from view_resv_list v left outer join resv_detail r");
		sql.append(" on v.resv_id = r.resv_id");
		//sql.append(" and to_char(resv_time, 'yyyy')='"+yy+"' and to_char(resv_time,'mm')='"+DateUtil.getDateString(Integer.toString(mm+1))+"' ");
		//sql.append(" and to_char(stay_date, 'yyyy')='"+yy+"' and to_char(stay_date,'mm')='"+DateUtil.getDateString(Integer.toString(mm+1))+"' ");
			
		//System.out.println("sql is "+sql.toString());
	
		try {
			pstmt=con.prepareStatement(sql.toString());
			//pstmt.setString(1, Integer.toString(yy));
			//pstmt.setString(2, DateUtil.getDateString(Integer.toString(mm)));
			
			rs=pstmt.executeQuery();
			//int count=0;
			int ho=0;
			Vector ho_vec=null; //�ߺ��ɰ�쿣 �� ���� ����
			
			while(rs.next()){
				//	count++;
				
				/*------------------------------------------------
				 �ߺ����� ���� �����
				------------------------------------------------*/
				if(ho != rs.getInt("room_number")){
					ho_vec= new Vector();
					ho_vec.add(rs.getInt("room_number"));
					data.add(ho_vec);
					
					for(int i=1;i<=lastDay;i++){
						
						int y=Integer.parseInt(rs.getString("resv_time").split("-")[0]);
						int m=Integer.parseInt(rs.getString("resv_time").split("-")[1]);
						int date=Integer.parseInt(rs.getString("resv_time").split("-")[2]); 
						
						int year=Integer.parseInt(rs.getString("stay_date").split("-")[0]);
						int month=Integer.parseInt(rs.getString("stay_date").split("-")[1]);
						int stay=Integer.parseInt(rs.getString("stay_date").split("-")[2]);
						
						
						temp_cal.set(yy, mm, i); //�ݺ��� ������ ��¥ ���� i�� 
						resv_cal.set(y, m,date); //resv ���̺��� ���೯¥
						stay_cal.set(year, month,stay); //resv_detail ���̺��� stay_date�� ��¥ ����
						
						if((temp_cal.get(Calendar.YEAR)==stay_cal.get(Calendar.YEAR))&&(temp_cal.get(Calendar.MONTH)==stay_cal.get(Calendar.MONTH))&&(temp_cal.get(Calendar.DATE)==stay_cal.get(Calendar.DATE)))
							{
								if((temp_cal.get(Calendar.YEAR)==resv_cal.get(Calendar.YEAR))&&(temp_cal.get(Calendar.MONTH)==resv_cal.get(Calendar.MONTH))&&(temp_cal.get(Calendar.DATE)==resv_cal.get(Calendar.DATE)))
								{
									//���� �ٸ� �������� ������ �� �ֵ��� ù���� �ٸ������� ä��
									ho_vec.addElement("");
								}	
								else{
									ho_vec.addElement(" ");
							
								}
							}
						else{
							ho_vec.addElement("  ");
						}
						//System.out.print(i+"��,");
					}
					
				/*------------------------------------------------
				 �ߺ��Ǵ� �����
				------------------------------------------------*/
				}else{//�̹� ������ ���� ȣ�����...
					for(int i=1;i<=lastDay;i++){
						
						int y=Integer.parseInt(rs.getString("resv_time").split("-")[0]);
						int m=Integer.parseInt(rs.getString("resv_time").split("-")[1]);
						int date=Integer.parseInt(rs.getString("resv_time").split("-")[2]); 
			
						int year=Integer.parseInt(rs.getString("stay_date").split("-")[0]);
						int month=Integer.parseInt(rs.getString("stay_date").split("-")[1]);
						int stay=Integer.parseInt(rs.getString("stay_date").split("-")[2]);
						
						
						temp_cal.set(yy, mm, i); //�ݺ��� ������ ��¥ ���� i�� 
						resv_cal.set(y, m,date); //resv ���̺��� ���೯¥
						stay_cal.set(year, month,stay); //resv_detail ���̺��� stay_date�� ��¥ ����
						
						//ȣ���� ������ ���೯¥�� �ٸ� ���->�̹� �����ϴ� ȣ���Ϳ� ���, �ش��ϴ� stay_date�� ĥ�Ѵ�.
						if((temp_cal.get(Calendar.YEAR)==stay_cal.get(Calendar.YEAR))&&(temp_cal.get(Calendar.MONTH)==stay_cal.get(Calendar.MONTH))&&(temp_cal.get(Calendar.DATE)==stay_cal.get(Calendar.DATE)))
						{
							if((temp_cal.get(Calendar.YEAR)==resv_cal.get(Calendar.YEAR))&&(temp_cal.get(Calendar.MONTH)==resv_cal.get(Calendar.MONTH))&&(temp_cal.get(Calendar.DATE)==resv_cal.get(Calendar.DATE)))
							{
								//���� �ٸ� �������� ������ �� �ֵ��� ù���� �ٸ������� ä��
								String str=(String)ho_vec.get(i);
								str="";
								ho_vec.set(i, str);
							
							}	
							else{
								String str=(String)ho_vec.get(i);
								str=" ";
								ho_vec.set(i, str);
							
						
							}
						}
					}
				}
				//�� ���� ��¥���� ���� �ݺ��� 
				//System.out.println("");
				//System.out.println(count);
				ho=rs.getInt("room_number");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
			
	}
	
	public int getColumnCount() {
		return columnName.size();
	}
	
	public int getRowCount() {
		return data.size();
	}
	
	public String getColumnName(int col) {
		return columnName.get(col);
	}
	
	
 
	public Object getValueAt(int row, int col) {
		return data.get(row).get(col);
	}
 
	
	
}