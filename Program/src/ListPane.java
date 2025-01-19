import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * Component class for showing database
 */
public class ListPane extends JScrollPane
{
    String[][] list;
    View v;
    // private static final int NUM_PARAMS = Bike.LENGTH;
    final static String[] header = new String[] {
        "Date", "Name", "Make", "Model", "Color", "Checked Out", " "
    };
    public ListPane()
    {
        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public ListPane(String[][] displayList, View view)
    {
        super(
            getJTable(displayList, view), 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        v = view;
    }

    public void show(String[][] newContent)
    {
        setViewportView(getJTable(newContent, v));
    }

    public static Object[][] convertData(String[][] displayList, View v)
    {
        Object[][] data = new Object[displayList.length][7];
        JButton bikeButton;
        for (int i = 0; i < displayList.length; i++)
        {
            data[i][0] = displayList[i][7];
            data[i][1] = displayList[i][5];
            data[i][2] = displayList[i][1];
            data[i][3] = displayList[i][2];
            data[i][4] = displayList[i][3];
            data[i][5] = Boolean.valueOf(displayList[i][9]);
            bikeButton = new JButton("\u22EE");
            bikeButton.addActionListener(v.getListener(View.ScreenListener.PAPER, displayList[i]));
            data[i][6] = bikeButton;
        }
        return data;
    }

    static private JTable getJTable(String[][] displayList, View v)
    {
        BikeTableModel b = new BikeTableModel(convertData(displayList, v), header);
        JTable table = new JTable(b);
        table.getColumn(" ").setCellRenderer(new ButtonRenderer());
        table.getColumn(" ").setCellEditor(new ButtonEditor());
        table.getColumn(" ").setMaxWidth(40);
        return table;
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
        return (JButton)value;
    }
}

class ButtonEditor extends DefaultCellEditor {
    public ButtonEditor() {
        super(new JCheckBox()); 
        // DefaultCellEditor does not have a no-arg constructor,
        // using JCheckBox constructor bc it's similar
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column)
    {
        return (JButton)value;
    }
}
