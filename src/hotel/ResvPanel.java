package hotel;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JPanel;

public class ResvPanel extends JPanel{
	HotelMain main;
	Connection con;
	
	public ResvPanel(HotelMain main) {
		this.main=main;
		con=main.con;
		
		setBackground(Color.CYAN);
		
		setPreferredSize(new Dimension(1100, 900));
		setVisible(false);			
	}
}
