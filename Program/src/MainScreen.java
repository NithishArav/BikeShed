import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Component class
 * Referenced in View.java
 */
public class MainScreen extends JPanel implements Screen
{
    JLabel logo;
    JButton searchButton;
    JButton addButton;
    ListPane dbPane;

    // ActionListener paperListener;
    View v;

    public MainScreen(View view, ActionListener searchListener, ActionListener addListener, ActionListener paperListener)
    {
        v = view;
        setLayout(new GridBagLayout());

        // this.paperListener = paperListener;
        logo = new JLabel("Bicycle Database");
        logo.setFont(new Font("Georgia", Font.PLAIN, 36));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(logo, c);

        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        searchButton = new JButton("Search");
        searchButton.setActionCommand("search");
        searchButton.addActionListener(searchListener);
        add(searchButton, c);

        c.gridy = 1;
        addButton = new JButton("Register Bicycle");
        addButton.addActionListener(addListener);
        add(addButton, c);
    }

    public void setDatabase(String[][] db)
    {
        dbPane = new ListPane(db, v);
        dbPane.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 300)));
        add(dbPane, v.getConstraint(0, 2, 2));
    }

    public void update()
    {
        dbPane.show(v.db.as2DArray());
    }

    public void active()
    {

    }
    
    @Override
    public void reset()
    {

    }
}
