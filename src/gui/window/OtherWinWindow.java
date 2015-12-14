/*
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * - RETURN OF POI -
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
package gui.window;

import gui.element.BackgroundPanel;
import gui.element.ImageButton;
import gui.element.Label;
import gui.element.TransparentPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Kelas window untuk menampilkan pemenang yang bukan player sendiri
 */
public class OtherWinWindow extends BackgroundPanel {
    // Atribut
    private ImageButton yesButton;

    // Konstruktor
    public OtherWinWindow(String winnerNickname) {
        // Background
        super("bg-other-win.png");
        super.setOpaque(false);

        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2, 1, 0, 50));

        // Label nama pemenang
        Label winnerNameLabel = new Label(winnerNickname, "white", 47);
        elementPanel.add(winnerNameLabel);
        // Button yes
        yesButton = new ImageButton("button-check.png");
        elementPanel.add(yesButton.getButton());

        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(320, 0, 0, 0));
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }

    // Getter
    public JButton getYesButton() {
        return yesButton.getButton();
    }
}
