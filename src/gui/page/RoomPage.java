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

import gui.data.PlayersDetail;
import gui.element.*;
import gui.element.Label;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Kelas halaman ruangan tempat pengguna bermain
 */
public class RoomPage extends BackgroundPanel {
    // Atribut
    private ArrayList<String> characterFileInfo;
    private String thisPlayer;
    private ImageButton backButton;
    private ImageButton startButton;
    private PlayersPanel playersPanel;
    private int[][] boardValue;
    private Label[][] board;

    // Konstruktor
    public RoomPage(String roomName, ArrayList<String> characterFileInfo, PlayersDetail playersDetail, String thisPlayer, int[][] boardValue) {
        // Background
        super("bg-room.jpg");

        this.boardValue = boardValue;
        this.characterFileInfo = characterFileInfo;
        this.thisPlayer = thisPlayer;

        // Panel Info
        TransparentPanel infoPanel = new TransparentPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        infoPanel.setPreferredSize(new Dimension(350,750));

        // Panel Header
        TransparentPanel headerPanel = new TransparentPanel();
        // Button back
        backButton = new ImageButton("button-back.png");
        headerPanel.add(backButton.getButton());
        System.out.println("l: " + roomName);
        headerPanel.add(new Label(roomName, "white", 40));
        infoPanel.add(headerPanel);

        // Panel Players
        playersPanel = new PlayersPanel(characterFileInfo, playersDetail, thisPlayer);
        JScrollPane playersScroll = new JScrollPane(playersPanel);
        playersScroll.setOpaque(false);
        playersScroll.getViewport().setOpaque(false);
        playersScroll.setBorder(null);
        playersScroll.setBounds(0,0,350,450);
        playersScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        TransparentPanel contentPane = new TransparentPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(350, 450));
        contentPane.add(playersScroll);
        infoPanel.add(contentPane);

        // Button start
        startButton = new ImageButton("button-start.png");
        infoPanel.add(startButton.getButton());

        // Panel board
        board = new Label[20][20];
        TransparentPanel boardPanel = new TransparentPanel();
        boardPanel.setLayout(new GridLayout(20, 20, 0, 0));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        boardPanel.setPreferredSize(new Dimension(700, 700));
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Label label = new Label("");
                if (boardValue[i][j] != -1) {
                    // Board telah terisi
                    String fileName = "icon-" + characterFileInfo.get(boardValue[i][j]);
                    label.setIcon(fileName);
                }
                label.setBorder(BorderFactory.createLineBorder(Color.decode("#00B0FF")));
                board[i][j] = label;
                boardPanel.add(label);
            }
        }

        // Panel Element
        TransparentPanel elementPanel = new TransparentPanel();
        elementPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        elementPanel.add(infoPanel, c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        c.insets = new Insets(0, 150, 0, 150);
        elementPanel.add(boardPanel, c);

        // Finalisasi
        setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        add(elementPanel, Component.LEFT_ALIGNMENT);
    }

    // Getter
    public JButton getBackButton() {
        return backButton.getButton();
    }

    public JButton getStartButton() {
        return startButton.getButton();
    }

    public Label[][] getBoard() {
        return board;
    }

    // Setter
    // Mengisi board pada posisi ke (x,y) menjadi characterSign
    public void setBoardValue(int x, int y, int characterSign) {
        if (characterSign != -1) {
            // Board telah terisi
            boardValue[x][y] = characterSign;
            String fileName = "icon-" + characterFileInfo.get(characterSign);
            board[x][y].setIcon(fileName);
        }
    }
}
