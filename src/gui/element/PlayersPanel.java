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

    // Konstruktor
    public PlayersPanel(ArrayList<String> characterFileInfo, PlayersDetail playersDetail, String thisPlayer) {
        // Ubah nama thisPlayer menjadi "You"
        playersDetail.changePlayerNameToYou(thisPlayer);

        // Buat tabel dengan baris sejumlah pemain yang ada
        setLayout(new GridLayout(playersDetail.size(), 1, 0, 0));
        for (int i = 0; i < playersDetail.size(); i++) { // Looping sejumlah jumlah pemain
            // Panel untuk tiap player
            TransparentPanel playerPanel = new TransparentPanel();
            playerPanel.setLayout(new GridLayout(1, 3, 20, 0));

            // Player's character sign
            int characterSign = playersDetail.getCharacterSign(i);
            ImageButton playerIcon = new ImageButton("character-" + characterFileInfo.get(characterSign));
            playerPanel.add(playerIcon.getButton());

            // Player's name
            playerPanel.add(new Label(playersDetail.getPlayerName(i)));

            // Room's master key
            boolean isMaster = playersDetail.getIsMaster(i);
            if (isMaster) {
                ImageButton masterIcon = new ImageButton("key.png");
                playerPanel.add(masterIcon.getButton());
            } else {
                playerPanel.add(new Label(" "));
            }
            add(playerPanel);
        }
    }

    // Setter
    // Mengubah warna pada nama player untuk menunjukkan giliran bermain menjadi warna hijau
    public void setTurn(String playerName) {
        int i = 0;
        while(!playersDetail.getPlayerName(i).equals(playerName) && i < playersDetail.size()) {
            i++;
        }

    }

    // Mengubah master key pada player tertentu
    public void setMaster(String playerName) {
    }
}
