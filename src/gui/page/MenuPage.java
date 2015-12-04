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

import gui.element.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;

/**
 * Kelas halaman menu
 */
public class MenuPage extends BackgroundPanel {
    // Atribut
    private ImageButton backButton;
    private ImageButton newRoomButton;
    private Label nicknameLabel;
    private ImageButton menuButton;
    
    // Konstruktor
    public MenuPage(String nickname) {
        // Background
        super("bg-menu.jpg");
        
        // Panel button
        TransparentPanel buttonPanel = new TransparentPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 1100, 0));
        // Button back
        backButton = new ImageButton("button-back.png");
        buttonPanel.add(backButton.getButton());
        // Button new room
        newRoomButton = new ImageButton("button-plus.png");
        buttonPanel.add(newRoomButton.getButton());
        
        // Panel nickname dan menu
        TransparentPanel menuPanel = new TransparentPanel();
        menuPanel.setLayout(new GridLayout(2,1,0,20));
        // Label nickname
        nicknameLabel = new Label(nickname);
        menuPanel.add(nicknameLabel);
        // Tombol menu
        menuButton = new ImageButton("button-highscore.png");
        menuPanel.add(menuButton.getButton());

        // Finalisasi
        // Panel element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(null);
        elementPanel.add(menuPanel);
        elementPanel.add(buttonPanel);
        
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(buttonPanel);
    }
    
    // Getter
    public JButton getBackButton() {
        return backButton.getButton();
    }
    public JButton getNewRoomButton() {
        return newRoomButton.getButton();
    }
}
