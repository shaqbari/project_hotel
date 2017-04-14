package hotel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CheckAdmin extends JFrame{
	JLabel la_title, la_id, la_pw;
	JPanel p_input;
	JTextField txt_id;
	JPasswordField txt_pw;	
	
	String adminId="admin";
	String adminPw="admin";
	
	
	public CheckAdmin() {
		p_input=new JPanel();
		la_title=new JLabel("호텔예약관리시스템");
		la_id=new JLabel("id");
		la_pw=new JLabel("pw");		
		txt_id=new JTextField(10);
		txt_pw=new JPasswordField(10);
				
		p_input.setLayout(new GridLayout(2, 2));
		
		p_input.add(la_id);
		p_input.add(txt_id);
		p_input.add(la_pw);
		p_input.add(txt_pw);
		
		add(la_title, BorderLayout.NORTH);
		add(p_input);
		
		setSize(400, 150);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
	public static void main(String[] args) {
		new CheckAdmin();
	}
	
	
}
