/**
 * @author  Nithish Aravinthan
 */

/**
 * Main java class, runnable
 * Initializes View object and invokes start method
 */
public class Main
{
    public static final String PATH = "src/data/paper_database.csv";
    public static void main(String[] args)
    {
        View v = new View(PATH);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                v.start();
            }
        });
    }
}
