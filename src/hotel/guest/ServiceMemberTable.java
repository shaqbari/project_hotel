package hotel.guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ServiceMemberTable extends AbstractTableModel{
	Connection con;
	Vector<String> columnName= new Vector<String>();
	Vector<Vector> data= new Vector<Vector>();
	String value;
	public ServiceMemberTable(Connection con,String value) {
		this.value=value;
		this.con=con;
		columnName.add("서비스번호");
		columnName.add("제품명");
		columnName.add("가격");
		columnName.add("서비스 주문시간");
		getList();
	}
	
	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select s.service_id, s.service_name, s.service_price,");
		sql.append(" su.service_use_time from service s, service_use su, hotel_user h,");
		sql.append(" MEMBERSHIP m where s.service_id=su.service_id");
		sql.append(" and su.hotel_user_id=h.hotel_user_id and h.hotel_user_id=m.hotel_user_id and");
		sql.append(" m.MEMBERSHIP_id=?");
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, value);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Vector vec=new Vector();
				vec.add(rs.getString("service_id"));
				vec.add(rs.getString("service_name"));
				vec.add(rs.getString("service_price"));
				vec.add(rs.getString("service_use_time"));
				data.add(vec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

}
