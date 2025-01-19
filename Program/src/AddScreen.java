import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;


/**
 * Component class
 * Referenced in View.java
 */
public class AddScreen extends JPanel implements Screen
{
    JButton homeButton;
    JTextArea questionPane;
    JTextArea answerPane;
    JButton nextButton;
    JButton backButton;
    JButton submit;

    int qNum;

    String[] questions;
    String[] answers;

    View v;

    public AddScreen(View view, ActionListener mainListener)
    {
        questions = new String[] {
            "What is the make of the bicycle?",
            "What is the model of the bicycle",
            "What color is the bicycle?",
            "What is the serial number of the bicycle?",
            "What is the first name of the owner?",
            "What is the key number of the bicycle?",
            "What is the date the bicycle was acquired or last worked on?",
            "Is the bicycle new? Please enter either true or false",
            "What work has been done on the bicycle?"
        };

        v = view;
        setLayout(new GridBagLayout());

        answers = new String[Bike.NUM_PARAMS];
        qNum = 0;

        homeButton = new JButton("cancel");
        homeButton.addActionListener(mainListener);
        add(homeButton, v.getConstraint(0, 0));

        questionPane = new JTextArea(3, 100);
        questionPane.setEditable(false);
        add(questionPane, v.getConstraint(0, 1, 2));

        answerPane = new JTextArea(3, 100);
        answerPane.setEditable(true);
        add(answerPane, v.getConstraint(0, 2, 2));

        nextButton = new JButton("next");
        nextButton.addActionListener((ActionEvent e) -> {
            if (updateAnswers())
            {
                qNum++;
                questionPane.setText(questions[qNum]);
                answerPane.setText("");
                answerPane.setCaretPosition(0);
                if (qNum+1 == 9)
                {
                    nextButton.setEnabled(false);
                    submit.setEnabled(true);
                }
                backButton.setEnabled(true);
            }
        });
        add(nextButton, v.getConstraint(1, 3));

        backButton = new JButton("back");
        backButton.addActionListener((ActionEvent e) -> {
            if (updateAnswers())
            {
                qNum--;
                questionPane.setText(questions[qNum]);
                answerPane.setText("");
                answerPane.setCaretPosition(0);
                if (qNum-1 == 0)
                {
                    backButton.setEnabled(false);
                }
            }
            nextButton.setEnabled(true);
            submit.setEnabled(false);
        });
        add(backButton, v.getConstraint(0, 3));

        submit = new JButton("submit");
        submit.setEnabled(false);
        submit.addActionListener((ActionEvent e) -> {
            answers[qNum+1] = answerPane.getText();
            v.add(answers);
            mainListener.actionPerformed(e);
        });
        add(submit, v.getConstraint(0, 4));
    }

    public void active()
    {
        qNum = 0;
        questionPane.setText(questions[qNum]);
        backButton.setEnabled(false);
        submit.setEnabled(false);
        answers[0] = Integer.toString(v.getSize());
        answerPane.setCaretPosition(0);
    }

    public boolean updateAnswers()
    {
        String input = answerPane.getText();
        input = input.trim();
        switch (qNum) {
            case 6:
                try
                {
                    LocalDate.parse(input);
                }
                catch (DateTimeParseException e)
                {
                    JOptionPane.showMessageDialog(v.frame, "Invalid date format\nPlease use YYYY-MM-DD");
                    return false;
                }   break;
            case 7:
                input = input.toLowerCase();
                if (!(input.equals("true") || input.equals("false")))
                {
                    JOptionPane.showMessageDialog(v.frame, "Invalid boolean format, please type either true or false");
                    return false;
                }   break;
            case 9:
                qNum++;
                answers[qNum] = "true";
                break;
            default:
                break;
        }
        answers[qNum+1] = input;
        return true;
    }

    public static String deleteCommaWhitespace(String input)
    {
        String out = input;
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
