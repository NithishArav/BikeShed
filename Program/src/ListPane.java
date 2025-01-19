import java.awt.*;

import javax.swing.*;

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
    }

    public ListPane(String[][] displayList, View view)
    {
        super(
            new ListContentPane(displayList, view), 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        v = view;
    }

    public void show(String[][] newContent)
    {
        setViewportView(new ListContentPane(newContent, v));
    }

    public Object[][] convertData(String[][] displayList)
    {
        Object[][] data = new Object[displayList.length][7];
        for (int i = 0; i < displayList.length; i++)
        {
            data[i][0] = displayList[i][7];
            data[i][1] = displayList[i][5];
            data[i][2] = displayList[i][1];
            data[i][3] = displayList[i][2];
            data[i][4] = displayList[i][3];
            data[i][5] = displayList[i][4];
        }
        return data;
    }

    static private class ListContentPane extends JTable
    {
        // public ListContentPane(String[][] displayList, View v)
        // {
        //     // if (displayList[0].length != NUM_PARAMS)
        //     // {
        //     //     throw new IndexOutOfBoundsException(
        //     //         "display_list row dimension should be " + NUM_PARAMS + ", is " + displayList[0].length);
        //     // }

        //     setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //     JPanel listPanel;
        //     JButton paperButton;
        //     for (String[] x : displayList)
        //     {
        //         // x = Arrays.copyOfRange(x, 1, x.length);

        //         listPanel = new JPanel(new GridBagLayout());
        //         listPanel.setSize(getToolkit().getScreenSize().width, 0);

        //         GridBagConstraints constraints = new GridBagConstraints();
        //         constraints.gridy = 0;
        //         for (int i = 0; i < 6; i++)
        //         {
        //             JLabel labelOne;
        //             labelOne = new JLabel(x[i+1]);
        //             constraints.gridx = i * 2;
        //             listPanel.add(labelOne, constraints);

        //             constraints.gridx = (i * 2) + 1;
        //             listPanel.add(new JLabel(" | "), constraints);
        //         }
        //         paperButton = new JButton("\u22EE");
        //         paperButton.addActionListener(v.getListener(View.ScreenListener.PAPER, x));
        //         listPanel.add(paperButton);
        //         listPanel.setAlignmentY(Panel.TOP_ALIGNMENT);
        //         listPanel.setMaximumSize(new Dimension(getToolkit().getScreenSize().width, 20));
        //         add(listPanel);
        //     }
        // }
        public ListContentPane(String[][] displayList, View v)
        {
            super(displayList, new String[] {"1", "2", "3", "4", "5", "6", "7"});
        }
    }

    // public static JTable newContentPane(String[][] displayList, View v)
    // {
    //     return new ListContentPane(displayList, v);
    // }
}
