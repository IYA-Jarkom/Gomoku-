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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Kelas halaman daftar ruangan
 */
public class RoomsListPanel extends JPanel {
    // Atribut
    private JPanel roomListPanel;
    
    // Konstruktor
    public RoomsListPanel() {
        roomListPanel = new JPanel();
        // Background
        setBackground(Color.decode("#16495A"));
        setOpaque(true);

        // Panel Room List
        roomListPanel.setLayout(new GridLayout(2,2,0,20)); // Ganti dengan ukuran room, baris: 2
        for (int i=0; i<2; i++) {
            String roomName = "Room " + i;
            roomListPanel.add(new Label(roomName));
            String roomMember = i + "people";
            roomListPanel.add(new Label(roomMember));
        }
        
        // Finalisasi
        // Panel element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(null);
        elementPanel.add(roomListPanel);
        roomListPanel.setLocation(0,100);
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
    
}