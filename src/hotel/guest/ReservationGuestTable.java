package hotel.guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ReservationGuestTable extends AbstractTableModel{
	Connection con;
	Vector<String> columnName= new Vector<String>();
	Vector<Vector> data= new Vector<Vector>();
	int index;
	public ReservationGuestTable(Connection con,int index) {
		this.index=index;
		this.con=con;
		columnName.add("RESV_ID");
		columnName.add("GUEST_NAME");
		columnName.add("GUEST_PHONE");
		columnName.add("RESV_TIME");
		columnName.add("ROOM_NUMBER");
		columnName.add("ROOM_OPTION_NAME");
		columnName.add("CHECK_IN_TIME");
		columnName.add("CHECK_OUT_TIME");
		getList();
	}
	
	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select re.RESV_ID, g.GUEST_NAME, g.GUEST_PHONE");
		sql.append(" , re.RESV_TIME, r.ROOM_NUMBER, ro.ROOM_OPTION_NAME");
		sql.append(" , c.CHECK_IN_TIME, c.CHECK_OUT_TIME");
		sql.append(" from ROOM_OPTION ro, ROOM r, RESV re");
		sql.append(" , CHECK_IO c, HOTEL_USER h, GUEST g");
		sql.append(" where r.ROOM_OPTION_ID=ro.ROOM_OPTION_ID");
		sql.append(" and r.ROOM_NUMBER=re.ROOM_NUMBER and re.RESV_ID=c.RESV_ID and re.HOTEL_USER_ID=h.HOTEL_USER_ID");
		sql.append("  and h.HOTEL_USER_ID = g.HOTEL_USER_ID");
		sql.append("  and g.guest_id=?");
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, index);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Vector vec=new Vector();
				vec.add(rs.getString("RESV_ID"));
				vec.add(rs.getString("GUEST_NAME"));
				vec.add(rs.getString("GUEST_PHONE"));
				vec.add(rs.getString("RESV_TIME"));
				vec.add(rs.getString("ROOM_NUMBER"));
				vec.add(rs.getString("ROOM_OPTION_NAME"));
				vec.add(rs.getString("CHECK_IN_TIME"));
				vec.add(rs.getString("CHECK_OUT_TIME"));
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
