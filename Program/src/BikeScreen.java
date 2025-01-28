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
    JButton checkIn;
    JTextArea workDone;

    String[] bike;

    View v;

    final String CHECKEDIN = "Check Out";
    final String CHECKEDOUT = "Check In";

    public BikeScreen(View view, ActionListener mainListener)
    {
        v = view;
        setLayout(new GridBagLayout());

        home = new JButton("home");
        home.addActionListener(mainListener);
        add(home, v.getConstraint(0, 0, 1, GridBagConstraints.NORTHWEST));

        add(new JLabel("Name: "), v.getConstraint(0, 1, 1, GridBagConstraints.EAST));
        name = new JTextField();
        name.setEditable(false);
        add(name, v.getConstraint(1, 1, 1, GridBagConstraints.WEST));
        
        add(new JLabel("Make: "), v.getConstraint(0, 2, 1, GridBagConstraints.EAST));
        make = new JTextField();
        make.setEditable(false);
        add(make, v.getConstraint(1, 2, 1, GridBagConstraints.WEST));

        add(new JLabel("Model: "), v.getConstraint(2, 2, 1, GridBagConstraints.EAST));
        model = new JTextField();
        model.setEditable(false);
        add(model, v.getConstraint(3, 2, 1, GridBagConstraints.WEST));

        add(new JLabel("Color: "), v.getConstraint(4, 2, 1, GridBagConstraints.EAST));
        color = new JTextField();
        color.setEditable(false);
        add(color, v.getConstraint(5, 2, 1, GridBagConstraints.WEST));

        add(new JLabel("Serial Number: "), v.getConstraint(0, 3, 1, GridBagConstraints.EAST));
        serial = new JTextField();
        serial.setEditable(false);
        add(serial, v.getConstraint(1, 3, 1, GridBagConstraints.WEST));
        
        add(new JLabel("Key Number: "), v.getConstraint(2, 3, 1, GridBagConstraints.EAST));
        key = new JTextField();
        key.setEditable(false);
        add(key, v.getConstraint(3, 3, 1, GridBagConstraints.WEST));

        add(new JLabel("Date: "), v.getConstraint(4, 3, 1, GridBagConstraints.EAST));
        date_ = new JTextField();
        date_.setEditable(false);
        add(date_, v.getConstraint(5, 3, 1, GridBagConstraints.WEST));

        add(new JLabel("Is new?: "), v.getConstraint(6, 3, 1, GridBagConstraints.EAST));
        new_ = new JTextField();
        new_.setEditable(false);
        add(new_, v.getConstraint(7, 3, 1, GridBagConstraints.WEST));

        checkIn = new JButton();
        add(checkIn, v.getConstraint(8, 2, 1, GridBagConstraints.CENTER));

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
        edit.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            buttonCards.show(buttonPane, "save");
            
            for (JTextComponent t : new JTextComponent[] {
                make, model, color, serial, name, key, date_,
                new_, workDone})
            {
                t.setEditable(true);
            }
            home.setEnabled(false);
            delete.setEnabled(false);
        });

        save = new JButton("save edits");
        save.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            save(buttonCards);
        });

        buttonPane.add(edit, "edit");
        buttonPane.add(save, "save");
        buttonCards.show(buttonPane, "edit");

        add(buttonPane, v.getConstraint(3, 1));
        
        delete = new JButton("delete");
        delete.addActionListener((ActionEvent e) -> {
            v.delete(Integer.parseInt(bike[0]));
            mainListener.actionPerformed(e);
        });
        add(delete, v.getConstraint(4, 1));

        checkIn.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            if (checkIn.getText().equals(CHECKEDIN))
            {
                checkIn.setText(CHECKEDOUT);
            }
            else
            {
                checkIn.setText(CHECKEDIN);
            }
            save(buttonCards);
        });
    }

    public void active ()
    {
        
    }
    public void active(String[] bikeValues)
    {
        bike = bikeValues;
        make.setText(bikeValues[1]);
        model.setText(bikeValues[2]);
        color.setText(bikeValues[3]);
        serial.setText(bikeValues[4]);
        name.setText(bikeValues[5]);
        key.setText(bikeValues[6]);
        date_.setText(bikeValues[7]);
        new_.setText(bikeValues[8]);
        if (bikeValues[9].equals("true"))
        {
            checkIn.setText("Check Out");
        }
        else
        {
            checkIn.setText("Check In");
        }
        workDone.setText(bikeValues[10]);
        v.update();
    }

    private void save(CardLayout buttonCards)
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
                bike[i++] = deleteExtraWhitespace(t.getText());
            }
            bike[10] = bike[9];
            if (checkIn.getText().equals(CHECKEDIN))
            {
                bike[9] = Boolean.toString(true);
            }
            else
            {
                bike[9] = Boolean.toString(false);
            }
            bike[2] = bike[2].toLowerCase();
            v.edit(bike);
            home.setEnabled(true);
            delete.setEnabled(true);
        }
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
    
    @Override
    public void reset()
    {

    }
}
