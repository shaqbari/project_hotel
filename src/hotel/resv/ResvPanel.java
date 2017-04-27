package hotel.resv;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import hotel.HotelMain;

//----------�߰�����--------
//-------------------------

public class ResvPanel extends JPanel implements ActionListener{
	String TAG=this.getClass().getName();
	HotelMain main;
	Connection con;
	JPanel p_info,p_bt,p_north, p_center;
	JButton bt_prev, bt_next, bt_refresh;
	JLabel la_date,la_info;
	JScrollPane scroll;
	JTable table;
	Calendar cal=Calendar.getInstance();
	ResvModel rm;
	Resv_Open newOpen;
	
	//���� ��¥ �޾ƿ��� ����
	int yy;
	int mm;
	int dd;
	String value;
	
	public ResvPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		this.setLayout(new BorderLayout());
		p_info = new JPanel();
		p_bt = new JPanel();
		p_north=new JPanel();
		p_center=new JPanel();
		la_info = new JLabel("�ʷϻ�:������(ü��������) , ������:ü���ϼ�");
		bt_prev=new JButton("��");
		la_date=new JLabel();
		bt_next=new JButton("��");
		bt_refresh = new JButton("���ΰ�ħ");
		table=new JTable();
		scroll=new JScrollPane(table);
		
		table.setPreferredScrollableViewportSize(new Dimension(1000, 736));
		table.setRowHeight(23);		//�߰�����
		
		p_info.add(la_info);
		p_bt.add(bt_prev);
		p_bt.add(la_date);
		p_bt.add(bt_next);
		p_bt.add(bt_refresh);
		p_center.add(scroll);
		
		la_date.setFont(new Font("���� ���", Font.BOLD, 20));	//�߰�����
		
		p_north.add(p_info,BorderLayout.NORTH);
		p_north.add(p_bt,BorderLayout.SOUTH);
		add(p_north, BorderLayout.NORTH);
		add(p_center,BorderLayout.CENTER);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				int col=table.getSelectedColumn();
				String num=table.getValueAt(row,0).toString();
				System.out.println(num);
				value=(String) rm.getValueAt(row, col);
				if(value==""){
					newOpen = new Resv_Open(main,con,num,col,cal);
				}else{
					
				}
			}
		});
		
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);
		bt_refresh.addActionListener(this);
		
		
		//------------�߰�����-----------
		/*
		Thread thread = new Thread(){
			public void run(){
				while(true){
					try{
						Thread.sleep(3000);
						setMonth();
						//System.out.println("�޷� ���� �ҷ������Ѵ�");
					} catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}	
		};
		thread.start();
		*/
		//---------------------------------------
		
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);
	}
	
	public void setMonth(){
		yy=cal.get(Calendar.YEAR);
		mm=cal.get(Calendar.MONTH);
		
		la_date.setText(yy+"-"+(mm+1));
		
		//������� �Űܼ� ����
		table.setModel(rm=new ResvModel(con, cal,table));
		
		for(int i=0; i<rm.getColumnCount(); i++){
			table.setDefaultRenderer(table.getColumnClass(i), new Resv_Render());
		}
	
		table.updateUI();
		p_center.updateUI();
	}
	public void prev(){
		mm--;
		if(mm<0){
			yy--;
			mm=11;
		}
		cal.set(yy, mm, 1);
		setMonth();
	}
	
	public void next(){
		mm++;
		if(mm>12){
			yy++;
			mm=1;
		}
		cal.set(yy, mm, 1);
		setMonth();
	}
	
	//���� �� ���� ��ư�� ���� ������, �����޷� �̵�
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_prev){
			prev();
		}else if(obj==bt_next){
			next();
		}else if(obj==bt_refresh){
			setMonth();
		}
	}
	
	
}
