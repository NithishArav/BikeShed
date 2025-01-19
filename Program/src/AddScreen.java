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
            "What is the title of the paper?",
            "Who is the author? Please use Last name,First Name format",
            "What is the main area of the paper? \nIf there is a subfield, please separate each subfield using a comma",
            "What date was it published? Please use YYYY-MM-DD form",
            "What journal is the paper a part of?",
            "On a scale of 1-10, how important is the paper to your research, 10 being the most important?",
            "What is a citation for the paper?",
            "Please write a summary for the paper",
            "Please write about the limitations of the paper regarding your research",
            "Please write about the importance of the paper (last question)"
        };

        v = view;
        setLayout(new GridBagLayout());

        answers = new String[Paper.NUM_PARAMS];
        qNum = 0;

        homeButton = new JButton("cancel");
        homeButton.addActionListener(mainListener);
        add(homeButton, v.getConstraint(0, 0, 1, GridBagConstraints.BASELINE_LEADING));

        questionPane = new JTextArea(3, 100);
        questionPane.setEditable(false);
        add(questionPane, v.getConstraint(0, 1, 2));

        answerPane = new JTextArea(3, 100);
        answerPane.setEditable(true);
        add(answerPane, v.getConstraint(0, 2, 2));

        nextButton = new JButton("next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (updateAnswers())
                {
                    qNum++;
                    questionPane.setText(questions[qNum]);
                    answerPane.setText("");
                    answerPane.setCaretPosition(0);
                    if (qNum+1 == 10)
                    {
                        nextButton.setEnabled(false);
                        submit.setEnabled(true);
                    }
                    backButton.setEnabled(true);
                }
            }
        });
        add(nextButton, v.getConstraint(1, 3));

        backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
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
            }
        });
        add(backButton, v.getConstraint(0, 3));

        submit = new JButton("submit");
        submit.setEnabled(false);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                answers[qNum+1] = answerPane.getText();
                v.add(answers);
                mainListener.actionPerformed(e);
            }
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
        if (qNum == 1)
        {
            if (input.split(",").length != 2)
            {
                JOptionPane.showMessageDialog(v.frame, "Invalid format for author\nPlease separate first and last name with a comma");
                return false;
            }
            input = deleteCommaWhitespace(input);
        }
        else if (qNum == 2)
        {
            input = "[" + input + "]";
        }
        else if (qNum == 3)
        {
            try
            {
                LocalDate.parse(input);
            }
            catch (DateTimeParseException e)
            {
                JOptionPane.showMessageDialog(v.frame, "Invalid date format\nPlease use YYYY-MM-DD");
                return false;
            }
        }
        else if (qNum == 5)
        {
            if (!(input.matches("[1-9]") || input.equals("10")))
            {
                JOptionPane.showMessageDialog(v.frame, "Invalid format for rating\nPlease use an integer between 1 and 10");
                return false;
            }
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
