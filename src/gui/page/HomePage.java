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
import gui.element.ColoredTextField;
import gui.element.ImageButton;
import gui.element.TransparentPanel;
import java.awt.FlowLayout;

/**
 * Kelas halaman home dimana pengguna akan memasukkan nickname
 */
public class HomePage extends BackgroundPanel {
    // Konstruktor
    public HomePage() {
        // Panel nickname
        TransparentPanel nicknamePanel = new TransparentPanel();
        nicknamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 500));
        
        // Name TextField
        ColoredTextField nameField = new ColoredTextField();
        nicknamePanel.add(nameField);
        // Button Check
        ImageButton imageButton = new ImageButton("button-check.png");
        nicknamePanel.add(imageButton.getButton());
        
        // Finalisasi
        add(nicknamePanel);
    }
}
