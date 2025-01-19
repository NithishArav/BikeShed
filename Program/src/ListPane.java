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
        "Date", "Name", "Make", "Model", "Color", "Checked Out", "Edit"
    };
    public ListPane()
    {
        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        BikeTableModel b = new BikeTableModel(list, header);
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
        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox()));
        return table;
    }

    // static private class ListContentPane extends JTable
    // {
    //     // public ListContentPane(String[][] displayList, View v)
    //     // {
    //     //     // if (displayList[0].length != NUM_PARAMS)
    //     //     // {
    //     //     //     throw new IndexOutOfBoundsException(
    //     //     //         "display_list row dimension should be " + NUM_PARAMS + ", is " + displayList[0].length);
    //     //     // }

    //     //     setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    //     //     JPanel listPanel;
    //     //     JButton paperButton;
    //     //     for (String[] x : displayList)
    //     //     {
    //     //         // x = Arrays.copyOfRange(x, 1, x.length);

    //     //         listPanel = new JPanel(new GridBagLayout());
    //     //         listPanel.setSize(getToolkit().getScreenSize().width, 0);

    //     //         GridBagConstraints constraints = new GridBagConstraints();
    //     //         constraints.gridy = 0;
    //     //         for (int i = 0; i < 6; i++)
    //     //         {
    //     //             JLabel labelOne;
    //     //             labelOne = new JLabel(x[i+1]);
    //     //             constraints.gridx = i * 2;
    //     //             listPanel.add(labelOne, constraints);

    //     //             constraints.gridx = (i * 2) + 1;
    //     //             listPanel.add(new JLabel(" | "), constraints);
    //     //         }
    //     //         paperButton = new JButton("\u22EE");
    //     //         paperButton.addActionListener(v.getListener(View.ScreenListener.PAPER, x));
    //     //         listPanel.add(paperButton);
    //     //         listPanel.setAlignmentY(Panel.TOP_ALIGNMENT);
    //     //         listPanel.setMaximumSize(new Dimension(getToolkit().getScreenSize().width, 20));
    //     //         add(listPanel);
    //     //     }
    //     // }
    //     public ListContentPane(String[][] displayList, View v)
    //     {
    //         super(BikeTableModel(convertData(displayList, v)));
    //     }
    // }

    // public static JTable newContentPane(String[][] displayList, View v)
    // {
    //     return new ListContentPane(displayList, v);
    // }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText(((JButton)value).getText());
        return (JButton)value;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = ((JButton)value).getText();
        // label = "\u22EE";
        button.setText(label);
        isPushed = true;
        return (JButton)value;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
