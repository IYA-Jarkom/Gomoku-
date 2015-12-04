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
import gui.element.ColoredTextField;
import gui.element.ImageButton;
import gui.element.TransparentPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 * Kelas window untuk memasukkan host name
 */
public class HostNameWindow extends BackgroundPanel {
    // Atribut
    private ColoredTextField hostNameField;
    private ImageButton yesButton;
    
    // Kosntruktor
    public HostNameWindow() {
        // Background
        super("bg-host-name.png");
        super.setOpaque(false);
       
        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2,1,100,20));
        
        // Panel Host Name
        TransparentPanel hostNamePanel = new TransparentPanel();
        hostNamePanel.setLayout(new FlowLayout());
        // Text Field room name
        hostNameField = new ColoredTextField();
        hostNamePanel.add(hostNameField);
        
        // Panel decision
        TransparentPanel decisionPanel = new TransparentPanel();
        decisionPanel.setLayout(new FlowLayout());
        // Button yes
        yesButton = new ImageButton("button-check.png");
        decisionPanel.add(yesButton.getButton());
       
        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(320,0,0,0));
        elementPanel.add(hostNamePanel);
        elementPanel.add(decisionPanel);
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
    
    // Getter
    public String getHostName() {
        return hostNameField.getText();
    }
    public JButton getYesButton() {
        return yesButton.getButton();
    }
}
