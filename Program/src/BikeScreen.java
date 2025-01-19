/**
 * @author  Nithish Aravinthan
 */
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.text.JTextComponent;


/**
 * Class structure for Paper objects to be stored
 * Referenced in Database.java
 */
public class BikeScreen extends JPanel implements Screen
{
    JButton home;

    JButton edit;
    JButton save;
    JPanel buttonPane;

    JButton delete;

    JTextField make;
    JTextField model;
    JTextField color;
    JTextField serial;
    JTextField name;
    JTextField key;
    JTextField date_;
    JTextField new_;
    JTextArea workDone;

    String[] paper;

    View v;

    public BikeScreen(View view, ActionListener mainListener)
    {
        v = view;
        setLayout(new GridBagLayout());

        home = new JButton("home");
        home.addActionListener(mainListener);
        add(home, v.getConstraint(0, 0));

        add(new JLabel("Make: "), v.getConstraint(0, 1));
        make = new JTextField();
        make.setEditable(false);
        add(make, v.getConstraint(1, 1));

        add(new JLabel("Model: "), v.getConstraint(0, 2));
        model = new JTextField();
        model.setEditable(false);
        add(model, v.getConstraint(1, 2));

        add(new JLabel("Color: "), v.getConstraint(2, 2));
        color = new JTextField();
        color.setEditable(false);
        add(color, v.getConstraint(3, 2));

        add(new JLabel("Serial Number: "), v.getConstraint(4, 2));
        serial = new JTextField();
        serial.setEditable(false);
        add(serial, v.getConstraint(5, 2));

        add(new JLabel("Name: "), v.getConstraint(6, 2));
        name = new JTextField();
        name.setEditable(false);
        add(name, v.getConstraint(7, 2));
        
        add(new JLabel("Key Number: "), v.getConstraint(0, 3));
        key = new JTextField();
        key.setEditable(false);
        add(key, v.getConstraint(1, 3));

        add(new JLabel("Date: "), v.getConstraint(2, 3));
        date_ = new JTextField();
        date_.setEditable(false);
        add(date_, v.getConstraint(3, 3));

        add(new JLabel("Is new?: "), v.getConstraint(4, 3));
        new_ = new JTextField();
        new_.setEditable(false);
        add(new_, v.getConstraint(5, 3));

        int textHeight = v.getScreenHeight() - 200;
        int textWidth = v.getScreenWidth() / 4;
        textWidth -= 20;


        add(new JLabel("Work Done:"), v.getConstraint(0, 4));

        workDone = new JTextArea();

        JScrollPane textContainer;

        workDone.setLineWrap(true);
        workDone.setWrapStyleWord(true);
        workDone.setEditable(false);
        textContainer = new JScrollPane(workDone, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textContainer.setPreferredSize(new Dimension(textWidth, textHeight));
        add(textContainer, v.getConstraint(0, 5, 2));

        CardLayout buttonCards = new CardLayout();
        buttonPane = new JPanel(buttonCards);

        edit = new JButton("edit");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonCards.show(buttonPane, "save");

                for (JTextComponent t : new JTextComponent[] {
                    make, model, color, serial, name, key, date_,
                    new_, workDone})
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
                        make, model, color, serial, name, key, date_,
                        new_, workDone})
                    {
                        t.setEditable(false);
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
    public void active(String[] bikeValues)
    {
        System.out.println("BikeScreen:177" + Arrays.toString(bikeValues));
        paper = bikeValues;
        make.setText(bikeValues[1]);
        model.setText(bikeValues[2]);
        color.setText(bikeValues[3]);
        serial.setText(bikeValues[4]);
        name.setText(bikeValues[5]);
        key.setText(bikeValues[6]);
        date_.setText(bikeValues[7]);
        new_.setText(bikeValues[8]);
        workDone.setText(bikeValues[9]);
        v.update();
    }

    private boolean isAllFormat()
    {
        try
        {
            LocalDate.parse(date_.getText());
        }
        catch (DateTimeParseException e)
        {
            JOptionPane.showMessageDialog(v.frame, "Invalid date format\nPlease use YYYY-MM-DD");
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
