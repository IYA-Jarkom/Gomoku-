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
import gui.element.ImageButton;
import gui.element.TransparentPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 * Kelas window untuk memilih character
 */
public class CharacterWindow extends BackgroundPanel {
    // Atribut
    private ImageButton yesButton;
    private ImageButton noButton;
    
    // Konstruktor
    public CharacterWindow() {
        // Background
        super("bg-character.png");
        super.setOpaque(false);
       
        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2,1,100,20));
        
        // Panel Role
        TransparentPanel characterPanel = new TransparentPanel();
        characterPanel.setLayout(new GridLayout(2,3));
        // Button character
        ImageButton penguinButton = new ImageButton("character-penguin.png");
        characterPanel.add(penguinButton.getButton());
        ImageButton deerButton = new ImageButton("character-deer.png");
        characterPanel.add(deerButton.getButton());
        ImageButton birdButton = new ImageButton("character-bird.png");
        characterPanel.add(birdButton.getButton());
        ImageButton dogButton = new ImageButton("character-dog.png");
        characterPanel.add(dogButton.getButton());
        ImageButton duckButton = new ImageButton("character-duck.png");
        characterPanel.add(duckButton.getButton());
        ImageButton rabbitButton = new ImageButton("character-rabbit.png");
        characterPanel.add(rabbitButton.getButton());
        
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
        elementPanel.setBorder(new EmptyBorder(290,0,0,0));
        elementPanel.add(characterPanel);
        elementPanel.add(decisionPanel);
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }
    
    // Getter
    public JButton getYesButton() {
        return yesButton.getButton();
    }
    public JButton getNoButton() {
        return noButton.getButton();
    }
}
