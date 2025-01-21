import java.awt.CardLayout;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;


public class AnswerPane extends JPanel
{
    private interface answerComponent
    {
        public String getText();
    }
    public static final String JTEXTFIELD = "JTextField";
    public static final String JTEXTAREA = "JTextArea";
    public static final String DATEPICKER = "Date Picker";

    // private JTextField textField;
    // private JTextArea textArea;
    // private DatePicker datePicker;

    private HashMap<String, answerComponent> entries = new HashMap<>();

    private CardLayout cards;

    private String type;

    public AnswerPane(String type)
    {
        cards = new CardLayout();
        setLayout(cards);
        AnswerField textField = new AnswerField();
        add(textField);
        entries.put(JTEXTFIELD, textField);

        AnswerArea textArea = new AnswerArea();
        add(textArea);
        entries.put(JTEXTAREA, textArea);

        DatePicker datePicker = new DatePicker();
        add(datePicker, DATEPICKER);
        entries.put(DATEPICKER, datePicker);

        this.type = type;
        switchType(type);
    }

    public final void switchType(String type)
    {
        this.type = type;
        cards.show(this, type);
    }

    private class AnswerField extends JTextField implements answerComponent {}

    private class AnswerArea extends JTextField implements answerComponent {}

    // private JTextField createJTextField()
    // {
    //     JTextField textField = new JTextField();
    //     return textField;
    // }

    // private JTextArea createJTextArea()
    // {
    //     JTextArea textArea = new JTextArea();
    //     return textArea;
    // }

    private class DatePicker extends JPanel implements answerComponent
    {
        JTextField year;

        JComboBox<String> month;
        String[] monthNames = new String[] {
            "January",
            "February",
            "March",
            "April", 
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        };

        JComboBox<Integer> day;
        HashMap<Integer, DefaultComboBoxModel<Integer>> dayNames = new HashMap<>();
        
        public DatePicker()
        {
            Integer[] dayArray;
            for (int i=31; i>=28; i--)
            {
                dayArray = new Integer[i];
                for (int j=0; j<i; j++)
                {
                    dayArray[j] = j+1;
                }
                dayNames.put(i, new DefaultComboBoxModel<>(dayArray));
            }
            month = new JComboBox<>(monthNames);
            day = new JComboBox<>(dayNames.get(31));
            month.addActionListener((ActionEvent e) -> 
            {   
                String selectedItem = (String)month.getSelectedItem();
                if (selectedItem.equals("February"))
                {
                    if (isLeapYear(Integer.parseInt(year.getText())))
                    {
                        day.setModel(dayNames.get(29));
                    }
                    else
                    {
                        day.setModel(dayNames.get(28));
                    }
                }
                else
                {
                    if (month.getSelectedIndex() % 2 == 0)
                    {
                        day.setModel(dayNames.get(31));
                    }
                    else
                    {
                        day.setModel(dayNames.get(30));
                    }
                }
            });
            
            year = new JTextField(4);
            add(month);
            add(day);
            add(year);
        }

        @Override
        public String getText()
        {
            String date;
            date = year.getText();
            date = "0".repeat(4 - date.length()) + date + "-";

            int d = (int)day.getSelectedItem();
            if (d < 10)
                {date = date + "0";}
            date = date + Integer.toString(d) + "-";
            
            int m = month.getSelectedIndex();
            if (m < 10)
                {date = date + "0";}
            date = date + Integer.toString(m);

            return date;
        }

        private static boolean isLeapYear(int y)
        {
            return ((y%400) == 0 || ((y%4) == 0) && ((y%100) == 0));
        }
    }

    public String getText()
    {
        return entries.get(type).getText();
    }
}
