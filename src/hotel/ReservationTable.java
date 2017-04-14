package hotel;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class ReservationTable extends JFrame{
	JTable table;
	public ReservationTable() {
		table=new JTable();
		setSize(600,700);
		setBackground(Color.YELLOW);
	}
}
