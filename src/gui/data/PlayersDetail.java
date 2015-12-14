package gui.data;

import java.util.ArrayList;

/**
 * Kelas untuk menyimpan keterangan rinci dari kumpulan player pada suatu room
 */
public class PlayersDetail {
    // Atribut
    private ArrayList<PlayerDetail> playersDetail;

    // Konstruktor
    public PlayersDetail() {
        playersDetail = new ArrayList<>();
    }

    // Getter
    public int getCharacterSign(int i) {
        return playersDetail.get(i).getCharacterSign();
    }
    public String getPlayerName(int i) {
        return playersDetail.get(i).getPlayerName();
    }
    public boolean getIsTurn(int i) {
        return playersDetail.get(i).isTurn();
    }
    public boolean getIsMaster(int i) {
        return playersDetail.get(i).isMaster();
    }

    // Setter
    public void setCharacterSign(int i, int characterSign) {
        playersDetail.get(i).setCharacterSign(characterSign);
    }
    public void setPlayerName(int i, String playerName) {
        playersDetail.get(i).setPlayerName(playerName);
    }
    public void setIsMaster(int i, boolean isMaster) {
        playersDetail.get(i).setMaster(isMaster);
    }

    // Method
    public void add(int characterSign, String playerName, boolean isTurn, boolean isMaster) {
        playersDetail.add(new PlayerDetail(characterSign, playerName, isTurn, isMaster));
    }

    public void set(int i, int characterSign, String playerName, boolean isTurn, boolean isMaster) {
        PlayerDetail newPlayerDetail = new PlayerDetail(characterSign, playerName, isTurn, isMaster);
        playersDetail.set(i, newPlayerDetail);
    }

    // Mencari nama player yang bermain pada daftar seluruh player dan mengubah nama player tersebut dengan "You"
    public void changePlayerNameToYou(String playerName) {
        if (!playersDetail.isEmpty()) {
            int i = 0;
            while (!playersDetail.get(i).getPlayerName().equals(playerName) && i<playersDetail.size()-1) {
                i++;
            }
            playersDetail.get(i).setPlayerName("You");
        }
    }

    // Mengembalikan jumlah pemain
    public int size() {
        return playersDetail.size();
    }
}
