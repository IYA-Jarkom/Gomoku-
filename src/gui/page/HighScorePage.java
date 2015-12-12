/*
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * - RETURN OF POI -
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
package gui.page;

import gui.element.BackgroundPanel;
import gui.element.ImageButton;
import gui.element.TransparentPanel;
import gui.element.Label;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

/**
 * Kelas halaman high scores dimana seluruh high scores pengguna ditampilkan
 */
public class HighScorePage extends BackgroundPanel{
    // Atribut
    private ImageButton backButton;

    // Konstruktor
    public HighScorePage(Map highScores) {
        super("bg-high-scores.jpg");
        // Panel back
        backButton = new ImageButton("button-back.png");
        backButton.getButton().setHorizontalAlignment(SwingConstants.LEFT);

        // Panel high scores
        TransparentPanel highScorePanel = new TransparentPanel();
        highScorePanel.setLayout(new GridLayout(highScores.size(), 2, 20, 0));
        highScorePanel.setBorder(new EmptyBorder(90,200,30,200));
        for (Object key : highScores.keySet()) {
            Label playerName = new Label ((String) key, "green", 30);
            playerName.setHorizontalAlignment(SwingConstants.LEFT);
            playerName.setBorder(new EmptyBorder(0,100,0,0));
            highScorePanel.add(playerName);

            Label playerScore = new Label(highScores.get(key) + " points", "green", 30);
            highScorePanel.add(playerScore);
        }

        //Finalisasi
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setPreferredSize(new Dimension(1330,700));
        elementPanel.setLayout(new BorderLayout());
        elementPanel.add(backButton.getButton(), BorderLayout.PAGE_START);
        elementPanel.add(highScorePanel, BorderLayout.CENTER);

        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }

    // Getter
    public JButton getBackButton() {
        return backButton.getButton();
    }
}
