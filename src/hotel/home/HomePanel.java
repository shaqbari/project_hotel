package hotel.home;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JPanel;

import hotel.HotelMain;

public class HomePanel extends JPanel{
	HotelMain main;
	Connection con;

	
	public HomePanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		setBackground(Color.gray);
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(true);		
	}
	
}