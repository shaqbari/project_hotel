package hotel.guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class GuestTable extends AbstractTableModel{
	Connection con;
	MemberPanel main;
	Vector<String> columnName= new Vector<String>();
	Vector<Vector> data= new Vector<Vector>();
	public GuestTable(Connection con,MemberPanel main) {
		this.con=con;
		this.main=main;
		columnName.add("GUEST_ID");
		columnName.add("HOTEL_USER_ID");
		columnName.add("GUEST_NAME");
		columnName.add("GUEST_PHONE");
	}
	public void getGuestList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		data.removeAllElements();
		String sql="select *from guest order by guest_id asc";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ResultSetMetaData meta=rs.getMetaData();
			int count=meta.getColumnCount();
			for(int i=0;i<count;i++){
				columnName.add(meta.getColumnName(i+1));
			}
			while(rs.next()){
				Vector vec=new Vector();
				vec.add(rs.getString("guest_id"));
				vec.add(rs.getString("hotel_user_id"));
				vec.add(rs.getString("guest_name"));
				vec.add(rs.getString("guest_phone"));
				data.add(vec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public void getSelectedGuestList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from guest where guest_name=?";
		data.removeAllElements();
		try {
			pstmt=con.prepareStatement(sql);
			System.out.println(main.t_name.getText());
			pstmt.setString(1, main.t_name.getText());
			rs=pstmt.executeQuery();
			ResultSetMetaData meta=rs.getMetaData();
			columnName= new Vector<String>();
			int count=meta.getColumnCount();
			for(int i=0;i<count;i++){
				columnName.add(meta.getColumnName(i+1));
			}
			while(rs.next()){
				Vector vec=new Vector();
				vec.add(rs.getString("guest_id"));
				vec.add(rs.getString("hotel_user_id"));
				vec.add(rs.getString("guest_name"));
				vec.add(rs.getString("guest_phone"));
				data.add(vec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public String getColumnName(int col) {
		return columnName.get(col);
	}
	public int getRowCount() {		
		return data.size();
	}
	public int getColumnCount() {
		return columnName.size();
	}
	public Object getValueAt(int row, int col) {
		return data.elementAt(row).elementAt(col);
	}
	//수정적용
	public boolean isCellEditable(int row, int col) {
		boolean flag=true;
		if(col==0||col==1){
			flag=false;
		}
		return flag;
	}
	public void setValueAt(Object Value, int row, int col) {
		data.elementAt(row).set(col, Value);
	}
}
