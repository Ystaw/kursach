package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer
{
    private int x;
    private int y;
    CustomTableCellRenderer( int ax, int ay)
    {
        x = ax;
        y = ay;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //Возвращает компонент, используемый для рисования ячейки
        if ((row ==this.x) && (column == this.y))
        {
            cell.setBackground(Color.red);
            table.repaint();
        }
        else cell.setBackground(Color.white);


        return cell;
    }
}