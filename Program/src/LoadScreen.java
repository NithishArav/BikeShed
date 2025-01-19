import javax.swing.*;


/**
 * Component class
 * Referenced in View.java
 */
public class LoadScreen extends JPanel implements Screen
{
    public LoadScreen()
    {
        add(new JLabel("Loading . . ."));
    }

    public void reset()
    {

    }
}
