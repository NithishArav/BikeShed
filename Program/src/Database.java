/**
 * @author  Nithish Aravinthan
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

// import com.opencsv.CSVParser;
// import com.opencsv.CSVParserBuilder;
// import com.opencsv.CSVReader;
// import com.opencsv.CSVReaderBuilder;
// import com.opencsv.CSVWriter;

/**
 * Loads, edits, and saves the database of Paper objects
 * Referenced in View.java
 */
public class Database
{
    private String dPath;
    private String path;
    private ArrayList<Paper> db;

    /**
     * no-arg constructor, initializes empty database
     */
    public Database()
    {
        db = new ArrayList<>();
    }

    /**
     * 
     * @param filename relative path to existing .csv file to store database
     */
    public Database(String filename)
    {
        this();

        // Processing filename to avoid errors
        dPath = new File(".").getAbsolutePath();
        dPath = dPath.substring(0, dPath.length()-1);
        path = dPath + filename;
        path = path.trim();
        load();
    }

    // public void save()
    // {
    //     try
    //     {
    //         FileWriter outputFile = new FileWriter(new File(path), false);
    //         CSVWriter writer = new CSVWriter(outputFile, ';', '"', '\\', "\n");
    //         for (Paper p : db)
    //         {
    //             writer.writeNext(p.asArray());
    //         }
    //         writer.close();
    //         outputFile.close();
    //     }
    //     catch (Exception e)
    //     {
    //         System.out.println("Could not save file: ");
    //         e.printStackTrace();
    //     }
    //     backup();
    // }
    public void save()
    {
        try (FileWriter writer = new FileWriter(new File(path), false)) 
        {
                writer.write(asString());
        }
        catch (Exception e)
        {
            System.out.println("Could not save file");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void backup()
    {
        // update change_log.txt with the last change made to the database
        // +i_,_:
        // +d_,_:
    }

    // public void load()
    // {
    //     try
    //     {
    //         File db_file = new File(path);
    //         CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
    //         CSVReader reader = new CSVReaderBuilder(new FileReader(db_file)).withCSVParser(parser).build();
    //         String[] nextLine;
    //         while ((nextLine = reader.readNext()) != null)
    //         {
    //             db.add(new Paper(nextLine));
    //         }
    //         reader.close();
    //     }
    //     catch (Exception e)
    //     {
    //         System.out.print("Could not load database: ");
    //         e.printStackTrace();
    //     }
    // }
    private void load()
    {
        try (BufferedReader reader = new BufferedReader( new FileReader(new File(path))))
        {
            String nextLine;
            while ((nextLine = reader.readLine()) != null)
            {
                db.add(new Paper(nextLine));
            }
        }
        catch (Exception e)
        {
            System.out.println("Could not load file");
            e.printStackTrace();
            // for (StackTraceElement i : e.getStackTrace())
            // {
            //     System.out.println(i);
            // }
        }
    }

    public int size()
    {
        return db.size();
    }

    public ArrayList<Paper> getDb()
    {
        return db;
    }

    public int indexOf(Paper value)
    {
        return db.indexOf(value);
    }

    public void add(Paper newPaper)
    {
        db.add(newPaper);
    }

    public void add(String[] data)
    {
        if (data.length == Paper.LENGTH)
        {
            ArrayList<String> tempArray = new ArrayList<String>();
            tempArray.add(Integer.toString(size()));
            tempArray.addAll(Arrays.asList(data));
        }
        db.add(new Paper(data));
        save();
    }

    // public void add(String loadString)
    // {
    //     db.add(new Paper(loadString));
    // }

    public void add(int index, Paper newPaper)
    {
        db.add(index, newPaper);
        save();
    }

    public Paper get(int index)
    {
        return db.get(index);
    }

    public void edit(int index, Paper newPaper)
    {
        db.remove(index);
        db.add(index, newPaper);
        save();
    }

    public void edit(int index, String[] newPaper)
    {
        db.remove(index);
        db.add(index, new Paper(newPaper));
        save();
    }

    public void edit(int index, String param, String value)
    {
        db.get(index).set(param, value);
        save();
    }

    public void delete(int index)
    {
        db.remove(index);
        save();
    }

    public Database search(String param, String value)
    {
        Database returnDb = new Database();
        for (Paper p : db)
        {
            if (p.get(param).equals(value))
            {
                returnDb.add(p);
            }
        }
        return returnDb;
    }

    public Database sort(String param, boolean increasing)
    {
        // using insertion sort
        int index = 0;
        int compResult = 0;
        Database returnDb = new Database();
        for (int i = 0; i < db.size(); i++)
        {
            Paper p = db.get(i);
            for (int j = i-1; j >= 0; j--)
            {
                compResult = p.compareParam(param, returnDb.db.get(j).get(param));
                if (compResult == 0)
                {
                    // if they are the same, add to the following index to keep sort stable
                    index = j + 1;
                    break;
                }
                if (!increasing)
                {
                    compResult *= -1;
                }
                if (compResult > 0)
                {
                    index = j + 1;
                    break;
                }
            }
            if (index == returnDb.size())
            {
                returnDb.db.add(p);
            }
            else
            {
                returnDb.db.add(index, p);
            }
        }
        return returnDb;
    }

    public Database sort(String param)
    {
        return sort(param, true);
    }

    public Database filter(String param, String threshold, String comparator)
    {
        int result;
        Database returnDb = new Database();
        for (Paper p : db)
        {
            result = p.compareParam(param, threshold);
            if (result < 0)
            {
                if (comparator.equals("<") || comparator.equals("<="))
                    {
                        returnDb.add(p);
                    }
            }
            else if (result > 0)
            {
                if (comparator.equals(">") || comparator.equals(">="))
                    {
                        returnDb.add(p);
                    }
            }
            else
            {
                if (comparator.equals("<=") || comparator.equals(">="))
                    {
                        returnDb.add(p);
                    }
            }
        }
        return returnDb;
    }

    public String asString()
    {
        String returnString = "";

        for (String s : asArray())
        {
            returnString += s;
        }

        return returnString;
    }

    public String[] asArray()
    {
        String[] returnArray = new String[size()];
        for (int i = 0; i < size(); i++)
        {
            returnArray[i] = db.get(i).toString();
        }
        return returnArray;
    }

    public String[][] as2DArray()
    {
        String[][] returnArray = new String[size()][Paper.NUM_PARAMS];
        for (int i = 0; i < size(); i++)
        {
            returnArray[i] = db.get(i).asArray();
        }
        return returnArray;
    }

    @Override
    public String toString()
    {
        String returnString = "";
        for (Paper p : db)
        {
            returnString += "\""+p.get(Paper.TITLE)+"\""
                + ", by " + p.get(Paper.AUTHOR)
                + " (" + p.get(Paper.DATE) 
                + ") Rating: " + p.get(Paper.RATING)+ "\n";
        }
        return returnString;
    }

    public String toString(Database reference)
    {
        String returnString = "";
        for (Paper p : db)
        {
            returnString += "id: #" + reference.db.indexOf(p) + ", \""+p.get(Paper.TITLE)+"\""
                + ", by " + p.get(Paper.AUTHOR)
                + " (" + p.get(Paper.DATE) 
                + ") Rating: " + p.get(Paper.RATING)+ "\n";
        }
        return returnString;
    }
}
