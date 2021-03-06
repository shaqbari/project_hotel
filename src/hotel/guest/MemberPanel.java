package hotel.guest;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import hotel.HotelMain;
import hotel.main.DBManager;



public class MemberPanel extends JPanel implements ActionListener{
	HotelMain main;
	Connection con;
	JPanel p_north,p_center,p_container,p_south,p_main;
	JTable table;
	JScrollPane scroll;
	JLabel la_north,la_center;
	JButton bt_reservation,bt_modify,bt_search;
	JTextField t_name;
	MemberTable table_member;
	GuestTable table_guest;
	DBManager manager=DBManager.getInstance();
	Checkbox ch_guest,ch_member;
	CheckboxGroup chkgroup;
	MemberPanel me;
	boolean flag=true;
	public MemberPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		con=manager.getConnection();
		p_main=new JPanel();
		p_north=new JPanel();
		p_center=new JPanel();
		p_container=new JPanel();
		p_south=new JPanel();
		chkgroup=new CheckboxGroup();
		table=new JTable();
		scroll=new JScrollPane(table);
		bt_reservation=new JButton("예약확인");
		bt_modify=new JButton("수정");
		bt_search=new JButton("조회");
		//la_north=new JLabel("회원관리");
		la_center=new JLabel("회원명");
		ch_member=new Checkbox("회원",chkgroup,false);
		ch_guest=new Checkbox("비회원",chkgroup,false);
		t_name=new JTextField(10);
		//p_north.add(la_north,BorderLayout.EAST);
		p_center.add(ch_member);
		p_center.add(ch_guest);
		p_center.add(la_center);
		p_center.add(t_name);
		p_center.add(bt_search);
		p_center.add(bt_reservation);
		p_container.setLayout(new GridLayout(2, 1));
		p_container.add(p_north);
		p_container.add(p_center);
		
		//-------------------
		table.setRowHeight(25);
		
		resizeColumnWidth(table);
		
		//----------
		
		//p_container.setPreferredSize(new Dimension(800, 100));
		p_main.setLayout(new BorderLayout());
		p_main.add(p_container,BorderLayout.NORTH);
		p_main.add(scroll,BorderLayout.CENTER);
		p_main.add(p_south,BorderLayout.SOUTH);
		p_main.setPreferredSize(new Dimension(1050, 850));
		add(p_main);
		ch_member.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
					findmember();
				
			}
		});
		ch_guest.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				findguest();
			}
		});
		table.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					modify();
				}
			}
		});
		bt_search.addActionListener(this);
		bt_modify.addActionListener(this);
		bt_reservation.addActionListener(this);
		//setBackground(Color.PINK);
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);	
	}
	public void reservation(){
		int row=table.getSelectedRow();
		Object obj=table.getValueAt(row, 0);
		String value=obj.toString();
		//System.out.println(value);
		ReservationTable reservation=new ReservationTable(con,value,flag);
		reservation.setVisible(true);
		//System.out.println(table.getColumnCount());
		
	}
	public void modify(){
		int row=table.getSelectedRow();
		int col=table.getSelectedColumn();
		String value= table.getValueAt(row, 0).toString();
		if(flag==true){
			PreparedStatement pstmt=null;
			String[] data= new String[9];
			for(int i=0; i<9; i++){
				data[i]=table.getValueAt(row, i).toString();
			}
			StringBuffer sb= new StringBuffer();
			sb.append("update membership set membership_id=?");
			sb.append(", hotel_user_id=?");
			sb.append(", membership_name=?");
			sb.append(", membership_nick=?");
			sb.append(", membership_phone=?");
			sb.append(", membership_email=?");
			sb.append(", membership_gender=?");
			//sb.append(", membership_birthday=?");
			sb.append(" where membership_id=?");
			try {
				pstmt=con.prepareStatement(sb.toString());
				pstmt.setInt(1, Integer.parseInt(data[0]));
				pstmt.setInt(2, Integer.parseInt(data[1]));
				pstmt.setString(3, data[2]);
				pstmt.setString(4, data[3]);
				pstmt.setString(5, data[5]);
				pstmt.setString(6, data[6]);
				pstmt.setString(7, data[7]);
				//pstmt.setString(8, data[8]);
				pstmt.setInt(8, Integer.parseInt(value));
				int result=pstmt.executeUpdate();
				if(result!=0){
					JOptionPane.showMessageDialog(this, "수정완료");
				}else{
					JOptionPane.showMessageDialog(this, "수정실패");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag==false){
			PreparedStatement pstmt=null;
			String[] data= new String[4];
			for(int i=0; i<4; i++){
				data[i]=table.getValueAt(row, i).toString();
			}
			StringBuffer sb= new StringBuffer();
			sb.append("update guest set guest_id=?");
			sb.append(", hotel_user_id=?");
			sb.append(", guest_name=?");
			sb.append(", guest_phone=?");
			sb.append(" where guest_id=?");
			try {
				pstmt=con.prepareStatement(sb.toString());
				pstmt.setInt(1, Integer.parseInt(data[0]));
				pstmt.setInt(2, Integer.parseInt(data[1]));
				pstmt.setString(3, data[2]);
				pstmt.setString(4, data[3]);
				pstmt.setInt(5, Integer.parseInt(value));
				int result=pstmt.executeUpdate();
				if(result!=0){
					JOptionPane.showMessageDialog(this, "수정완료");
				}else{
					JOptionPane.showMessageDialog(this, "수정실패");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//String sql="update "
		//pstmt=con.prepareStatement(sql);
		
		
	}
	public void searchmember(){
		table.setModel(table_member=new MemberTable(con, this));
		table_member.getSelectedMemberList();
		resizeColumnWidth(table);
		table.updateUI();
		
	}
	public void serachguest(){
		table.setModel(table_guest=new GuestTable(con, this));
		table_guest.getSelectedGuestList();
		resizeColumnWidth(table);
		table.updateUI();
	}
	public void findmember(){
		table.setModel(table_member=new MemberTable(con, this));
		table_member.getMemberList();
		resizeColumnWidth(table);
		table.updateUI();
		flag=true;
	}
	public void findguest(){
		table.setModel(table_guest=new GuestTable(con, this));
		table_guest.getGuestList();
		resizeColumnWidth(table);
		table.updateUI();
		flag=false;
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_search && flag==true){
			searchmember();
		}else if(obj==bt_search && flag==false){
			serachguest();
		}else if(obj==bt_reservation){
			reservation();
		}else if(obj==bt_modify){
			modify();
		}
	}
	
	//각 셀의 데이터 길이에 따라 길이 조절 메서드  
	public void resizeColumnWidth(JTable table) { 
		final TableColumnModel columnModel = table.getColumnModel(); 
		for (int column = 0; column < table.getColumnCount(); column++) { 
			int width = 50; // Min width 
			for (int row = 0; row < table.getRowCount(); row++) { 
				TableCellRenderer renderer = table.getCellRenderer(row, column); 
				Component comp = table.prepareRenderer(renderer, row, column); 
				width = Math.max(comp.getPreferredSize().width +1 , width); 
				} 
			columnModel.getColumn(column).setPreferredWidth(width); 
			} 
	}

}
