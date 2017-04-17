package hotel.resv;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Resv_Render extends DefaultTableCellRenderer{
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		ResvModel rm=(ResvModel)table.getModel();
		
		Component cell=super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		  if(table.getValueAt(row, col).equals("O")){
	            cell.setBackground(Color.RED);
	        } else if(table.getValueAt(row, col).equals("X")){
	            cell.setBackground(Color.WHITE);
	        }
	        return cell;
	    }
	}

