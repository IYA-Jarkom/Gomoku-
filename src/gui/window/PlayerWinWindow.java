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
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 * Kelas window untuk menampilkan pemenang
 */
public class PlayerWinWindow extends BackgroundPanel {
    // Atribut
    private ImageButton yesButton;
    
    // Konstruktor
    public PlayerWinWindow() {
        // Background
        super("bg-player-win.png");
        super.setOpaque(false);
       
        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        
        // Button yes
        yesButton = new ImageButton("button-check.png");
        elementPanel.add(yesButton.getButton());
       
        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(400,0,0,0));
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
    
    // Getter
    public JButton getYesButton() {
        return yesButton.getButton();
    }
}
