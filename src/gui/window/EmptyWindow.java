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
import java.awt.Dimension;
import java.awt.GridLayout;
import javafx.scene.paint.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Kelas window untuk menampilkan suatu pesan
 */
public class EmptyWindow extends BackgroundPanel {
    // Atribut
    private String message;
    private ImageButton yesButton;
    
    // Konstruktor
    public EmptyWindow() {
        // Background
        super("bg-empty.png");
        super.setOpaque(false);
       
        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2,1,0,20));
        elementPanel.setBorder(BorderFactory.createLineBorder(java.awt.Color.white));
        
        // Label nama pemenang
        message = "Something bad happened!";
        Label winnerNameLabel = new Label(message, "white", 35);
        elementPanel.add(winnerNameLabel);
        // Button yes
        yesButton = new ImageButton("button-check.png");
        elementPanel.add(yesButton.getButton());
       
        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(300,0,0,0));
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
    
    // Konstruktor dengan pesan sesuai dengan masukkan pengguna
    public EmptyWindow(String message) {
        // Background
        super("bg-empty.png");
        super.setOpaque(false);
       
        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2,1,0,20));
        
        // Label nama pemenang
        Label winnerNameLabel = new Label(message, "white", 35);
        elementPanel.add(winnerNameLabel);
        // Button yes
        yesButton = new ImageButton("button-check.png");
        elementPanel.add(yesButton.getButton());
       
        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(300,0,0,0));
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
    
    // Getter
    public JButton getYesButton() {
        return yesButton.getButton();
    }
}
