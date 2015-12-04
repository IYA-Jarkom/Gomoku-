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
import gui.element.ColoredTextField;
import gui.element.ImageButton;
import gui.element.Label;
import gui.element.TransparentPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

/**
 * Kelas window untuk menampilkan pemenang yang bukan player sendiri
 */
public class OtherWinWindow extends BackgroundPanel {
    // Konstruktor
    public OtherWinWindow() {
        // Background
        super("bg-other-win.png");
        super.setOpaque(false);
       
        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2,1,0,50));
        
        // Label nama pemenang
        Label winnerNameLabel = new Label("Nickname2", "white", 47);
        elementPanel.add(winnerNameLabel);
        // Button yes
        ImageButton yesButton = new ImageButton("button-check.png");
        elementPanel.add(yesButton.getButton());
       
        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(320,0,0,0));
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
}
