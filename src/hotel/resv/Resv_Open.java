package hotel.resv;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import hotel.HotelMain;

public class Resv_Open extends JFrame{
	JPanel p_date;
	JLabel la_date;
	JPanel p_in,p_out;
	JLabel la_in,la_out;
	JTable table_in,table_out;
	JScrollPane scroll_in, scroll_out;
	
	Calendar cal=Calendar.getInstance();
	HotelMain main;
	Connection con;
	ResvModel rm;
	ResvPanel rp;
	
	//ResvModel 과 날짜가 늘 일치하여야하므로, 수정필요
	//지금은 테스트상태
	int yy; 
	int mm;
	int dd;
	String num;
	int col;
	
	Resv_InModel newModel_in;
	Resv_OutModel newModel_out;
	
	public Resv_Open(HotelMain main,Connection con,String num,int col, Calendar cal){
		this.main=main;
		this.con=con;
		this.num=num;
		this.col=col;
		this.cal=cal;
		
		this.setLayout(new BorderLayout());
		
		yy=cal.get(Calendar.YEAR);
		mm=cal.get(Calendar.MONTH);
		dd=col;
		
		//north
		p_date = new JPanel();
		//**선택된 테이블 셀의 날짜에 맞게 ....
		la_date = new JLabel(yy+"-"+(mm+1)+"-"+dd);
		//west
		p_in = new JPanel();
		la_in = new JLabel("예약정보");
		table_in = new JTable();
		//east
		p_out = new JPanel();
		la_out = new JLabel("Check-In&Out");
		table_out = new JTable();
		
		p_date.setLayout(new BorderLayout());
		p_in.setLayout(new BorderLayout());
		p_out.setLayout(new BorderLayout());
		
		scroll_in=new JScrollPane(table_in);
		scroll_out=new JScrollPane(table_out);
		
		//table
		scroll_in.setPreferredSize(new Dimension(1100, 150));
		scroll_out.setPreferredSize(new Dimension(1100, 150));
		
		resizeColumnWidth(table_out);
		
		//north
		p_date.add(la_date,BorderLayout.NORTH);
		add(p_date,BorderLayout.NORTH);
		
		//west
		p_in.add(la_in,BorderLayout.NORTH);
		p_in.add(scroll_in);
		add(p_in,BorderLayout.NORTH);
		
		//east
		p_out.add(la_out,BorderLayout.NORTH);
		p_out.add(scroll_out);
		add(p_out,BorderLayout.SOUTH);
	
		setVisible(true);
		setSize(new Dimension(1100, 400));
		
		table_in.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					check_in_modify();					
				}
			}
		});
		
		table_out.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					check_out_modify();					
				}
			}
		});
		
		table_in.setModel(newModel_in = new Resv_InModel(main,con,num,col,mm));
		table_in.updateUI();
		table_out.setModel(newModel_out = new Resv_OutModel(main,con,num,col,mm));
		table_out.updateUI();
	}
	
	
	public void check_in_modify(){
		int row=table_in.getSelectedRow();
		int col=table_in.getSelectedColumn();
		
		String check_id=(String)table_in.getValueAt(row, 0);
		String modify=(String)table_in.getValueAt(row, col);
		
		//System.out.println("내가 입력한 값은?"+modify);
		
		PreparedStatement pstmt=null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update check_io set check_in_time=to_date('"+modify+"','yyyy-mm-dd-hh24-mi')");
		sql.append(" where check_io_id="+check_id);
		
		//System.out.println(sql.toString());
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			//pstmt.setString(1, check_id);
			
			int result=pstmt.executeUpdate();
			
			if(result!=0){
				JOptionPane.showMessageDialog(this, "check in time 수정완료");
			}else{
				JOptionPane.showMessageDialog(this, "check in time 수정실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void check_out_modify(){
		int row=table_out.getSelectedRow();
		int col=table_out.getSelectedColumn();
		
		String check_id=(String)table_out.getValueAt(row, 0);
		String modify=(String)table_out.getValueAt(row, col);
		
		//System.out.println("내가 입력한 값은?"+modify);
		
		PreparedStatement pstmt=null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update check_io set check_out_time=to_date('"+modify+"','yyyy-mm-dd-hh24-mi')");
		sql.append(" where check_io_id="+check_id);
		
		//System.out.println(sql.toString());
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			//pstmt.setString(1, check_id);
			
			int result=pstmt.executeUpdate();
			
			if(result!=0){
				JOptionPane.showMessageDialog(this, "check out time 수정완료");
			}else{
				JOptionPane.showMessageDialog(this, "check out time 수정실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//각 셀의 데이터 길이에 따라 길이 조절 메서드  
	public void resizeColumnWidth(JTable table) { 
		final TableColumnModel columnModel = table.getColumnModel(); 
		for (int column = 0; column < table.getColumnCount(); column++) { 
			int width = 70; // Min width 
			for (int row = 0; row < table.getRowCount(); row++) { 
				TableCellRenderer renderer = table.getCellRenderer(row, column); 
				Component comp = table.prepareRenderer(renderer, row, column); 
				width = Math.max(comp.getPreferredSize().width +10 , width); 
				} 
			columnModel.getColumn(column).setPreferredWidth(width); 
			} 
	}
	
}
