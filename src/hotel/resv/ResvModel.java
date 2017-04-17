package hotel.resv;
 
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import hotel.HotelMain;
 
public class ResvModel extends AbstractTableModel{
	String TAG=this.getClass().getName();
	Vector<String> columnName=new Vector<String>(); //�÷���:��¥ 
	Vector <Vector> data=new Vector<Vector>(); //ȣ��
	HotelMain main;
	Connection con;
	Calendar cal;
	int yy;
	int mm;
	int dd;
	int lastDay;
	JTable table;
	
	public ResvModel(Connection con,Calendar cal, JTable table) {
		this.con=con; 
		//���� ��¥ �����Ͱ� ����
		this.cal=cal;
		this.table=table;
		yy=cal.get(Calendar.YEAR);
		mm=cal.get(Calendar.MONTH);
		
		System.out.println(TAG+" ���� ����?"+(mm));
		
		//�� ���� ������ �� ���ϱ� 
		cal.set(yy, mm+1 ,0);//�����޷� �켱 �� ��, �� ������ -1���� ���� �ٷ� �ش� ���� ��������.. 
		lastDay=cal.get(Calendar.DATE);
		
		int num=0;
		
		columnName.add("��¥");
		
		for(int i=num; i<lastDay; i++){
			num++;
			columnName.add(Integer.toString(num));
		}
	
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
		
		sql.append("select r.room_number, nvl(resv_id,0) as resv_id , nvl(hotel_user_id,0) as hotel_user_id ,  nvl(to_char(resv_time, 'yy-mm-dd'), '0000-00-00') as resv_time");
		sql.append(" from  room r  left outer join resv rv  on r.room_number = rv.room_number ");
		sql.append(" and to_char(resv_time, 'yyyy')='"+yy+"' and to_char(resv_time,'mm')='"+DateUtil.getDateString(Integer.toString(mm+1))+"' ");
		sql.append(" order by r.room_number");
		
		System.out.println(sql.toString());
	
		try {
			pstmt=con.prepareStatement(sql.toString());
			//pstmt.setString(1, Integer.toString(yy));
			//pstmt.setString(2, DateUtil.getDateString(Integer.toString(mm)));
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Vector vec=new Vector();
				
				//�� ���� ��¥���� ���� �ݺ��� 
				vec.add(rs.getInt("room_number"));
				
				for(int i=1;i<=lastDay;i++){
					//������ i�� ��¥�̹Ƿ�, �� ��¥�� rs�� ��¥�� ��ġ�ϸ� ���� �簢�� ����!!
					int date=Integer.parseInt(rs.getString("resv_time").split("-")[2]);
					
					if(i==date){
						vec.addElement("O");
						//System.out.println(i+"�Ͽ� ����߰�");
						
					}else{
						vec.addElement(" ");
					}
					//System.out.print(i+"��,");
				}
				System.out.println("");
				data.add(vec);
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