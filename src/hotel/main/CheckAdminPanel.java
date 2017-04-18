package hotel.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import hotel.HotelMain;

public class CheckAdminPanel extends JPanel implements ActionListener{
	HotelMain main;
	Connection con;
	
	JPanel p_north, p_input, p_south;
	JLabel la_title, la_id, la_pw;
	Font font;
	JTextField txt_id;
	JPasswordField txt_pw;	
	JButton bt_check, bt_regist;
	
	ArrayList<Hotel_admin>hotel_admins=new ArrayList<Hotel_admin>();
	
	String adminId="admin";
	String adminPw="admin";
	
	public CheckAdminPanel(HotelMain main) {		
		this.main=main;
		con=main.con;
		
		p_north=new JPanel();
		p_input=new JPanel();
		p_south=new JPanel();
		la_title=new JLabel("호텔예약관리시스템");
		la_id=new JLabel("id");
		la_pw=new JLabel("pw");
		font=new Font("맑은 고딕", font.PLAIN, 20);
		txt_id=new JTextField("admin", 10);
		txt_pw=new JPasswordField("admin", 10);
		bt_check=new JButton("확인");
		bt_regist=new JButton("등록");
		
		this.setLayout(new BorderLayout());
		
		p_input.setPreferredSize(new Dimension(400, 100));
		p_input.setLayout(new GridLayout(2, 2));
		
		la_title.setFont(new Font("맑은고딕", font.BOLD, 25));
		la_id.setFont(font);
		la_pw.setFont(font);
		txt_id.setFont(font);
		txt_pw.setFont(font);
		
		p_north.add(la_title);
		
		p_input.add(la_id);
		p_input.add(txt_id);
		p_input.add(la_pw);
		p_input.add(txt_pw);
		
		p_south.add(bt_check);
		p_south.add(bt_regist);
				
		add(p_north, BorderLayout.NORTH);
		add(p_input);
		add(p_south, BorderLayout.SOUTH);

		txt_pw.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if (key==KeyEvent.VK_ENTER) {
					check();
				}
			}
		});
		bt_check.addActionListener(this);
		bt_regist.addActionListener(this);
				
		
		setSize(400, 200);
		setVisible(true);		
	}
	
	public void setHotel_admins(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from hotel_admin";
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			
			hotel_admins.removeAll(hotel_admins);//먼저 지운다.
			
			while (rs.next()) {
				Hotel_admin dto=new Hotel_admin();
				dto.setHotel_admin_id(rs.getString("hotel_admin_id"));
				dto.setHotel_admin_pw(rs.getNString("hotel_admin_pw"));
				dto.setHotel_admin_name(rs.getString("hotel_admin_name"));
				
				hotel_admins.add(dto);
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 		
		}		
	}
	
	//아이디 비번 일치하면 hotelMain만 보이게한다.
	public void check(){
		setHotel_admins();
		
		for (int i = 0; i < hotel_admins.size(); i++) {
			if (hotel_admins.get(i).getHotel_admin_id().equals(txt_id.getText())) {//아이디가 일치하면
				if (hotel_admins.get(i).getHotel_admin_pw().equals(new String(txt_pw.getPassword()))) {//비밀번호가 일치하면
					this.setVisible(false);	
					txt_id.setText("");
					txt_pw.setText("");
					txt_id.requestFocus();
					
					String name=hotel_admins.get(i).getHotel_admin_name();
					JOptionPane.showMessageDialog(this, name+"님 반갑습니다.");
					main.la_user.setText(name);
					main.setPage(2);//hotelmain페이지만 보이게 한다.		
					return; //pw와 id모두 일치하면 아래가 실행되지 않는다.
				}
			}
		}
		
		txt_id.setText("");
		txt_pw.setText("");
		txt_id.requestFocus();
		JOptionPane.showMessageDialog(this, "로그인정보가 정확하지 않습니다.");
	}

	
	public void actionPerformed(ActionEvent e) {
		Object obj=(Object)e.getSource();
		if (obj==bt_check) {
			check();
		}else if(obj==bt_regist){
			main.setPage(1); //RegAdminPanel보이게 한다.
		}
	}	
	
}
