package gui.data;

/**
 * Kelas untuk menyimpan keterangan rinci dari player pada suatu room
 * Keterangan yang disimpan adalah tanda karakter pemain pada board, nama pemain,
 * dan keterangan apakah pemain adalah pemilik ruangan tersebut.
 */
public class PlayerDetail {
    // Atribut
    private int characterSign;
    private String playerName;
    private boolean isTurn;
    private boolean isMaster;

    // Konstruktor
    public PlayerDetail(int characterSign, String playerName, boolean isTurn, boolean isMaster) {
        this.characterSign = characterSign;
        this.playerName = playerName;
        this.isTurn = isTurn;
        this.isMaster = isMaster;
    }

    // Getter
    public int getCharacterSign() {
        return characterSign;
    }
    public String getPlayerName() {
        return playerName;
    }
    public boolean isTurn() {
        return isTurn;
    }
    public boolean isMaster() {
        return isMaster;
    }

    // Setter
    public void setCharacterSign(int characterSign) {
        this.characterSign = characterSign;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setTurn(boolean turn) {
        isTurn = turn;
    }
    public void setMaster(boolean master) {
        isMaster = master;
    }
}
