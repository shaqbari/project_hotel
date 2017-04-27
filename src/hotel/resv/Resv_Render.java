package hotel.resv;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Resv_Render extends DefaultTableCellRenderer{
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		ResvModel rm=(ResvModel)table.getModel();
		
		Component cell=super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		if(col == 0){ //첫번째 컬럼(호수)- 색칠되면 안되므로 따로 조건 지정
			cell.setBackground(Color.WHITE);
		}
		else if(table.getValueAt(row, col).equals(" ")){ //stay_date
	        	cell.setBackground(Color.RED);
	     }
		 else if(table.getValueAt(row, col).equals("")){ //resv_time
			  cell.setBackground(Color.GREEN);
		  } 
		 else if(table.getValueAt(row, col).equals("  ")){
			  cell.setBackground(Color.WHITE);
		  }	  
		
	        return cell;
	    }
	}

