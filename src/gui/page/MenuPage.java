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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Kelas halaman menu
 */
public class MenuPage extends BackgroundPanel {
    // Atribut
    private ImageButton backButton;
    private ImageButton newRoomButton;
    private Label nicknameLabel;
    private ImageButton highScoreButton;
    private JPanel roomListPanel;
    
    // Konstruktor
    public MenuPage(String nickname) {
        // Background
        super("bg-menu.jpg");
        
        // Panel button
        TransparentPanel buttonPanel = new TransparentPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 1100, 0));
        buttonPanel.setBorder(new EmptyBorder(0,0,35,0));
        // Button back
        backButton = new ImageButton("button-back.png");
        buttonPanel.add(backButton.getButton());
        // Button new room
        newRoomButton = new ImageButton("button-plus.png");
        buttonPanel.add(newRoomButton.getButton());
        
        // Panel nickname dan menu
        TransparentPanel menuPanel = new TransparentPanel();
        menuPanel.setLayout(new GridLayout(2,1,0,30));
        // Label nickname
        nicknameLabel = new Label(nickname);
        menuPanel.add(nicknameLabel);
        // Tombol menu
        highScoreButton = new ImageButton("button-highscore.png");
        menuPanel.add(highScoreButton.getButton());

        // Panel Room List
        roomListPanel = new JPanel();
        roomListPanel.setBackground(Color.decode("#16495A"));
        roomListPanel.setOpaque(true);
        roomListPanel.setBorder(new EmptyBorder(20,0,0,20));
        roomListPanel.setLayout(new GridLayout(5,2,0,50)); // Ganti dengan ukuran room, baris: 5
        for (int i=0; i<5; i++) {
            String roomName = "Room " + i;
            roomListPanel.add(new Label(roomName));
            String roomMember = i + " people";
            roomListPanel.add(new Label(roomMember));
        }
        
        // Finalisasi
        // Panel element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new BorderLayout(0,0));
        elementPanel.add(buttonPanel, BorderLayout.PAGE_START);
        elementPanel.add(menuPanel, BorderLayout.LINE_START);
        elementPanel.add(roomListPanel, BorderLayout.CENTER);
        
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
    
    // Getter
    public JButton getBackButton() {
        return backButton.getButton();
    }
    public JButton getNewRoomButton() {
        return newRoomButton.getButton();
    }
    public JPanel getRoomListPanel() {
        return roomListPanel;
    }
    public JButton getHighScoreButton() {
        return highScoreButton.getButton();
    }
}
