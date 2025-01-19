/**
 * @author  Nithish Aravinthan
 */
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * 
 */
public class Paper
{
    private int id;
    private String title;
    private String[] author = new String[2];
    private ArrayList<String> field = new ArrayList<>();
    private LocalDate publishDate;
    private String journal;
    private int rating;

    private String citation;
    private String summary;
    private String limitations;
    private String importance;

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String FIELD = "field";
    public static final String DATE = "date";
    public static final String JOURNAL = "journal";
    public static final String RATING = "rating";

    public static final String CITATION = "citation";
    public static final String SUMMARY = "summary";
    public static final String LIMITATIONS = "limitations";
    public static final String IMPORTANCE = "importance";

    public static final int LESSTHAN = 0;
    public static final int GREATERTHAN = 1;
    public static final int EQUALTO = 2;

    public static final int LENGTH = 11;

    public static final ArrayList<String> FILTERABLE_PARAMS = new ArrayList<String>() {
        {
            add(DATE);
            add(RATING);
        }
    };

    public static final ArrayList<String> COMPARABLE_PARAMS = new ArrayList<String>() {
    {
        add(TITLE);
        add(AUTHOR);
        add(FIELD);
        add(DATE);
        add(JOURNAL);
        add(RATING);
    }};

    public static final ArrayList<String> ALL_PARAMS = new ArrayList<String>() {
        {
            add(ID);
            addAll(COMPARABLE_PARAMS);
            add(CITATION);
            add(SUMMARY);
            add(LIMITATIONS);
            add(IMPORTANCE);
        }
    };

    public static final int NUM_PARAMS = ALL_PARAMS.size();

    /**
     * Returns true if parameter is defined in the Database filter method
     */
    public static boolean isFilterableParam(String param)
    {
        return FILTERABLE_PARAMS.contains(param);
    }

    /**
     * Returns true if parameter is defined in Database search and sort methods
     */
    public static boolean isComprableParam(String param)
    {
        return COMPARABLE_PARAMS.contains(param);
    }

    /**
     * Returns true if parameter is defined as an attribute of Paper
     */
    public static boolean isParam(String param)
    {
        return ALL_PARAMS.contains(param);
    }

    /**
     * Empty contructor
     */
    public Paper()
    {
      
    }

    /**
     * Constructor setting object attributes to corresponding elements in data
     * @param data  CSVReader list of fields
     */
    public Paper(String[] data)
    {
        id = Integer.parseInt(data[0]);
        title = data[1];

        author = new String[] {data[2].split(",")[1], data[2].split(",")[0]};

        String fieldString = data[3];
        field = new ArrayList<>();
        fieldString = fieldString.substring(1, fieldString.length() - 1);
        int commaIndex = fieldString.indexOf(",");
        while (commaIndex != -1)
        {
            field.add(fieldString.substring(0, commaIndex));
            if (fieldString.substring(commaIndex+1,commaIndex+2).equals(" "))
                fieldString = fieldString.substring(commaIndex+2);
            else
                fieldString = fieldString.substring(commaIndex+1);
            commaIndex = fieldString.indexOf(",");
        }
        field.add(fieldString);

        publishDate = LocalDate.parse(data[4]);

        journal = data[5];
        
        rating = Integer.parseInt(data[6]);

        citation = data[7];
        summary = data[8];
        limitations = data[9];
        importance = data[10];
    }

    /**
     * Constructor setting object attributes to the corresponding substring of loadString
     * @param loadString    String with different fields delimited with semicolons
     */
    public Paper(String loadString)
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
            title,
            (author[1] + "," + author[0]),
            field.toString(),
            publishDate.toString(),
            journal,
            Integer.toString(rating),
            citation, 
            summary, 
            limitations, 
            importance
        };
        return params;
    }

    /**
     * Returns all object attributes delimited using semicolons
     * @return dumpString
     */
    @Override
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
    public String get(String param)
    {
        return switch (param) {
            case ID -> Integer.toString(id);
            case TITLE -> title;
            case AUTHOR -> author[1] + "," + author[0];
            case FIELD -> field.toString();
            case DATE -> publishDate.toString();
            case JOURNAL -> journal;
            case RATING -> Integer.toString(rating);
            case CITATION -> citation;
            case SUMMARY -> summary;
            case LIMITATIONS -> limitations;
            case IMPORTANCE -> importance;
            default -> null;
        };
    }

    /**
     * Setter method of all attributes
     * @param param Name of a defined Paper attribute
     * @param value String representation of the new value of the attribute
     */
    public void set(String param, String value)
    {
        switch (param)
        {
            case ID:
                id = Integer.parseInt(value);
            case TITLE:
                title = value;
                break;
            case AUTHOR:
                String [] authorArr = value.substring(1, value.length()-1).split(",");
                author[0] = authorArr[1];
                author[1] = authorArr[0];
                break;
            case FIELD:
                String fieldString = value.substring(1, value.length() - 1);
                while (fieldString.indexOf(",") != -1)
                {
                    field.add(fieldString.substring(0, fieldString.indexOf(",")));
                    fieldString = fieldString.substring(fieldString.indexOf(",")+2);
                }
                field.add(fieldString);
                break;
            case DATE:
                publishDate = LocalDate.parse(value);
                break;
            case JOURNAL:
                journal = value;
                break;
            case RATING:
                rating = Integer.parseInt(value);
                break;
            case CITATION:
                citation = value;
                break;
            case SUMMARY:
                summary = value;
                break;
            case LIMITATIONS:
                limitations = value;
                break;
            case IMPORTANCE:
                importance = value;
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
    public boolean paramIs(String param, String other)
    {
        String compVar = "";
        switch (param)
        {
            case ID -> {
                if (id == Integer.parseInt(other))
                {
                    return true;
                }
                return false;
            }
            case TITLE -> compVar = title;
            case AUTHOR -> compVar = author[1] + " " + author[0];
            case FIELD -> {
                if (field.contains(other))
                {
                    return true;
                }
                compVar = "";
            }
            case DATE -> compVar = publishDate.toString();
            case JOURNAL -> compVar = journal;
            case RATING -> {
                return rating == Integer.parseInt(other);
            }

            case CITATION -> compVar = citation;
            case SUMMARY -> compVar = summary;
            case LIMITATIONS -> compVar = limitations;
            case IMPORTANCE -> compVar = importance;
            default -> {
                return false;
            }
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
    public int compareParam(String param, String other)
    {
        int compResult;
        switch (param)
        {
            case ID -> {
                return id - Integer.parseInt(other);
            }
            case TITLE -> {
                return title.compareToIgnoreCase(other);
            }
            case AUTHOR -> {
                String[] otherAuthor = other.substring(1, other.length()-1).split(",");
                compResult = author[1].compareToIgnoreCase(otherAuthor[1]);
                if (compResult != 0)
                {
                    return compResult;
                }
                else
                {
                    return author[0].compareToIgnoreCase(otherAuthor[0]);
                }
            }
            case FIELD -> {
                compResult = 1;
                String[] otherArr = other.substring(1, other.length()-1).split(",");
                for (int x = 0; ((compResult != 0)
                        && (compResult < field.size())
                        && (compResult < otherArr.length)); x++)
                {
                    compResult = field.get(x).compareToIgnoreCase(otherArr[x]);
                }
                return compResult;
            }
            case DATE -> {
                return publishDate.compareTo(LocalDate.parse(other));
            }
            case JOURNAL -> {
                return journal.compareToIgnoreCase(other);
            }
            case RATING -> {
                int otherInt = Integer.valueOf(other);
                return rating - otherInt;
            }
            default -> {
                return "".compareTo(other);
            }
        }
    }
}
