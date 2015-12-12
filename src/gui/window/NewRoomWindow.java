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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Kelas window untuk memasukkan nama room baru
 */
public class NewRoomWindow extends BackgroundPanel {
    // Atribut
    private ColoredTextField roomNameField;
    private ImageButton yesButton;
    private ImageButton noButton;

    // Kosntruktor
    public NewRoomWindow() {
        // Background
        super("bg-new-room.png");
        super.setOpaque(false);

        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2, 1, 100, 20));

        // Panel Room Name
        TransparentPanel roomNamePanel = new TransparentPanel();
        roomNamePanel.setLayout(new FlowLayout());
        // Text Field room name
        roomNameField = new ColoredTextField();
        roomNamePanel.add(roomNameField);

        // Panel decision
        TransparentPanel decisionPanel = new TransparentPanel();
        decisionPanel.setLayout(new FlowLayout());
        // Button no
        noButton = new ImageButton("button-cross.png");
        decisionPanel.add(noButton.getButton());
        // Button yes
        yesButton = new ImageButton("button-check.png");
        decisionPanel.add(yesButton.getButton());

        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(320, 0, 0, 0));
        elementPanel.add(roomNamePanel);
        elementPanel.add(decisionPanel);
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }

    // Getter
    public String getNewRoomName() {
        return roomNameField.getText();
    }

    public JButton getYesButton() {
        return yesButton.getButton();
    }

    public JButton getNoButton() {
        return noButton.getButton();
    }
}
