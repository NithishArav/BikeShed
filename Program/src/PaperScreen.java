/**
 * @author  Nithish Aravinthan
 */
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.*;
import javax.swing.text.JTextComponent;


/**
 * Class structure for Paper objects to be stored
 * Referenced in Database.java
 */
public class PaperScreen extends JPanel implements Screen
{
    JButton home;

    JButton edit;
    JButton save;
    JPanel buttonPane;

    JButton delete;

    JTextField title;
    JTextField author;
    JTextArea field;
    JTextField date;
    JTextField journal;
    JTextField rating;

    JTextArea citation;
    JTextArea summary;
    JTextArea limitations;
    JTextArea importance;

    String[] paper;

    View v;

    public PaperScreen(View view, ActionListener mainListener)
    {
        v = view;
        setLayout(new GridBagLayout());

        home = new JButton("home");
        home.addActionListener(mainListener);
        add(home, v.getConstraint(0, 0));

        add(new JLabel("Title: "), v.getConstraint(0, 1));
        title = new JTextField();
        title.setEditable(false);
        add(title, v.getConstraint(1, 1));

        add(new JLabel("Author: "), v.getConstraint(0, 2));
        author = new JTextField();
        author.setEditable(false);
        add(author, v.getConstraint(1, 2));

        add(new JLabel("Fields: "), v.getConstraint(2, 2));
        field = new JTextArea(1, 0);
        field.setEditable(false);
        JScrollPane fieldPane = new JScrollPane(field, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fieldPane.setPreferredSize(new Dimension(300, (int)field.getPreferredSize().getHeight()+20));
        add(fieldPane, v.getConstraint(3, 2));

        add(new JLabel("Journal: "), v.getConstraint(4, 2));
        journal = new JTextField();
        journal.setEditable(false);
        add(journal, v.getConstraint(5, 2));

        add(new JLabel("Date: "), v.getConstraint(6, 2));
        date = new JTextField();
        date.setEditable(false);
        add(date, v.getConstraint(7, 2));
        
        add(new JLabel("Rating: "), v.getConstraint(8, 2));
        rating = new JTextField();
        rating.setEditable(false);
        add(rating, v.getConstraint(9, 2));

        int textHeight = v.getScreenHeight() - 200;
        int textWidth = v.getScreenWidth() / 4;
        textWidth -= 20;


        add(new JLabel("Citation: "), v.getConstraint(0, 3));
        add(new JLabel("Summary:"), v.getConstraint(2, 3));
        add(new JLabel("Limitations:"), v.getConstraint(4, 3));
        add(new JLabel("Importance:"), v.getConstraint(6, 3));

        citation = new JTextArea();
        summary = new JTextArea();
        limitations = new JTextArea();
        importance = new JTextArea();

        JScrollPane textContainer;
        int col = 0;

        for (JTextArea t : new JTextArea[] {citation, summary, limitations, importance})
        {
            t.setLineWrap(true);
            t.setWrapStyleWord(true);
            t.setEditable(false);
            textContainer = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            textContainer.setPreferredSize(new Dimension(textWidth, textHeight));
            if (col == 6)
            {
                add(textContainer, v.getConstraint(col, 4, 4));
            }
            else
            {
                add(textContainer, v.getConstraint(col, 4, 2));
            }
            col += 2;
        }

        CardLayout buttonCards = new CardLayout();
        buttonPane = new JPanel(buttonCards);

        edit = new JButton("edit");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonCards.show(buttonPane, "save");

                for (JTextComponent t : new JTextComponent[] {
                    title, author, field, date, journal, rating, 
                    citation, summary, limitations, importance})
                {
                    t.setEditable(true);
                }
                home.setEnabled(false);
                delete.setEnabled(false);
            }
        });

        save = new JButton("save edits");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (isAllFormat())
                {
                    buttonCards.show(buttonPane, "edit");
                    int i = 1;
                    for (JTextComponent t : new JTextComponent[] {
                        title, author, field, date, journal, rating, 
                        citation, summary, limitations, importance})
                    {
                        t.setEditable(false);
                        if (i == 3)
                            paper[i++] = "[" + t.getText() + "]";
                        else
                            paper[i++] = deleteExtraWhitespace(t.getText());
                    }
                    v.edit(paper);
                    home.setEnabled(true);
                    delete.setEnabled(true);
                }
            }
        });

        buttonPane.add(edit, "edit");
        buttonPane.add(save, "save");
        buttonCards.show(buttonPane, "edit");

        add(buttonPane, v.getConstraint(3, 1));
        
        delete = new JButton("delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                v.delete(Integer.valueOf(paper[0]));
                mainListener.actionPerformed(e);
            }
        });
        add(delete, v.getConstraint(4, 1));
    }

    public void active ()
    {
        
    }
    public void active(String[] paperValues)
    {
        paper = paperValues;
        title.setText(paperValues[1]);
        author.setText(paperValues[2]);
        String fieldString = paperValues[3].substring(1, paperValues[3].length()-1);
        field.setText(fieldString);
        field.setSize((int)field.getPreferredSize().getWidth(), 10);

        date.setText(paperValues[4]);
        journal.setText(paperValues[5]);
        rating.setText(paperValues[6]);

        citation.setText(paperValues[7]);
        summary.setText(paperValues[8]);
        limitations.setText(paperValues[9]);
        importance.setText(paperValues[10]);
        v.update();
    }

    private boolean isAllFormat()
    {
        if (author.getText().indexOf(",") == -1)
        {
            JOptionPane.showMessageDialog(v.frame, "Invalid format for author\nPlease separate first and last name with a comma");
            return false;
        }

        try
        {
            LocalDate.parse(date.getText());
        }
        catch (DateTimeParseException e)
        {
            JOptionPane.showMessageDialog(v.frame, "Invalid date format\nPlease use YYYY-MM-DD");
            return false;
        }

        String ratingText = rating.getText();
        if (!(ratingText.matches("[1-9]") || ratingText.equals("10")))
        {
            JOptionPane.showMessageDialog(v.frame, "Invalid format for rating\nPlease use an integer between 1 and 10");
            return false;
        }

        return true;
    }

    public static String deleteExtraWhitespace(String input)
    {
        String out = input.trim();
        for (int i = 0; i < out.length()-1; i++)           
        {
            if (out.substring(i, i+1).equals(","))
            {
                if (out.substring(i+1, i+2).equals(" "))
                {
                    out = out.substring(0, i+1) + out.substring(i+2);
                }
            }
        }
        return out;
    }
    
    public void reset()
    {

    }
}
