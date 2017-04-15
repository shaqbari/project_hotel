package hotel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class ResvPanel extends JPanel implements ActionListener{
	HotelMain main;
	Connection con;
	JPanel p_north, p_center;
	JButton bt_prev, bt_next;
	JLabel la_date;
	JScrollPane scroll;
	JTable table;
	Calendar cal=Calendar.getInstance();
	ResvModel rm;
	Resv_Open newOpen;
	
	//���� ��¥ �޾ƿ��� ����
	int yy=cal.get(cal.YEAR);
	int mm=cal.get(cal.MONTH)+1;
	int dd=cal.get(cal.DAY_OF_MONTH);
	
	public ResvPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		System.out.println("���� ������ "+(mm));
		
		this.setLayout(new BorderLayout());
		p_north=new JPanel();
		p_center=new JPanel();
		bt_prev=new JButton("��");
		la_date=new JLabel(yy+"-"+(mm));
		bt_next=new JButton("��");
		table=new JTable();
		scroll=new JScrollPane(table);
		
		table.setPreferredScrollableViewportSize(new Dimension(1000, 750));
		
		p_north.add(bt_prev);
		p_north.add(la_date);
		p_north.add(bt_next);
		p_center.add(scroll);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center,BorderLayout.CENTER);

		cal.set(yy, mm, 0);
		table.setModel(rm=new ResvModel(con,cal));
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//System.out.println("������� �޷����̺� Ŭ���� �̺�Ʈ�߻�");
				newOpen = new Resv_Open(main,con);
			}
		});
		
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);
		
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);
		

	}
	
	//���� �� ���� ��ư�� ���� ������, �����޷� �̵�
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_prev){
			mm--;
			if(mm<1){
				yy--;
				mm=12;
			}
			System.out.println("���� ����?"+(mm));
		}else if(obj==bt_next){
			mm++;
			if(mm>12){
				yy++;
				mm=1;
			}
			System.out.println("���� ����?"+(mm));
		}
		//������� �Űܼ� ����
		la_date.setText(yy+"-"+(mm));
		cal.set(yy, mm, 0);
		System.out.println("���� ����?"+(mm));
		
		table.setModel(rm=new ResvModel(con,cal));
		table.updateUI();
		p_center.updateUI();
	}//
}
