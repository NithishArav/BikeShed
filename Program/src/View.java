/**
 * @author  Nithish Aravinthan
 * Last modified: 2/21/24
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates GUI window using java swing and component classes
 * Referenced in Main.java
 */
public class View
{
    JFrame frame;
    MainScreen mainScreen;
    SearchScreen searchScreen;
    AddScreen addScreen;
    BikeScreen paperScreen;
    LoadScreen loadScreen;

    final static String MAINPANEL = "Card with Main Panel";
    final static String SEARCHPANEL = "Card with Search Panel";
    final static String ADDPANEL = "Card with Add Panel";
    final static String PAPERPANEL = "Card with Paper Panel";
    final static String LOADPANEL = "Card wtih Loading Panel";

    CardLayout cards;
    JPanel pane;

    String path;

    Database db;


    public View(String path)
    {
        this.path = path;
        pane = new JPanel(new CardLayout());

        ActionListener mainListener = new ScreenListener(ScreenListener.MAIN);
        ActionListener searchListener = new ScreenListener(ScreenListener.SEARCH);
        ActionListener addListener = new ScreenListener(ScreenListener.ADD);
        ActionListener paperListener = new ScreenListener(ScreenListener.PAPER);

        // Initialize all screen JPanel components and add to card layout
        pane.add(mainScreen = new MainScreen(this, searchListener, addListener, paperListener), MAINPANEL);
        pane.add(searchScreen = new SearchScreen(this, mainListener, paperListener), SEARCHPANEL);
        pane.add(addScreen = new AddScreen(this, mainListener), ADDPANEL);
        pane.add(paperScreen = new BikeScreen(this, mainListener), PAPERPANEL);
        pane.add(loadScreen = new LoadScreen(), LOADPANEL);
        
        cards = (CardLayout)(pane.getLayout());
        cards.show(pane, LOADPANEL);
    }
    
    
    public void start()
    {
        frame = new JFrame("My Research Paper Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(pane);

        frame.pack();
        frame.setVisible(true);

        setDatabase();
    }

    private void setDatabase()
    {
        db = new Database(path);
        mainScreen.setDatabase(db.as2DArray());
        cards.show(pane, MAINPANEL);
    }

    public void showMain()
    {
        cards.show(pane, MAINPANEL);
    }

    public String[][] getSearchResult(String[] searchParams)
    {
        Database returnDb;
        if (searchParams[0].equals("SEARCH%ALL"))
            returnDb = db;
        else
            returnDb = db.search(Integer.parseInt(searchParams[1]), searchParams[0]);

        // if (searchParams[3].equals("1"))
        // {
        //     returnDb = returnDb.filter(Integer.parseInt(searchParams[2]), searchParams[4], "<");
        // }
        // else if (searchParams[3].equals("2"))
        // {
        //     returnDb = returnDb.filter(Integer.parseInt(searchParams[2]), searchParams[4], ">");
        // }
        if (!(searchParams[3].equals("no filter")))
        {
            switch (searchParams[3])
            {
                case "lexographically before", "older than" -> {returnDb = returnDb.filter(
                    Integer.parseInt(searchParams[2]), searchParams[4], "<"
                );}
                case "lexographically after", "new than" -> {returnDb = returnDb.filter(
                    Integer.parseInt(searchParams[2]), searchParams[4], ">"
                );}
                case "is" -> {returnDb = returnDb.search(
                    Integer.parseInt(searchParams[2]), "true"
                );}
                case "is not" -> {returnDb = returnDb.search(
                    Integer.parseInt(searchParams[2]), "false"
                );}
                default -> {}
            }
        }

        return returnDb.as2DArray();
    }
    
    public class ScreenListener implements ActionListener
    {
        String command;
        public static final String MAIN = "main screen";
        public static final String SEARCH = "search screen";
        public static final String ADD = "add screen";
        public static final String PAPER = "add paper";

        String[] vals;
        
        public ScreenListener(String screenCommand)
        {
            this.command = screenCommand;
        }

        public ScreenListener(String screenCommand, String[] paperValues)
        {
            this.command = screenCommand;
            this.vals = paperValues;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            for (Component c : pane.getComponents())
            {
                if (c.isVisible())
                {
                    ((Screen)c).reset();
                }
            }
            switch (command)
            {
                case MAIN:
                    mainScreen.update();
                    cards.show(pane, MAINPANEL);
                    break;
                case SEARCH:
                    cards.show(pane, SEARCHPANEL);
                    break;
                case ADD:
                    addScreen.active();
                    cards.show(pane, ADDPANEL);
                default:
                    break;
            }

            if (vals != null)
            {
                switch (command)
                {
                    case PAPER -> {
                        paperScreen.active(vals);
                        cards.show(pane, PAPERPANEL);
                    }
                }
            }
        }
    }

    public ScreenListener getListener(String type, String[] paperValues)
    {
        return new ScreenListener(type, paperValues);
    }

    public void update()
    {
        frame.revalidate();
        frame.repaint();
    }

    public int getScreenHeight()
    {
        return (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    }

    public int getScreenWidth()
    {
        return (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }

    public void add(String[] newPaper)
    {
        if (newPaper.length != Bike.NUM_PARAMS)
        {
            throw new IndexOutOfBoundsException("Length of new paper is " + newPaper.length
                                                + "should be " + Bike.NUM_PARAMS);
        }
        db.add(newPaper);
    }

    public int getSize()
    {
        return db.size();
    }

    public void edit(String[] newPaper)
    {
        if (newPaper.length != Bike.LENGTH)
        {
            throw new IndexOutOfBoundsException("Edit paper has length " + newPaper.length 
                                                + " should be " + Bike.LENGTH);
        }
        db.edit(Integer.parseInt(newPaper[0]), newPaper);
    }

    public void delete(int index)
    {
        db.delete(index);
    }

    /**
     * Singleton helper class for all Component Classes
     * Used to simplify GrigBagLayouts
     */
    private static class Constraint
    {
        static GridBagConstraints self;

        /**
         * For singletons, constructor must be private
         */
        private Constraint()
        {
            // Private constructor to prevent direct instantiation
        }

        private static GridBagConstraints get(int gridx, int gridy, int gridwidth, int anchor)
        {
            if (self == null)
            {
                self = new GridBagConstraints();
                self.weightx = 1.0;
                self.weighty = 1.0;
            }

            self.gridx = gridx;
            self.gridy = gridy;
            self.gridwidth = gridwidth;
            self.anchor = anchor;

            return self;
        }
    }
        
    public GridBagConstraints getConstraint(int gridx, int gridy)
    {
        return Constraint.get(gridx, gridy, 1, GridBagConstraints.CENTER);
    }

    public GridBagConstraints getConstraint(int gridx, int gridy, int gridwidth)
    {
        return Constraint.get(gridx, gridy, gridwidth, GridBagConstraints.CENTER);
    }

    public GridBagConstraints getConstraint(int gridx, int gridy, int gridwidth, int anchor)
    {
        return Constraint.get(gridx, gridy, gridwidth, anchor);
    }
}