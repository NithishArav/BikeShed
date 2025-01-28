/**
 * @author  Nithish Aravinthan
 */

/**
 * Main java class, runnable
 * Initializes View object and invokes start method
 */
public class Main
{
    // private static String path = "src/data/bike_database.csv";
    // private static View v;
    public static void main(String[] args)
    {
        String path = "src/data/bike_database.csv";
        if (args.length != 0)
        {
            path = args[0];
        }
        View v = new View(path);
        javax.swing.SwingUtilities.invokeLater(() -> {
            v.start();
        });
    }
}
