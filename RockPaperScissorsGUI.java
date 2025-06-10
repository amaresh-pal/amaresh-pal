import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class RockPaperScissorsGUI extends JFrame implements ActionListener {
    private JButton rockButton, paperButton, scissorsButton, playAgainButton, exitButton;
    private JLabel titleLabel, userChoiceLabel, computerChoiceLabel, resultLabel, scoreLabel;
    private JPanel mainPanel, buttonPanel, choicePanel, resultPanel, scorePanel;
    private Random random;
    private int userWins = 0, computerWins = 0, ties = 0;
    private String[] choices = {"Rock", "Paper", "Scissors"};
    private String[] emojis = {"🪨", "📄", "✂️"};

    public RockPaperScissorsGUI() {
        random = new Random();
        initializeGUI();
    }

    private void initializeGUI() {
        // Set up the main frame
        setTitle("Rock Paper Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Title
        titleLabel = new JLabel("Rock Paper Scissors", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Button panel for game choices
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        rockButton = createGameButton("🪨 Rock", Color.decode("#FF6B6B"));
        paperButton = createGameButton("📄 Paper", Color.decode("#4ECDC4"));
        scissorsButton = createGameButton("✂️ Scissors", Color.decode("#45B7D1"));
        
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);

        // Choice display panel
        choicePanel = new JPanel(new GridLayout(1, 2, 20, 10));
        choicePanel.setBackground(new Color(240, 248, 255));
        choicePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Choices", 
            0, 0, new Font("Arial", Font.BOLD, 14)));

        userChoiceLabel = new JLabel("Your choice: -", SwingConstants.CENTER);
        computerChoiceLabel = new JLabel("Computer choice: -", SwingConstants.CENTER);
        
        userChoiceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        computerChoiceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        choicePanel.add(userChoiceLabel);
        choicePanel.add(computerChoiceLabel);

        // Result panel
        resultPanel = new JPanel(new FlowLayout());
        resultPanel.setBackground(new Color(240, 248, 255));
        
        resultLabel = new JLabel("Make your choice to start!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(new Color(25, 25, 112));
        resultPanel.add(resultLabel);

        // Score panel
        scorePanel = new JPanel(new FlowLayout());
        scorePanel.setBackground(new Color(240, 248, 255));
        
        scoreLabel = new JLabel("Score - You: 0 | Computer: 0 | Ties: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(105, 105, 105));
        scorePanel.add(scoreLabel);

        // Control buttons
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(new Color(240, 248, 255));
        
        playAgainButton = new JButton("New Game");
        playAgainButton.setFont(new Font("Arial", Font.PLAIN, 14));
        playAgainButton.setBackground(new Color(50, 205, 50));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setFocusPainted(false);
        playAgainButton.addActionListener(this);
        
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setBackground(new Color(220, 20, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(this);
        
        controlPanel.add(playAgainButton);
        controlPanel.add(exitButton);

        // Combine center panels
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(buttonPanel);
        centerPanel.add(choicePanel);
        centerPanel.add(resultPanel);
        centerPanel.add(scorePanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createGameButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(120, 50));
        button.addActionListener(this);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to exit?", "Exit Game", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (e.getSource() == playAgainButton) {
            resetGame();
        } else {
            // Game button pressed
            String userChoice = "";
            if (e.getSource() == rockButton) {
                userChoice = "Rock";
            } else if (e.getSource() == paperButton) {
                userChoice = "Paper";
            } else if (e.getSource() == scissorsButton) {
                userChoice = "Scissors";
            }
            
            playGame(userChoice);
        }
    }

    private void playGame(String userChoice) {
        // Generate computer choice
        int computerIndex = random.nextInt(3);
        String computerChoice = choices[computerIndex];
        
        // Update choice labels
        userChoiceLabel.setText("Your choice: " + emojis[getChoiceIndex(userChoice)] + " " + userChoice);
        computerChoiceLabel.setText("Computer choice: " + emojis[computerIndex] + " " + computerChoice);
        
        // Determine winner
        String result;
        Color resultColor;
        
        if (userChoice.equals(computerChoice)) {
            result = "It's a tie! 🤝";
            resultColor = new Color(255, 165, 0); // Orange
            ties++;
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                   (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                   (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            result = "You win! 🎉";
            resultColor = new Color(34, 139, 34); // Forest Green
            userWins++;
        } else {
            result = "Computer wins! 🤖";
            resultColor = new Color(220, 20, 60); // Crimson
            computerWins++;
        }
        
        resultLabel.setText(result);
        resultLabel.setForeground(resultColor);
        
        // Update score
        scoreLabel.setText("Score - You: " + userWins + " | Computer: " + computerWins + " | Ties: " + ties);
        
        // Add some visual feedback
        Timer timer = new Timer(100, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count % 2 == 0) {
                    resultLabel.setVisible(false);
                } else {
                    resultLabel.setVisible(true);
                }
                count++;
                if (count > 3) {
                    ((Timer) e.getSource()).stop();
                    resultLabel.setVisible(true);
                }
            }
        });
        timer.start();
    }

    private int getChoiceIndex(String choice) {
        for (int i = 0; i < choices.length; i++) {
            if (choices[i].equals(choice)) {
                return i;
            }
        }
        return 0;
    }

    private void resetGame() {
        userWins = 0;
        computerWins = 0;
        ties = 0;
        
        userChoiceLabel.setText("Your choice: -");
        computerChoiceLabel.setText("Computer choice: -");
        resultLabel.setText("Make your choice to start!");
        resultLabel.setForeground(new Color(25, 25, 112));
        scoreLabel.setText("Score - You: 0 | Computer: 0 | Ties: 0");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RockPaperScissorsGUI());
    }
}