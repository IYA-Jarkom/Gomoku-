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

import gui.element.*;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

/**
 * Kelas window untuk memilih role pengguna sebagai player atau spectator
 */
public class RoleWindow extends BackgroundPanel {
    // Konstruktor
    public RoleWindow() {
        // Background
        super("bg-role.png");
        super.setOpaque(false);
       
        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2,1,0,40));
        
        // Panel Role
        TransparentPanel rolePanel = new TransparentPanel();
        rolePanel.setLayout(new FlowLayout());
        // Button player
        ImageButton playerButton = new ImageButton("button-player.png");
        rolePanel.add(playerButton.getButton());
        // Button spectator
        ImageButton spectatorButton = new ImageButton("button-spectator.png");
        rolePanel.add(spectatorButton.getButton());
        
        // Panel decision
        TransparentPanel decisionPanel = new TransparentPanel();
        decisionPanel.setLayout(new FlowLayout());
        // Button no
        ImageButton noButton = new ImageButton("button-cross.png");
        decisionPanel.add(noButton.getButton());
        // Button yes
        ImageButton yesButton = new ImageButton("button-check.png");
        decisionPanel.add(yesButton.getButton());
       
        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(320,0,0,0));
        elementPanel.add(rolePanel);
        elementPanel.add(decisionPanel);
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
}