package hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class Resv_InModel extends AbstractTableModel{
	Connection con;
	HotelMain main;
	
	Vector<String> columnName = new Vector<String>();
	Vector<Vector> list = new Vector<Vector>();
	ResvModel rm;
	
	public Resv_InModel(HotelMain main,Connection con){
		this.main=main;
		this.con=con;
		
		getList();
	}
	
	//디비연동, con 객체 main으로부터 넘겨받을것...
	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select check_io_id,resv_id,check_in_time from check_io";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			//컬럼명 추출하자
			ResultSetMetaData meta = rs.getMetaData();
			for(int i=1; i<=meta.getColumnCount(); i++){
				columnName.add(meta.getColumnName(i));
			}
			
			
			while(rs.next()){
				Vector vec = new Vector();
				
				vec.add(rs.getString("check_io_id"));
				vec.add(rs.getString("resv_id"));
				vec.add(rs.getString("check_in_time"));
				
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
