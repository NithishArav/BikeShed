import java.awt.CardLayout;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;


public class AnswerPane extends JPanel
{
    public static final String TEXTFIELD = "JTextField";
    public static final String TEXTAREA = "JTextArea";
    public static final String DATEPICKER = "Date Picker";

    private JTextField textField;
    private JTextArea textArea;
    private DatePicker datePicker;

    private CardLayout cards;

    private String type;

    public AnswerPane(String type)
    {
        cards = new CardLayout();
        setLayout(cards);

        textField = new JTextField();
        add(textField, TEXTFIELD);

        textArea = new JTextArea();
        textArea.setEditable(true);
        add(textArea, TEXTAREA);

        datePicker = new DatePicker();
        add(datePicker, DATEPICKER);

        this.type = type;
        switchType(type);
    }

    // public AnswerPane(int rows, int columns)
    // {
    //     textArea = new JTextArea(rows, columns);
    // }

    public final void switchType(String type)
    {
        this.type = type;
        cards.show(this, type);
    }

    // private class AnswerField extends JTextField implements answerComponent
    // {
    //     @Override
    //     public String getText()
    //     {
    //         return super.getText();
    //     }

    //     @Override
    //     public void setText(String t) {
    //         super.setText(t);
    //     }
    // }

    // private class AnswerArea extends JTextArea implements answerComponent
    // {
    //     public AnswerArea()
    //     {

    //     }

    //     public AnswerArea(int rows, int columns)
    //     {
    //         super(rows, columns);
    //     }
    // }

    private class DatePicker extends JPanel
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

        public void setText(String t) {
            year.setText(t.substring(0, 4));
            month.setSelectedIndex(Integer.parseInt(t.substring(5, 7)));
            day.setSelectedIndex(Integer.parseInt(t.substring(8, 10)));
        }

        private static boolean isLeapYear(int y)
        {
            return ((y%400) == 0 || ((y%4) == 0) && ((y%100) == 0));
        }
    }

    public Object get()
    {
        return switch (type)
        {
            case TEXTFIELD -> textField;
            case TEXTAREA -> textArea;
            case DATEPICKER -> datePicker;
            default -> null;
        };
    }

    public String getText()
    {
        return switch (type)
        {
            case TEXTFIELD -> textField.getText();
            case TEXTAREA -> textArea.getText();
            case DATEPICKER -> datePicker.getText();
            default -> null;
        };
    }

    public void setText(String t)
    {
        switch (type)
        {
            case TEXTFIELD -> textField.setText(t);
            case TEXTAREA -> textArea.setText(t);
            case DATEPICKER -> datePicker.setText(t);
            default -> {}
        };
    }

    @Override
    public boolean requestFocusInWindow()
    {
        return switch (type)
        {
            case TEXTAREA -> textArea.requestFocusInWindow();
            default -> false;
        };
    }
}
