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

/**
 * Kelas halaman menu
 */
public class MenuPage extends BackgroundPanel {
   // Konstruktor
   public MenuPage() {
       // Background
       super("bg-menu.jpg");
       
       // Panel button
       TransparentPanel buttonPanel = new TransparentPanel();
       buttonPanel.setLayout(new GridLayout(1, 2, 1100, 0));

       // Button back
       ImageButton backButton = new ImageButton("button-back.png");
       buttonPanel.add(backButton.getButton());
       // Button new room
       ImageButton newRoomButton = new ImageButton("button-plus.png");
       buttonPanel.add(newRoomButton.getButton());
       
       // Finalisasi
       add(buttonPanel);
   }
}
