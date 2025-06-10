import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorfulQuizGUI {
    private int currentQuestion = 0;
    private int score = 0;
    private final String[] questions = {
        "What is the capital of India?", "Who is the father of geometry?", "Who are you?", " what is the rooot of 256?","what is the formula of heat?"
    };
    private final String[][] options = {
        {"Delhi", "BBSR", "Paris"},
        {"Euclid", "Newton", "Einstein"},
        {"Coder", "Student", "Teacher"},
        { "16", "14", "12"},
        {"Q=mc^2", "Q=mv^2", "Q=ma"}
    };
    private final int[] answers = {0, 2, 0, 0, 0}; 

    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JButton nextButton;

    public ColorfulQuizGUI() {
        frame = new JFrame("Colorful Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLayout(new BorderLayout());

        // Main panel with background color
        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 50, 150)); // Dark blue background
        panel.setLayout(new GridLayout(5, 1));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(questionLabel);

        optionGroup = new ButtonGroup();
        optionButtons = new JRadioButton[3];

        for (int i = 0; i < 3; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBackground(new Color(100, 100, 200)); // Light blue
            optionButtons[i].setForeground(Color.WHITE);
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionGroup.add(optionButtons[i]);
            panel.add(optionButtons[i]);
        }

        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(0, 180, 80)); // Green button
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        panel.add(nextButton);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText(questions[currentQuestion]);
            for (int i = 0; i < options[currentQuestion].length; i++) {
                optionButtons[i].setText(options[currentQuestion][i]);
                optionButtons[i].setSelected(false);
            }
        } else {
            showResult();
        }
    }

    private void checkAnswer() {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected() && i == answers[currentQuestion]) {
                score++;
            }
        }
        currentQuestion++;
        loadQuestion();
    }

    private void showResult() {
        JOptionPane.showMessageDialog(frame, "Quiz Over! Your score: " + score);
        frame.dispose();
    }

    public static void main(String[] args) {
        new ColorfulQuizGUI();
    }
}