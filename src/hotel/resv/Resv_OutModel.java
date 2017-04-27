package hotel.resv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import hotel.HotelMain;

public class Resv_OutModel extends AbstractTableModel{
	Connection con;
	HotelMain main;
	
	Vector<String> columnName = new Vector<String>();
	Vector<Vector> list = new Vector<Vector>();
	int col;
	ResvModel rm;
	
	public Resv_OutModel(HotelMain main,Connection con,int col){
		this.main=main;
		this.con=con;
		this.col=col;
		
		getList();
	}
	
	//디비연동, con 객체 main으로부터 넘겨받을것...
	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//String sql="select check_io_id,resv_id,to_char(check_out_time,'yyyy-mm-dd-hh24-mi') as check_out_time from check_io where  to_char(check_out_time,'dd')=?";
		StringBuffer sql= new StringBuffer();
		sql.append("select check_io_id,r.resv_id,to_char(check_in_time,'yyyy-mm-dd hh24-mi') as check_in_time,to_char(check_out_time,'yyyy-mm-dd hh24-mi') as check_out_time ,to_char(resv_time,'yyyy-mm-dd hh24-mi') as resv_time ");
		sql.append(" from resv r , check_io c");
		sql.append(" where r.resv_id=c.resv_id");
		sql.append(" and to_char(resv_time,'dd')=?");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1,col);
			rs=pstmt.executeQuery();
			
			//먼저지우고 추가하자
			columnName.removeAll(columnName);
			list.removeAll(list);
			
			columnName.add("체크 아이디");
			columnName.add("예약 아이디");
			columnName.add("체크 인 타임");
			columnName.add("체크 아웃 타임");
			columnName.add("예약 시간");
			//컬럼명 추출하자
			/*ResultSetMetaData meta = rs.getMetaData();
			for(int i=1; i<=meta.getColumnCount(); i++){
				columnName.add(meta.getColumnName(i));
			}*/
			
			
			while(rs.next()){
				Vector vec = new Vector();
				
				vec.add(rs.getString("check_io_id"));
				vec.add(rs.getString("resv_id"));
				vec.add(rs.getString("check_in_time"));
				vec.add(rs.getString("check_out_time"));
				vec.add(rs.getString("resv_time"));
				
				list.add(vec);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(pstmt!=null){
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
		return list.size();
	}
	
	public String getColumnName(int col) {
		
		return columnName.get(col);
	}
	
	public boolean isCellEditable(int row, int col) {
		boolean flag=true;
		if(col==0|col==1|col==3){
			flag=false;
		}
		return flag;
	}
	
	public Object getValueAt(int row, int col) {
		return list.get(row).get(col);
	}
	
	public void setValueAt(Object Value, int row, int col) {
		list.elementAt(row).set(col, Value);
	}
}
