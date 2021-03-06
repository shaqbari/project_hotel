package hotel.resv;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import hotel.HotelMain;

//----------추가했음--------
//-------------------------

public class ResvPanel extends JPanel implements ActionListener{
	String TAG=this.getClass().getName();
	HotelMain main;
	Connection con;
	JPanel p_north,p_bt,p_info,p_resv,p_due, p_center;
	JButton bt_prev, bt_next, bt_refresh;
	JLabel la_date,la_resv, la_due;
	JScrollPane scroll;
	JTable table;
	Calendar cal=Calendar.getInstance();
	ResvModel rm;
	Resv_Open newOpen;
	
	//현재 날짜 받아오는 변수
	int yy;
	int mm;
	int dd;
	String value;
	
	public ResvPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		/*----------------------------------------
			refresh 버튼 이미지 가져오기, 사이즈 조절하기
		----------------------------------------------*/
		URL url = null;
		try {
			url = new URL("http://pseudoluna.synology.me/experi/images/refresh.png");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}		
		ImageIcon icon = new ImageIcon(url);
		Image orignImg = icon.getImage();
		Image changeImg = orignImg.getScaledInstance(28, 30, Image.SCALE_SMOOTH);
		ImageIcon resizeIcon = new ImageIcon(changeImg);
				
		this.setLayout(new BorderLayout());
		p_north=new JPanel();
		p_bt = new JPanel();
		p_info = new JPanel();
		p_resv=new JPanel();
		p_due=new JPanel();
		p_center=new JPanel();
		la_resv= new JLabel("예약일");
		la_due=new JLabel("체류기간");
		bt_prev=new JButton("◀");
		la_date=new JLabel();
		bt_next=new JButton("▶");
		bt_refresh = new JButton(resizeIcon);
		table=new JTable();
		scroll=new JScrollPane(table);
		
		table.setPreferredScrollableViewportSize(new Dimension(1000, 736));
		table.setRowHeight(23);		//추가했음
		
		p_resv.setBackground(new Color(135,206,235));
		p_due.setBackground(new Color(255,182,193));

		la_date.setFont(new Font("맑은 고딕", Font.BOLD, 20));	//추가했음
	
		p_bt.add(bt_prev);
		p_bt.add(la_date);
		p_bt.add(bt_next);
		p_bt.add(bt_refresh);
		p_info.add(p_resv);
		p_info.add(la_resv);
		p_info.add(p_due);
		p_info.add(la_due);
		
		//p_north.setLayout(new BorderLayout());
		p_north.add(p_bt);
		p_north.add(p_info);
		p_center.add(scroll);
		
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
		
		bt_refresh.setPreferredSize(new Dimension(28, 30));

		
		//------------추가했음-----------
		/*
		Thread thread = new Thread(){
			public void run(){
				while(true){
					try{
						Thread.sleep(3000);
						setMonth();
						//System.out.println("달력 새로 불러오기한다");
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
		
		//공통사항 옮겨서 부착
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
	
	//이전 과 다음 버튼을 눌러 이전달, 다음달로 이동
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_prev){
			prev();
		}else if(obj==bt_next){
			next();
		}else if(obj==bt_refresh){
			setMonth();
			JOptionPane.showMessageDialog(this, "예약현황 갱신완료");
		}
	}
	
	
}
