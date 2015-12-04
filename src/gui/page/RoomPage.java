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
import javax.swing.JButton;

/**
 * Kelas halaman ruangan tempat pengguna bermain
 */
public class RoomPage extends BackgroundPanel {
    // Atribut
    private ImageButton backButton;
    // Konstruktor
    public RoomPage() {
       // Background
       super("bg-room.jpg");
       
       // Panel Element
       TransparentPanel elementPanel = new TransparentPanel();
       
       // Button back
       backButton = new ImageButton("button-back.png");
       elementPanel.add(backButton.getButton());
       
       // Finalisasi
       setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
       add(elementPanel);
    }
    
    // Getter
    public JButton getBackButton() {
        return backButton.getButton();
    }
}
