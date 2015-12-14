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
import gui.element.Label;
import gui.element.TransparentPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

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
    private ArrayList<Label> labels; // Daftar label nama ruangan

    // Konstruktor
    public MenuPage(String nickname, Map roomList) {
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
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        menuPanel.setPreferredSize(new Dimension(360,570));
        // Foto profile
        ImageButton profilePicture = new ImageButton("profile.png");
        menuPanel.add(profilePicture.getButton());
        // Label nickname
        nicknameLabel = new Label(nickname);
        menuPanel.add(nicknameLabel);
        // Tombol menu
        highScoreButton = new ImageButton("button-high-scores.png");
        menuPanel.add(highScoreButton.getButton());

        // Panel Room List
        roomListPanel = new JPanel();
        roomListPanel.setBackground(Color.decode("#16495A"));
//        roomListPanel.setOpaque(true);
        roomListPanel.setLayout(new GridLayout(roomList.size(), 2, 0, 20));

        labels = new ArrayList<>();
        for (Object key : roomList.keySet()) {
            String roomName = (String) key;
            Label roomNameLabel = new Label(roomName);
            roomNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            roomNameLabel.setBorder(new EmptyBorder(0,70,0,0));
            labels.add(roomNameLabel);
            roomListPanel.add(roomNameLabel);

            // Label jumlah member
            String roomMember;
            if ((int) roomList.get(key) > 1) {
                roomMember = roomList.get(key) + " people";
            } else {
                roomMember = roomList.get(key) + " person";
            }
            roomListPanel.add(new Label(roomMember));
        }

        // Finalisasi
        // Panel element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new BorderLayout(17,35));
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

    public ArrayList<Label> getRooms() {
        return labels;
    }
}
