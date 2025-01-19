import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


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

    ActionListener paperListener;
    View v;

    public MainScreen(View view, ActionListener searchListener, ActionListener addListener, ActionListener paperListener)
    {
        v = view;
        setLayout(new GridBagLayout());

        this.paperListener = paperListener;
        logo = new JLabel("My Research Paper Database");

        GridBagConstraints c;
        c = new GridBagConstraints() {
            {
                gridx = 0;
                gridy = 0;
                gridheight = 2;
            }};
        add(logo, c);

        searchButton = new JButton("Search");
        searchButton.setActionCommand("search");
        searchButton.addActionListener(searchListener);
        c = new GridBagConstraints() {
            {
                gridx = 1;
                gridy = 0;
            }};
        add(searchButton, c);

        addButton = new JButton("Add New Paper");
        // searchButton.setActionCommand("add");
        addButton.addActionListener(addListener);
        c = new GridBagConstraints() {
            {
                gridx = 1;
                gridy = 1;
            }};
        add(addButton, c);
    }

    public void setDatabase(String[][] db)
    {
        dbPane = new ListPane(db, v);
        dbPane.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 300)));
        GridBagConstraints c = new GridBagConstraints() {
            {
                gridx = 0;
                gridy = 2;
                gridwidth = 2;
            }};
        add(dbPane, c);
    }

    public void update()
    {
        dbPane.show(v.db.as2DArray());
    }

    public void active()
    {

    }
    
    public void reset()
    {

    }
}
