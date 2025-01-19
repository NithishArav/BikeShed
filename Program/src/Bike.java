/**
 * @author  Nithish Aravinthan
 */
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * 
 */
public class Bike
{
    private int id;
    private String make;
    private String model;
    private String color;
    private int serial;
    private String name;
    private int key;
    private LocalDate date_;
    private boolean new_;
    private String workDone;

    public static final int ID = 0;
    public static final int MAKE = 1;
    public static final int MODEL = 2;
    public static final int COLOR = 3;
    public static final int SERIAL = 4;
    public static final int NAME = 5;
    public static final int KEY = 6;
    public static final int DATE = 7;
    public static final int NEW = 8;
    public static final int WORKDONE = 9;

    public static final int LESSTHAN = 0;
    public static final int GREATERTHAN = 1;
    public static final int EQUALTO = 2;

    public static final int LENGTH = 10;

    public static final ArrayList<String> FILTERABLE_PARAMS = new ArrayList<String>() {
        {

        }
    };

    public static final ArrayList<String> COMPARABLE_PARAMS = new ArrayList<String>() {
    {

    }};

    public static final ArrayList<Integer> ALL_PARAMS = new ArrayList<Integer>() {
        {
            add(ID);
            add(MAKE);
            add(MODEL);
            add(COLOR);
            add(SERIAL);
            add(NAME);
            add(KEY);
            add(DATE);
            add(NEW);
            add(WORKDONE);
        }
    };

    public static final int NUM_PARAMS = ALL_PARAMS.size();

    /**
     * Returns true if parameter is defined in the Database filter method
     */
    public static boolean isFilterableParam(String param)
    {
        if (FILTERABLE_PARAMS.contains(param))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns true if parameter is defined in Database search and sort methods
     */
    public static boolean isComprableParam(String param)
    {
        if (COMPARABLE_PARAMS.contains(param))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns true if parameter is defined as an attribute of Paper
     */
    public static boolean isParam(int param)
    {
        if (ALL_PARAMS.contains(param))
        {
            return true;
        }
        return false;
    }

    /**
     * Empty contructor
     */
    public Bike()
    {
      
    }

    /**
     * Constructor setting object attributes to corresponding elements in data
     * @param data  CSVReader list of fields
     */
    public Bike(String[] data)
    {
        // To prevent index errors, add appropriate number of empty strings
        if (data.length<LENGTH)
        {
            String[] newData = new String[LENGTH];
            for (int i=0; i<LENGTH; i++)
            {
                if (i<data.length)
                {
                    newData[i] = data[i];
                }
                else
                {
                    newData[i] = "";
                }
            }
            data = newData;
        }

        id = Integer.parseInt(data[0]);
        make = data[1];
        model = data[2];
        color = data[3];
        serial = Integer.parseInt(data[4]);
        name = data[5];
        key = Integer.parseInt(data[6]);
        date_ = LocalDate.parse(data[7]);
        new_ = Boolean.parseBoolean(data[8]);

        workDone = data[9];
    }

    /**
     * Constructor setting object attributes to the corresponding substring of loadString
     * @param loadString    String with different fields delimited with semicolons
     */
    public Bike(String loadString)
    {
        this(loadString.split(";"));
    }

    /**
     * Return array list formatted for CSV writing
     * @return  params
     */
    public String[] asArray()
    {
        String[] params = new String[] {
            Integer.toString(id),
            make,
            model,
            color,
            Integer.toString(serial),
            name,
            Integer.toString(key),
            date_.toString(),
            Boolean.toString(new_),
            workDone
        };
        return params;
    }

    /**
     * Returns all object attributes delimited using semicolons
     * @return dumpString
     */
    public String toString()
    {
        String dumpString = "";
        for (String x : asArray())
        {
            dumpString += x;
            dumpString += ";";
        }
        return dumpString;
    }

    /**
     * Getter method for all attributes
     * @param param
     * @return
     */
    public String get(int param)
    {
        switch (param)
        {
            case ID:
                return Integer.toString(id);
            case MAKE:
                return make;
            case MODEL:
                return model;
            case COLOR:
                return color;
            case SERIAL:
                return Integer.toString(serial);
            case NAME:
                return name;
            case KEY:
                return Integer.toString(key);
            case DATE:
                return date_.toString();
            case NEW:
                return Boolean.toString(new_);
            case WORKDONE:
                return workDone;
            default:
                return null;
        }
    }

    /**
     * Setter method of all attributes
     * @param param Name of a defined Paper attribute
     * @param val String representation of the new value of the attribute
     */
    public void set(int param, String val)
    {
        switch (param)
        {
            case ID:
                id = Integer.parseInt(val);
            case MAKE:
                make = val;
                break;
            case MODEL:
                model = val;
                break;
            case COLOR:
                color = val;
                break;
            case SERIAL:
                serial = Integer.parseInt(val);
                break;
            case NAME:
                name = val;
                break;
            case KEY:
                key = Integer.parseInt(val);
                break;
            case DATE:
                date_ = LocalDate.parse(val);
                break;
            case NEW:
                new_ = Boolean.parseBoolean(val);
                break;
            case WORKDONE:
                workDone = val;
                break;
            default:
                break;
        }
    }

    /**
     * Equivalent to equals method, but with an attribute as an input
     * @param param Name of a defined Paper attribute
     * @param other String represention of the attribute to compare to
     * @return
     */
    public boolean paramIs(int param, String other)
    {
        String compVar = "";
        switch (param)
        {
            case ID:
                if (id == Integer.parseInt(other))
                {
                    return true;
                }
                return false;
            case MAKE:
                compVar = make;
                break;
            case MODEL:
                compVar = model;
                break;
            case COLOR:
                compVar = color;
                break;
            case SERIAL:
                return serial == Integer.parseInt(other);
            case NAME:
                compVar = name;
                break;
            case KEY:
                if (key == Integer.parseInt(other))
                {
                    return true;
                }
                return false;
            case DATE:
                return date_.equals(LocalDate.parse(other));
            case NEW:
                return (new_ == Boolean.parseBoolean(other));
            case WORKDONE:
                compVar = workDone;
                break;
            default:
                return false;
        }
        if (compVar.equalsIgnoreCase(other))
        {
            return true;
        }
        return false;
    }
    
    /**
     * Returns a negative integer if less than, 0 if equals, and positive integer if greater than
     * @param param Name of a defined Paper attribute
     * @param other String represention of the attribute to compare to
     * @return
     */
    public int compareParam(int param, String other)
    {
        // int compResult;
        // switch (param)
        // {
        //     case ID:
        //         return id - Integer.parseInt(other);
        //     case TITLE:
        //         return make.compareToIgnoreCase(other);
        //     case AUTHOR:
        //         String[] otherAuthor = other.substring(1, other.length()-1).split(",");
        //         compResult = author[1].compareToIgnoreCase(otherAuthor[1]);
        //         if (compResult != 0)
        //         {
        //             return compResult;
        //         }
        //         else
        //         {
        //             return author[0].compareToIgnoreCase(otherAuthor[0]);
        //         }
        //     case COLOR:
        //         compResult = 1;
        //         String[] otherArr = other.substring(1, other.length()-1).split(",");
        //         for (int x = 0; ((compResult != 0) 
        //                       && (compResult < field.size()) 
        //                       && (compResult < otherArr.length)); x++)
        //         {
        //             compResult = field.get(x).compareToIgnoreCase(otherArr[x]);
        //         }
        //         return compResult;
        //     case DATE:
        //         return publishDate.compareTo(LocalDate.parse(other));
        //     case JOURNAL:
        //         return journal.compareToIgnoreCase(other);
        //     case RATING:
        //         int otherInt = Integer.valueOf(other);
        //         return rating - otherInt;
        //     default:
        //         return "".compareTo(other);
        return 0;
    }
}
