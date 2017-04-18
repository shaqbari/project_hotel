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
		
		String sql="select check_io_id,resv_id,to_char(check_out_time,'yyyy-mm-dd-hh24-mi-ss') as check_out_time from check_io where  to_char(check_out_time,'dd')=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,col);
			rs=pstmt.executeQuery();
			
			//컬럼명 추출하자
			ResultSetMetaData meta = rs.getMetaData();
			for(int i=1; i<=meta.getColumnCount(); i++){
				columnName.add(meta.getColumnName(i));
			}
			
			
			while(rs.next()){
				Vector vec = new Vector();
				
				//int date=Integer.parseInt(rs.getString("check_in_time").split("-")[2]);
				//System.out.println(date);
				
				vec.add(rs.getString("check_io_id"));
				vec.add(rs.getString("resv_id"));
				vec.add(rs.getString("check_out_time"));
				
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
	
	public Object getValueAt(int row, int col) {
		return list.get(row).get(col);
	}
}
