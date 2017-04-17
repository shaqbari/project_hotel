package hotel.guest;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import hotel.DBManager;
import hotel.HotelMain;



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
		bt_reservation=new JButton("����Ȯ��");
		bt_modify=new JButton("����");
		bt_search=new JButton("��ȸ");
		//la_north=new JLabel("ȸ������");
		la_center=new JLabel("ȸ����");
		ch_member=new Checkbox("ȸ��",chkgroup,false);
		ch_guest=new Checkbox("��ȸ��",chkgroup,false);
		t_name=new JTextField(10);
		//p_north.add(la_north,BorderLayout.EAST);
		p_center.add(ch_member);
		p_center.add(ch_guest);
		p_center.add(la_center);
		p_center.add(t_name);
		p_center.add(bt_search);
		p_container.setLayout(new GridLayout(2, 1));
		p_container.add(p_north);
		p_container.add(p_center);
		
		//p_container.setPreferredSize(new Dimension(800, 100));
		p_south.add(bt_reservation);
		p_south.add(bt_modify);
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
		System.out.println(value);
		ReservationTable reservation=new ReservationTable(con,value,flag);
		reservation.setVisible(true);
		//System.out.println(table.getColumnCount());
		
	}
	public void modify(){
		
		
	}
	public void searchmember(){
		table.setModel(table_member=new MemberTable(con, this));
		table_member.getSelectedMemberList();
		table.updateUI();
	}
	public void serachguest(){
		table.setModel(table_guest=new GuestTable(con, this));
		table_guest.getSelectedGuestList();
		table.updateUI();
	}
	public void findmember(){
		table.setModel(table_member=new MemberTable(con, this));
		table_member.getMemberList();
		table.updateUI();
		flag=true;
	}
	public void findguest(){
		table.setModel(table_guest=new GuestTable(con, this));
		table_guest.getGuestList();
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


	
}
