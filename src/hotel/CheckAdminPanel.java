package hotel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CheckAdminPanel extends JPanel implements ActionListener{
	HotelMain main;
	JPanel p_north, p_input, p_south;
	JLabel la_title, la_id, la_pw;
	Font font;
	JTextField txt_id;
	JPasswordField txt_pw;	
	JButton bt_check, bt_regist;
	
	String adminId="admin";
	String adminPw="admin";
	
	public CheckAdminPanel(HotelMain main) {		
		this.main=main;
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
	
	public void check(){
		//아이디 비번 일치하면 hotelMain 보이게
		if (txt_id.getText().equals(adminId)&&new String(txt_pw.getPassword()).equals(adminPw)) {//char[]를 인자로 받는 String객체의 생성자를 이용한다.			
			this.setVisible(false);	
			txt_id.setText("");
			txt_pw.setText("");
			txt_id.requestFocus();
			main.setPage(2);
		}else{
			JOptionPane.showMessageDialog(this, "로그인 정보가 잘못되었습니다.");			
			txt_id.setText("");
			txt_pw.setText("");
			txt_id.requestFocus();
		}
	}
	
	public void regist(){
		main.setPage(1); //RegAdminPanel보이게 한다.
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=(Object)e.getSource();
		if (obj==bt_check) {
			check();
		}else if(obj==bt_regist){
			regist();
		}
	}	
	
}
