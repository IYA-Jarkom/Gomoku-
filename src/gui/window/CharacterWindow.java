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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Kelas window untuk memilih character
 */
public class CharacterWindow extends BackgroundPanel {
    // Atribut
    private ArrayList<ImageButton> characters;

    // Konstruktor
    public CharacterWindow() {
        // Background
        super("bg-character.png");
        super.setOpaque(false);

        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridLayout(2, 1, 100, 20));

        // Panel Role
        TransparentPanel characterPanel = new TransparentPanel();
        characterPanel.setLayout(new GridLayout(2, 3));
        // Button character
        characters = new ArrayList<>();
        ImageButton penguinButton = new ImageButton("big-character-penguin.png");
        characterPanel.add(penguinButton.getButton());
        characters.add(penguinButton);
        ImageButton deerButton = new ImageButton("big-character-deer.png");
        characterPanel.add(deerButton.getButton());
        characters.add(deerButton);
        ImageButton birdButton = new ImageButton("big-character-bird.png");
        characterPanel.add(birdButton.getButton());
        characters.add(birdButton);
        ImageButton dogButton = new ImageButton("big-character-dog.png");
        characterPanel.add(dogButton.getButton());
        characters.add(dogButton);
        ImageButton duckButton = new ImageButton("big-character-duck.png");
        characterPanel.add(duckButton.getButton());
        characters.add(duckButton);
        ImageButton rabbitButton = new ImageButton("big-character-rabbit.png");
        characterPanel.add(rabbitButton.getButton());
        characters.add(rabbitButton);

        // Finalisasi
        elementPanel.setBorder(new EmptyBorder(290, 0, 0, 0));
        elementPanel.add(characterPanel);
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel);
    }

    // Getter
    public ArrayList<ImageButton> getCharacters() {
        return characters;
    }

    public JButton getCharacterButton(int i) {
        return characters.get(i).getButton();
    }
}
