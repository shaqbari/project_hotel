package hotel.guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class MemberTable extends AbstractTableModel{
Connection con;
MemberPanel main;
Vector<String> columnName= new Vector<String>();
Vector<Vector> data= new Vector<Vector>();
	public MemberTable(Connection con,MemberPanel main) {
		this.con=con;
		this.main=main;
		columnName.add("회원번호");
		columnName.add("호텔이용자번호");
		columnName.add("이름");
		columnName.add("닉네임");
		columnName.add("회원등록일");
		columnName.add("전화번호");
		columnName.add("이메일");
		columnName.add("성별");
		columnName.add("생년월일");
		
	}
	
	public void getMemberList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		data.removeAllElements();
		String sql="select *from membership order by membership_id asc";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();

			//System.out.println(columnName.size());
			while(rs.next()){
				Vector vec=new Vector();
				vec.add(rs.getString("membership_id"));
				vec.add(rs.getString("hotel_user_id"));
				vec.add(rs.getString("membership_name"));
				vec.add(rs.getString("membership_nick"));
				vec.add(rs.getString("membership_regdate"));
				vec.add(rs.getString("membership_phone"));
				vec.add(rs.getString("membership_email"));
				vec.add(rs.getString("membership_gender"));
				vec.add(rs.getString("membership_birthday"));
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
	public void getSelectedMemberList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from membership where membership_name=?";
		data.removeAllElements();
		try {
			Vector<String> columnName= new Vector<String>();
			pstmt=con.prepareStatement(sql);
			System.out.println(main.t_name.getText());
			pstmt.setString(1, main.t_name.getText());
			rs=pstmt.executeQuery();
			ResultSetMetaData meta=rs.getMetaData();
			int count=meta.getColumnCount();
			for(int i=0;i<count;i++){
				columnName.add(meta.getColumnName(i+1));
			}
			while(rs.next()){
				Vector vec=new Vector();
				vec.add(rs.getString("membership_id"));
				vec.add(rs.getString("hotel_user_id"));
				vec.add(rs.getString("membership_name"));
				vec.add(rs.getString("membership_nick"));
				vec.add(rs.getString("membership_regdate"));
				vec.add(rs.getString("membership_phone"));
				vec.add(rs.getString("membership_email"));
				vec.add(rs.getString("membership_gender"));
				vec.add(rs.getString("membership_birthday"));
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
	public boolean isCellEditable(int row, int col) {
		boolean flag=true;
		if(col==0||col==1||col==4||col==8){
			flag=false;
		}
		return flag;
	}
	public void setValueAt(Object Value, int row, int col) {
		data.elementAt(row).set(col, Value);
	}


}
