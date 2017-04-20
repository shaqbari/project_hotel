package hotel.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import hotel.HotelMain;

public class RegAdminPanel extends JPanel implements ActionListener {
	HotelMain main;
	Connection con;
	
	JPanel p_north, p_input, p_south;
	JLabel la_title, la_serial, la_id, la_pw, la_name;
	Font font;
	JTextField txt_serial, txt_id, txt_name;
	JPasswordField txt_pw;
	JButton bt_regist, bt_prev;

	String serial="1111-2222-3333-4444";
	
	public RegAdminPanel(HotelMain main) {
		this.main = main;
		con=main.con;
			
		
		p_north = new JPanel();
		p_input = new JPanel();
		p_south = new JPanel();

		la_title = new JLabel("관리자 등록");
		la_serial = new JLabel("SN");
		la_id = new JLabel("ID");
		la_pw = new JLabel("PW");
		la_name = new JLabel("이름");

		font = new Font("맑은 고딕", font.PLAIN, 18);

		txt_serial=new JTextField("1111-2222-3333-4444", 10);
		txt_id = new JTextField(10);
		txt_name = new JTextField(10);
		txt_pw = new JPasswordField(10);
		
		txt_serial.setFont(font);
		txt_id.setFont(font);
		txt_name.setFont(font);
		txt_pw.setFont(font);

		bt_regist = new JButton("등록");
		bt_prev = new JButton("이전");

		setLayout(new BorderLayout());
		p_input.setLayout(new GridLayout(4, 2));
		p_input.setPreferredSize(new Dimension(400, 200));
		
		la_serial.setFont(font);
		la_title.setFont(new Font("맑은고딕", font.BOLD, 25));
		la_id.setFont(font);
		la_pw.setFont(font);
		la_name.setFont(font);

		p_north.add(la_title);

		p_input.add(la_serial);
		p_input.add(txt_serial);
		p_input.add(la_id);
		p_input.add(txt_id);
		p_input.add(la_pw);
		p_input.add(txt_pw);
		p_input.add(la_name);
		p_input.add(txt_name);

		p_south.add(bt_regist);
		p_south.add(bt_prev);

		add(p_north, BorderLayout.NORTH);
		add(p_input);
		add(p_south, BorderLayout.SOUTH);

		bt_regist.addActionListener(this);
		bt_prev.addActionListener(this);

		setPreferredSize(new Dimension(400, 300));
		setVisible(false);
	}
	
	//입력이 올바르지 않을경우 입력칸 초기화
	public void initialSet(){
		txt_serial.setText("");
		txt_id.setText("");
		txt_pw.setText("");
		txt_name.setText("");
		txt_serial.requestFocus();
	}
	
	public boolean check() {		
		/*if (txt_serial.getText().equals(serial)) {
			if (txt_id.getText().equals("")!=true) {
				if (txt_id.getText().getBytes().length>5) {
					if (new String(txt_pw.getPassword()).equals("")!=true) {
						if (txt_name.getText().equals("")!=true) {							
							return true;							
						}else{
							JOptionPane.showMessageDialog(this, "이름를 입력해주세요");
							initialSet();							
							return false;
						}						
					}else{
						JOptionPane.showMessageDialog(this, "password를 입력해주세요");
						initialSet();
						return false;						
					}					
				}else{
					JOptionPane.showMessageDialog(this, "id가 길이가 짧습니다.");
					initialSet();
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(this, "id를 입력해주세요");
				initialSet();
				return false;
			} 
		}else{
			JOptionPane.showMessageDialog(this, "serial number가 올바르지 않습니다.");
			initialSet();
			return false;
		}*/
		
		if (txt_serial.getText().equals(serial)==false) {//이렇게 쓰면 else문을 안써도 된다.
			JOptionPane.showMessageDialog(this, "serial number가 올바르지 않습니다.");
			initialSet();
			return false;
		}		
		if (txt_id.getText().isEmpty()) {//txt_id.getText().equals("")는 무거운연산수행
			JOptionPane.showMessageDialog(this, "id를 입력해주세요");
			initialSet();
			return false;
		}		
		if (txt_id.getText().getBytes().length<6) {
			JOptionPane.showMessageDialog(this, "id는 6자이상으로 해주세요");
			initialSet();
			return false;
		}
		if (new String(txt_pw.getPassword()).length()==0) {
			JOptionPane.showMessageDialog(this, "password를 입력해주세요");
			initialSet();
			return false;
		}
		if (new String(txt_pw.getPassword()).getBytes().length<4) {
			JOptionPane.showMessageDialog(this, "password는 4자이상으로 해주세요");
			initialSet();
			return false;
		}
		if(txt_name.getText().length()==0){//txt_id.getText().isEmpty()보다 좋다
			JOptionPane.showMessageDialog(this, "이름를 입력해주세요");
			initialSet();							
			return false;		
		}		
		return true;//위의 조건이 모두 아닐경우 true가 반환된다.
	}

	public void regist() {
		if (check()) {
			StringBuffer sql=new StringBuffer();
			sql.append("insert into hotel_admin(hotel_admin_id, hotel_admin_pw, hotel_admin_name)");
			sql.append("values (?, ?, ?)");
			
			PreparedStatement pstmt=null;
			try {
				pstmt=con.prepareStatement(sql.toString());
				pstmt.setString(1, txt_id.getText());
				pstmt.setString(2, new String(txt_pw.getPassword()));
				pstmt.setString(3, txt_name.getText());				
				int result=pstmt.executeUpdate();
				if (result==1) {
					System.out.println("삽입성공");
					JOptionPane.showMessageDialog(this, "등록되었습니다");
					main.setPage(0);
				}else {
					System.out.println("삽입실패");
					
				}				
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "중복되는 id가 있습니다.");
				initialSet();
			} finally {
				if (pstmt!=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}					
				}
			}			
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = (Object) e.getSource();
		if (obj == bt_prev) {
			main.setPage(0);
		} else if (obj == bt_regist) {
			regist();
		}
	}

}
