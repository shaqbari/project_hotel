package hotel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyButton extends JPanel implements ActionListener{
	HotelMain main;	
	JButton bt;
	JLabel la;
	ImageIcon icon;
	boolean flag=true;
	URL url;
	
	public MyButton(HotelMain main, JButton bt, URL url, String menu) {
		this.main=main;
		this.bt=bt;		
		icon=new ImageIcon(url);
		la=new JLabel(menu);
		
		setPreferredSize(new Dimension(120, 150));
		bt.setPreferredSize(new Dimension(100, 100));		
		bt.setIcon(icon);
		
		bt.setBorderPainted(false);
		bt.setContentAreaFilled(false);
		bt.setFocusPainted(false);
		bt.setOpaque(false);
		
		bt.addActionListener(this);
		
		add(bt);		
		add(la);
	}

	public void actionPerformed(ActionEvent e) {
		if (flag) {
			flag=!flag;
			for (int i = 0; i < main.myButtons.length; i++) {
				this.setBackground(Color.LIGHT_GRAY);
				if(main.myButtons[i]!=this){
					main.myButtons[i].setBackground(null);
					main.myButtons[i].flag=true;					
				}
			}
		}else {
			flag=!flag;
			this.setBackground(null);
		}
	}	
	
}
