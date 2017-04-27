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
	String value;
	public ReservationGuestTable(Connection con,String value) {
		this.value=value;
		this.con=con;
		columnName.add("�����ȣ");
		columnName.add("�̸�");
		columnName.add("��ȭ��ȣ");
		columnName.add("����ð�");
		columnName.add("���ȣ");
		columnName.add("�ɼǸ�");
		columnName.add("üũ�� �����ð�");
		columnName.add("üũ�ƿ� �����ð�");
		getList();
	}

	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select re.RESV_ID, g.GUEST_NAME, g.GUEST_PHONE");
		sql.append(" , re.RESV_REGTIME, r.ROOM_NUMBER, ro.ROOM_OPTION_NAME");
		sql.append(" , re.RESV_TIME, re.END_TIME");
		sql.append(" from ROOM_OPTION ro, ROOM r, RESV re");
		sql.append(" , HOTEL_USER h, GUEST g");
		sql.append(" where r.ROOM_OPTION_ID=ro.ROOM_OPTION_ID");
		sql.append(" and r.ROOM_NUMBER=re.ROOM_NUMBER and re.HOTEL_USER_ID=h.HOTEL_USER_ID");
		sql.append("  and h.HOTEL_USER_ID = g.HOTEL_USER_ID");
		sql.append("  and g.guest_id=?");

		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, value);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Vector vec=new Vector();
				vec.add(rs.getString("RESV_ID"));
				vec.add(rs.getString("GUEST_NAME"));
				vec.add(rs.getString("GUEST_PHONE"));
				vec.add(rs.getString("RESV_REGTIME"));
				vec.add(rs.getString("ROOM_NUMBER"));
				vec.add(rs.getString("ROOM_OPTION_NAME"));
				vec.add(rs.getString("RESV_TIME"));
				vec.add(rs.getString("END_TIME"));
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
