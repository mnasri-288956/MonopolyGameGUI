import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Monopoly Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 500);

            JTextArea log = new JTextArea();
            log.setEditable(false);
            JScrollPane scroll = new JScrollPane(log);

            JButton rollBtn = new JButton("Roll Dice");
            JButton buyBtn = new JButton("Buy Property");
            JButton saveBtn = new JButton("Save Game");
            JButton loadBtn = new JButton("Load Game");
            JButton newBtn  = new JButton("New Game");
            JButton chanceBtn = new JButton("Chance");
            JButton chestBtn  = new JButton("Community Chest");
            JButton houseBtn = new JButton("Build House/Hotel");

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setPreferredSize(new Dimension(650, 40));
            panel.add(newBtn);
            panel.add(rollBtn);
            panel.add(buyBtn);
            panel.add(saveBtn);
            panel.add(loadBtn);
            panel.add(chanceBtn);
            panel.add(chestBtn);
            panel.add(houseBtn);

            frame.setLayout(new BorderLayout());
            frame.add(scroll, BorderLayout.CENTER);
            frame.add(panel, BorderLayout.SOUTH);

            MonopolyGame game = new MonopolyGame();

            newBtn.addActionListener(e -> {
                game.resetForGUI();
                log.setText("New game started. $" + game.getMoney() +
                        ", pos " + game.getPosition() + "\n");
            });

            rollBtn.addActionListener(e -> {
                int roll = game.guiRoll();
                log.append("Rolled a " + roll + "\n");
            });

            buyBtn.addActionListener(e -> {
                String msg = game.guiBuyProperty();
                log.append(msg + "\n");
            });

            saveBtn.addActionListener(e -> {
                try {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        game.saveToFile(fc.getSelectedFile());
                        log.append("Game saved.\n");
                    }
                } catch (Exception ex) {
                    log.append("Save failed: " + ex.getMessage() + "\n");
                }
            });

            loadBtn.addActionListener(e -> {
                try {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        game.loadFromFile(fc.getSelectedFile());
                        log.append("Game loaded. $" + game.getMoney() +
                                ", pos " + game.getPosition() + "\n");
                    }
                } catch (Exception ex) {
                    log.append("Load failed: " + ex.getMessage() + "\n");
                }
            });

            chanceBtn.addActionListener(e -> {
                String card = game.getChanceCard();
                log.append("[Chance] " + card + "\n");
            });

            chestBtn.addActionListener(e -> {
                String card = game.getCommunityCard();
                log.append("[Community Chest] " + card + "\n");
            });

            houseBtn.addActionListener(e -> {
                // simple demo logic
                log.append("Built house/hotel on your property!\n");
            });

            frame.setVisible(true);
        });
    }
}