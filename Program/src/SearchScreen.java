import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import javax.swing.*;


/**
 * Component Class
 * Referenced in View.java
 * Creates screen to search, sort, and filter database
 */
public class SearchScreen extends JPanel implements Screen
{
    JTextField searchInput;
    JButton searchButton;
    JComboBox<String> searchParam;
    JComboBox<String> filterParam;
    JComboBox<String> filterInput;
    JTextField filterValue;
    JScrollPane dbPane;
    ListPane listPane;
    
    JButton homeButton;

    // ActionListener paperListener;
    final static String STR = "string";
    final static String DATE = "date";
    final static String INT = "int";
    final static String BOOL = "bool";

    private String[] chosen = new String[7];
    private LinkedHashMap<String, String> searchItems = new LinkedHashMap<>();

    private LinkedHashMap<String, String> filterParams = new LinkedHashMap<>();
    private LinkedHashMap<String, String[]> filterItems = new LinkedHashMap<>();

    View v;

    public SearchScreen(View view, ActionListener mainListener, ActionListener paperListener)
    {
        v = view;
        searchItems.put("see all", STR);
        searchItems.put("make", STR);
        searchItems.put("model", STR);
        searchItems.put("color", STR);
        searchItems.put("date", DATE);

        filterParams.put("date", DATE);
        filterParams.put("new", BOOL);
        filterParams.put("checked in", BOOL);

        filterItems.put(STR, new String[] {"no filter", "lexographically before", "lexographically after"});
        filterItems.put(DATE, new String[] {"no filter", "older than", "newer than"});
        filterItems.put(INT, new String[] {"no filter", "less than", "greater than"});
        filterItems.put(BOOL, new String[] {"no filter", "is", "is not"});

        // this.paperListener = paperListener;

        setLayout(new GridBagLayout());

        searchInput = new JTextField();
        searchInput.setPreferredSize(new Dimension(v.getScreenWidth()-20, 30));

        homeButton = new JButton("home");
        homeButton.addActionListener(mainListener);

        add(homeButton, v.getConstraint(0, 0));

        add(searchInput, v.getConstraint(0, 1, 6));

        add(new JLabel("Search in:"), v.getConstraint(0, 2));
        searchParam = new JComboBox<>(searchItems.keySet().toArray(new String[searchItems.size()]));
        add(searchParam, v.getConstraint(0, 3));
        
        add(new JLabel("Filter by:"), v.getConstraint(1, 2));
        filterParam = new JComboBox<>(filterParams.keySet().toArray(new String[filterParams.size()]));
        filterInput = new JComboBox<>(filterItems.get(DATE));
        filterValue = new JTextField(5);

        filterParam.addActionListener((ActionEvent e) -> {
            @SuppressWarnings("unchecked")
                    JComboBox<String> cb = (JComboBox<String>)e.getSource();
            String selectedItem = (String)cb.getSelectedItem();
            filterInput.setModel(new DefaultComboBoxModel<>(filterItems.get(filterParams.get(selectedItem))));
        });
        filterValue.addActionListener((ActionEvent e) -> {
            JTextField field = (JTextField)e.getSource();
            String input = field.getText();
            chosen[6] = input;
        });
        add(filterParam, v.getConstraint(1, 3));
        add(filterInput, v.getConstraint(2, 3));
        add(filterValue, v.getConstraint(3, 3));

        JLabel myLabel = new JLabel("Hello there!");
        JLabel noResult = new JLabel("No results found");

        searchButton = new JButton("Search");
        searchButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            if (searchParam.getSelectedIndex() == 0)
            {
                chosen[0] = "SEARCH%ALL";
            }
            else
            {
                chosen[0] = searchInput.getText();
            }
            chosen[1] = Integer.toString(Bike.STRING2PARAM((String)searchParam.getSelectedItem()));
            chosen[2] = Integer.toString(Bike.STRING2PARAM((String)filterParam.getSelectedItem()));
            chosen[3] = (String)filterInput.getSelectedItem();
            chosen[4] = filterValue.getText();
            
            String[][] searchResult = v.getSearchResult(chosen);
            if (searchResult.length == 0)
            {
                dbPane.setViewportView(noResult);
                add(dbPane, v.getConstraint(0, 5, 4));
            }
            else
            {
                listPane = new ListPane(searchResult, v);
                dbPane.setViewportView(listPane);
                add(dbPane, v.getConstraint(0, 5, 4));
            }
            v.update();
        });

        add(searchButton, v.getConstraint(0, 4, 4));

        dbPane = new JScrollPane(myLabel);
        dbPane.setPreferredSize(new Dimension(v.getScreenWidth()-20, v.getScreenHeight()-400));
        add(dbPane, v.getConstraint(0, 5, 4));
    }

    public void active() {

    }
    
    public void active(String[][] displayList)
    {
        dbPane = new ListPane(displayList, v);
    }

    @Override
    public void reset()
    {
        dbPane.setViewportView(null);
    }

    public String[] getChosen()
    {
        return chosen;
    }
}
