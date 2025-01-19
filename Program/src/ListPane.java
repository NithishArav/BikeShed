import java.awt.*;
import javax.swing.*;

/**
 * Component class for showing database
 */
public class ListPane extends JScrollPane
{
    // String[][] list;
    View v;
    // private static final int NUM_PARAMS = Bike.LENGTH;
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

    static private class ListContentPane extends JPanel
    {
        public ListContentPane(String[][] displayList, View v)
        {
            // if (displayList[0].length != NUM_PARAMS)
            // {
            //     throw new IndexOutOfBoundsException(
            //         "display_list row dimension should be " + NUM_PARAMS + ", is " + displayList[0].length);
            // }

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JPanel listPanel;
            JButton paperButton;
            for (String[] x : displayList)
            {
                // x = Arrays.copyOfRange(x, 1, x.length);

                listPanel = new JPanel(new GridBagLayout());
                listPanel.setSize(getToolkit().getScreenSize().width, 0);

                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridy = 0;
                for (int i = 0; i < 6; i++)
                {
                    JLabel labelOne;
                    labelOne = new JLabel(x[i+1]);
                    constraints.gridx = i * 2;
                    listPanel.add(labelOne, constraints);

                    constraints.gridx = (i * 2) + 1;
                    listPanel.add(new JLabel(" | "), constraints);
                }
                paperButton = new JButton("\u22EE");
                paperButton.addActionListener(v.getListener(View.ScreenListener.PAPER, x));
                listPanel.add(paperButton);
                listPanel.setAlignmentY(Panel.TOP_ALIGNMENT);
                listPanel.setMaximumSize(new Dimension(getToolkit().getScreenSize().width, 20));
                add(listPanel);
            }
        }
    }

    public static JPanel newContentPane(String[][] displayList, View v)
    {
        return new ListContentPane(displayList, v);
    }
}
