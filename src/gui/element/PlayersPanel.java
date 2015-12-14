package gui.element;

import gui.data.PlayersDetail;

import java.awt.*;
import java.util.ArrayList;

/**
 * Kelas TransparentPanel yang berisikan informasi detail mengenai player pada room
 */
public class PlayersPanel extends TransparentPanel {
    // Atribut
    private PlayersDetail playersDetail;
    private ArrayList<Label> playersNameLabel;

    // Konstruktor
    public PlayersPanel(ArrayList<String> characterFileInfo, PlayersDetail playersDetail, String thisPlayer) {
        this.playersDetail = playersDetail;
        // Ubah nama thisPlayer menjadi "You"
        this.playersDetail.changePlayerNameToYou(thisPlayer);

        // Buat tabel dengan baris sejumlah pemain yang ada
        setLayout(new GridLayout(this.playersDetail.size(), 1, 0, 0));
        playersNameLabel = new ArrayList<>();
        for (int i = 0; i < this.playersDetail.size(); i++) { // Looping sejumlah jumlah pemain
            // Panel untuk tiap player
            TransparentPanel playerPanel = new TransparentPanel();
            playerPanel.setLayout(new GridLayout(1, 3, 20, 0));

            // Player's character sign
            int characterSign = this.playersDetail.getCharacterSign(i);
            ImageButton playerIcon = new ImageButton("character-" + characterFileInfo.get(characterSign));
            playerPanel.add(playerIcon.getButton());

            // Player's name
            Label playerName;
            if (this.playersDetail.getIsTurn(i)) {
                playerName = new Label(this.playersDetail.getPlayerName(i), "green", 30);
            } else {
                playerName = new Label(this.playersDetail.getPlayerName(i), "white", 30);
            }
            playerPanel.add(playerName);
            playersNameLabel.add(playerName);

            // Room's master key
            boolean isMaster = this.playersDetail.getIsMaster(i);
            if (isMaster) {
                ImageButton masterIcon = new ImageButton("key.png");
                playerPanel.add(masterIcon.getButton());
            } else {
                playerPanel.add(new Label(" "));
            }
            add(playerPanel);
        }
    }

    // Getter
    public PlayersDetail getPlayersDetail() {
        return playersDetail;
    }

    // Setter
    // Mengubah warna pada nama player untuk menunjukkan giliran bermain menjadi warna hijau
    public void setTurn(String playerName) {
        for (int i=0; i<playersNameLabel.size(); i++) {
            playersNameLabel.get(i).setColor("white");
        }
        playersNameLabel.get(playersNameLabel.indexOf(playerName)).setColor("green");
    }
}
