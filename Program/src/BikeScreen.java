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
    AnswerPane date_;
    AnswerPane new_;
    JButton checkIn;
    JTextArea workDone;

    String[] bike;

    View v;

    final String CHECKEDIN = "Check Out";
    final String CHECKEDOUT = "Check In";

    public static JLabel LabelMaker(String text, int gridx, int gridy) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Georgia", Font.PLAIN, 14));
        return l;
    }

    public BikeScreen(View view, ActionListener mainListener)
    {
        v = view;
        setLayout(new GridBagLayout());

        home = new JButton("home");
        home.addActionListener(mainListener);
        add(home, v.getConstraint(0, 0, 1, GridBagConstraints.NORTHWEST));

        JLabel l;
        String[] labels = new String[] {
            "Name", "Make", "Model", "Color", "Serial Number", "Key Number", "Date", "New"
        };
        for (int i = 0; i < labels.length; i++) {
            l = new JLabel(labels[i] + ": ");
            l.setFont(new Font("Georgia", Font.PLAIN, 14));
            add(l, v.getConstraint(0, (i+1), 1, GridBagConstraints.EAST));
        }

        name = new JTextField();
        // name.setEditable(false);
        // add(name, v.getConstraint(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        
        make = new JTextField();
        // make.setEditable(false);
        // add(make, v.getConstraint(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));

        model = new JTextField();
        // model.setEditable(false);
        // add(model, v.getConstraint(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));

        color = new JTextField();
        // color.setEditable(false);
        // add(color, v.getConstraint(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));

        serial = new JTextField();
        // serial.setEditable(false);
        // add(serial, v.getConstraint(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        
        key = new JTextField();
        // key.setEditable(false);
        // add(key, v.getConstraint(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));

        date_ = new AnswerPane(AnswerPane.DATEPICKER);
        date_.setEnabled(false);
        add(date_, v.getConstraint(1, 7, 1, GridBagConstraints.WEST));

        new_ = new AnswerPane(AnswerPane.BOOLPICKER);
        new_.setEnabled(false);
        add(new_, v.getConstraint(1, 8, 1, GridBagConstraints.WEST));

        JTextField[] textFields = new JTextField[] {
            name, make, model, color, serial, key
        };
        for (int j = 0; j < textFields.length; j++) {
            textFields[j].setEditable(false);
            add(textFields[j], v.getConstraint(
                1, j+1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL
            ));
        }

        checkIn = new JButton();
        add(checkIn, v.getConstraint(3, 1, 1, GridBagConstraints.CENTER));

        int textHeight = 0; // let GridBagConstraint fill take care of height
        int textWidth = v.getScreenWidth() / 4;
        textWidth -= 20;


        add(new JLabel("Work Done:"), v.getConstraint(2, 2));

        workDone = new JTextArea();

        JScrollPane textContainer;

        workDone.setLineWrap(true);
        workDone.setWrapStyleWord(true);
        workDone.setEditable(false);
        textContainer = new JScrollPane(workDone, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textContainer.setPreferredSize(new Dimension(textWidth, textHeight));
        GridBagConstraints c = v.getConstraint(2, 3, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        c.gridheight = 6;
        add(textContainer, c);
        c.gridheight = 1;

        CardLayout buttonCards = new CardLayout();
        buttonPane = new JPanel(buttonCards);

        edit = new JButton("edit");
        edit.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            buttonCards.show(buttonPane, "save");
            
            for (JTextComponent t : new JTextComponent[] {
                make, model, color, serial, name, key, workDone
            }) {
                t.setEditable(true);
            }
            date_.setEnabled(true);
            new_.setEnabled(true);
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

        add(buttonPane, v.getConstraint(2, 0));
        
        delete = new JButton("delete");
        delete.addActionListener((ActionEvent e) -> {
            v.delete(Integer.parseInt(bike[0]));
            mainListener.actionPerformed(e);
        });
        add(delete, v.getConstraint(3, 0));

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
                make, model, color, serial, name, key})
            {
                t.setEditable(false);
                bike[i++] = deleteExtraWhitespace(t.getText());
            }
            date_.setEnabled(false);
            new_.setEnabled(false);
            bike[i++] = date_.getText();
            bike[i++] = new_.getText();
            // if (checkIn.getText().equals(CHECKEDIN))
            // {
            //     bike[9] = Boolean.toString(true);
            // }
            // else
            // {
            //     bike[9] = Boolean.toString(false);
            // }
            bike[i++] = Boolean.toString(checkIn.getText().equals(CHECKEDIN));
            bike[i++] = workDone.getText();
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
