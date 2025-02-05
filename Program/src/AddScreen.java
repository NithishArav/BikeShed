import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import javax.swing.*;


/**
 * Component class
 * Referenced in View.java
 */
public class AddScreen extends JPanel implements Screen
{
    JButton homeButton;
    JTextArea questionPane;
    AnswerPane answerPane;
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
            "What work has been done on the bicycle?"
        };

        v = view;
        setLayout(new GridBagLayout());

        answers = new String[Bike.NUM_PARAMS-2];
        qNum = 0;

        homeButton = new JButton("cancel");
        homeButton.setFont(new Font("Georgia", Font.PLAIN, 16));
        homeButton.addActionListener(mainListener);
        add(homeButton, v.getConstraint(0, 0, 1, GridBagConstraints.NORTHWEST));

        questionPane = new JTextArea(3, 100);
        questionPane.setFont(new Font("Georgia", Font.PLAIN, 24));
        questionPane.setColumns(50);
        questionPane.setEditable(false);
        add(questionPane, v.getConstraint(0, 1, 2));

        answerPane = new AnswerPane(AnswerPane.TEXTAREA);
        ((JTextArea)(answerPane.get())).setRows(3);
        ((JTextArea)(answerPane.get())).setColumns(100);
        add(answerPane, v.getConstraint(0, 2, 2));

        nextButton = new JButton("next");
        nextButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            System.out.println(Arrays.toString(answers));
            if (updateAnswers())
            {
                qNum++;
                if (qNum==6) {
                    answerPane.switchType(AnswerPane.DATEPICKER);
                }
                questionPane.setText(questions[qNum]);
                answerPane.setText(answers[qNum]);
                answerPane.requestFocusInWindow();

                if (qNum+1 == Bike.NUM_PARAMS-3)
                {
                    nextButton.setEnabled(false);
                    submit.setEnabled(true);
                }
                backButton.setEnabled(true);
            }
        });
        add(nextButton, v.getConstraint(1, 3));

        backButton = new JButton("back");
        backButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            if (updateAnswers())
            {
                qNum--;
                questionPane.setText(questions[qNum]);
                answerPane.setText(answers[qNum]);
                answerPane.requestFocusInWindow();
                if (qNum-1 == 0)
                {
                    backButton.setEnabled(false);
                }
                if (qNum==6)
                {
                    answerPane.switchType(AnswerPane.DATEPICKER);
                }
            }
            nextButton.setEnabled(true);
            submit.setEnabled(false);
            if (qNum == 0) {
                backButton.setEnabled(false);
            }
        });
        add(backButton, v.getConstraint(0, 3));

        submit = new JButton("submit");
        submit.setEnabled(false);
        submit.addActionListener((ActionEvent e) -> {
            answers[qNum+1] = answerPane.getText();
            int n =  Bike.NUM_PARAMS;
            String[] newBike = new String[n];
            for (int i = 0; i < Bike.NUM_PARAMS - 3; i++)
            {
                newBike[i] = answers[i];
            }
            newBike[n-3] = Boolean.toString(true);
            newBike[n-2] = Boolean.toString(true);
            newBike[n-1] = answers[n-3];
            v.add(newBike);
            mainListener.actionPerformed(e);
        });
        add(submit, v.getConstraint(1, 4));
    }

    public void active()
    {
        qNum = 0;
        questionPane.setText(questions[qNum]);
        answerPane.setText("");
        nextButton.setEnabled(true);
        backButton.setEnabled(false);
        submit.setEnabled(false);
        answers[0] = Integer.toString(v.getSize());
        EventQueue.invokeLater( () -> answerPane.requestFocusInWindow());
    }

    public boolean updateAnswers()
    {
        if (qNum == 6)
        {
            try {
                answerPane.getText();
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(v.frame, "Year must be valid 4 digit number");
            }
        }
        String input = answerPane.getText();
        input = input.trim();
        switch (qNum) {
            case 2 -> input = input.toLowerCase();
            // case 3, 5 -> {
            //     if (!(input.matches("\\d+")))
            //     {
            //         JOptionPane.showMessageDialog(v.frame, "Invalid number format, please use only digits");
            //         return false;
            //     }
            // }
            case 6 -> {
                answerPane.switchType(AnswerPane.TEXTAREA);
            }
            default -> {}
        }
        System.out.println(input);
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
