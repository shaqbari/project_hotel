package hotel;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
 
public class ResvModel extends AbstractTableModel{
	Vector<String> columnName=new Vector<String>(); //�÷���:��¥ 
	Vector <Vector> data=new Vector<Vector>(); //ȣ��
	HotelMain main;
	Connection con;
	Calendar cal;
	int yy;
	int mm;
	int dd;
	int lastDay;
	
	public ResvModel(Connection con,Calendar cal) {
		this.con=con; 
		//���� ��¥ �����Ͱ� ����
		this.cal=cal;
		yy=cal.get(Calendar.YEAR);
		mm=cal.get(Calendar.MONTH)+1;
		
		System.out.println("���� ����?"+(mm));
		
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
		String sql="select resv_id, hotel_user_id, room_number, to_char(resv_time, 'yy-mm-dd') as resv_time from resv where to_char(resv_time, 'yyyy')='"+yy+"' and to_char(resv_time,'mm')='"+DateUtil.getDateString(Integer.toString(mm))+"'order by room_number";
	
		try {
			pstmt=con.prepareStatement(sql);
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
						vec.addElement("X");
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