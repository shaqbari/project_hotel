package hotel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegAdminPanel extends JPanel implements ActionListener {
	HotelMain main;
	JPanel p_north, p_input, p_south;
	JLabel la_title, la_id, la_pw, la_name;
	Font font;
	JTextField txt_id, txt_name;
	JPasswordField txt_pw;
	JButton bt_regist, bt_prev;

	public RegAdminPanel(HotelMain main) {
		this.main = main;
		p_north = new JPanel();
		p_input = new JPanel();
		p_south = new JPanel();
		
		la_title = new JLabel("관리자 등록");
		la_id = new JLabel("id");
		la_pw = new JLabel("pw");
		la_name = new JLabel("dlfma");
		
		font = new Font("맑은 고딕", font.PLAIN, 20);
		
		txt_id = new JTextField(10);
		txt_name = new JTextField(10);
		txt_pw = new JPasswordField(10);
		
		bt_regist = new JButton("등록");
		bt_prev = new JButton("이전");

		this.setLayout(new BorderLayout());
		p_input.setLayout(new GridLayout(3, 2));
		p_input.setPreferredSize(new Dimension(400, 100));

		la_title.setFont(new Font("맑은고딕", font.BOLD, 25));
		la_id.setFont(font);
		la_pw.setFont(font);

		p_north.add(la_title);

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
		setVisible(true);
	}
/*
	public void check() {
		if (true) {// char[]를 인자로 받는 String객체의 생성자를 이용한다.
			this.setVisible(false);
			txt_id.setText("");
			txt_pw.setText("");
			txt_id.requestFocus();
			main.setPage(1);// 아이디 비번 일치하면 이패널 안보이고, 다른패널 보이게
		} else {
			JOptionPane.showMessageDialog(this, "로그인 정보가 잘못되었습니다.");
			txt_id.setText("");
			txt_pw.setText("");
			txt_id.requestFocus();
		}
	}
*/
	public void regist() {

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
