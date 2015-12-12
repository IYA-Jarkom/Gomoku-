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

import javax.swing.*;
import java.awt.*;

/**
 * Kelas halaman home dimana pengguna akan memasukkan nickname
 */
public class HomePage extends BackgroundPanel {
    // Atribut
    private ColoredTextField nameField;
    private ImageButton yesButton;

    // Konstruktor
    public HomePage() {
        // Panel nickname
        TransparentPanel nicknamePanel = new TransparentPanel();
        nicknamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 500));

        // Name TextField
        nameField = new ColoredTextField();
        nicknamePanel.add(nameField);
        // Button Check
        yesButton = new ImageButton("button-check.png");
        nicknamePanel.add(yesButton.getButton());

        // Finalisasi
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(nicknamePanel);
    }

    // Getter
    public String getNickname() {
        return nameField.getText();
    }

    public JButton getYesButton() {
        return yesButton.getButton();
    }

    // Setter
    public void setNickname(String nickname) {
        nameField.setText(nickname);
    }
}
