package hotel;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JPanel;

public class NowPanel extends JPanel{
	HotelMain main;
	Connection con;
	
	public NowPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		setBackground(Color.WHITE);
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);	
	}
}
