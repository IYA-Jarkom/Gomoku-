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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Kelas halaman ruangan tempat pengguna bermain
 */
public class RoomPage extends BackgroundPanel {
    // Atribut
    private ImageButton backButton;
    private ImageButton startButton;
    private Label[][] board;
    
    // Konstruktor
    public RoomPage(String roomName) {
       // Background
       super("bg-room.jpg");
       
       // Panel Info
       TransparentPanel infoPanel = new TransparentPanel();
       infoPanel.setLayout(new GridLayout(3,1,0,50));
       
       // Panel Header
       TransparentPanel headerPanel = new TransparentPanel();
       // Button back
       backButton = new ImageButton("button-back.png");
       headerPanel.add(backButton.getButton());
       headerPanel.add(new Label(roomName));
       infoPanel.add(headerPanel);
       
       // Panel Players
       TransparentPanel playersPanel = new TransparentPanel();
       playersPanel.setLayout(new GridLayout(3,1,0,0)); /// Jumlah pemain: 3
       for (int i=0; i<3; i++) { // Looping sejumlah jumlah pemain
            // Panel untuk tiap player
            TransparentPanel playerPanel = new TransparentPanel();
            playerPanel.setLayout(new GridLayout(1,3,20,0));
            // Player's icon
            ImageButton playerIcon = new ImageButton("character-penguin.png");
            playerPanel.add(playerIcon.getButton());
            // Player's name
            playerPanel.add(new Label("Player " + i));
            // Player's key
            ImageButton masterIcon = new ImageButton("key.png");
            playerPanel.add(masterIcon.getButton());
            playersPanel.add(playerPanel);
        }
        infoPanel.add(playersPanel);
       
        // Button start
        startButton = new ImageButton("button-start.png");
        infoPanel.add(startButton.getButton());
       
        // Panel board
        board = new Label[20][20];
        TransparentPanel boardPanel = new TransparentPanel();
        boardPanel.setLayout(new GridLayout(20,20,0,0));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        boardPanel.setPreferredSize(new Dimension(700, 700));
        for (int i=0; i<20; i++){
            for (int j=0; j<20; j++) {
                final Label label = new Label(" ", "green", 30);
                label.setBorder(BorderFactory.createLineBorder(Color.decode("#00B0FF")));
                board[i][j] = label;
                boardPanel.add(label);
            }
        }
       
       // Panel Element
       TransparentPanel elementPanel = new TransparentPanel();
       elementPanel.setLayout(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       c.gridx = 0;
       c.gridy = 0;
       c.weightx = 0.5;
       elementPanel.add(infoPanel, c);
       c = new GridBagConstraints();
       c.gridx = 1;
       c.gridy = 0;
       c.weightx = 0.5;
       c.insets = new Insets(0,150,0,150);
       elementPanel.add(boardPanel, c);
       
       // Finalisasi
       setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
       add(elementPanel, Component.LEFT_ALIGNMENT);
    }
    
    // Getter
    public JButton getBackButton() {
        return backButton.getButton();
    }
    public JButton getStartButton() {
        return startButton.getButton();
    }
    public Label[][] getBoard() {
        return board;
    }
}
