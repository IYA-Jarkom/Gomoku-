package algorithm;


import java.io.Serializable;

/**
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 *
 * @author Irene Wiliudarsan (13513002)
 * @author Yoga Adrian       (13513030)
 * @author Angela Lynn       (13513032)
 */

/**
 *  Kelas untuk menyimpan pemain dan data yang dimilikinya
 *
 */
public class Player implements Serializable {
    // Atribut
    private String nickName;
    private int winNumber;
    private int loseNumber;
    private int roomID;
    private int clientID;

    // Konstruktor
    public Player() {
        nickName = "Default";
        winNumber = 0;
        loseNumber = 0;
    }

    public Player(String _nickName, int _winNumber, int _loseNumber) {
        nickName = _nickName;
        winNumber = _winNumber;
        loseNumber = _loseNumber;
    }

    // Getter
    public String getNickName() {
        return nickName;
    }

    public int getWinNumber() {
        return winNumber;
    }

    public int getLoseNumber() {
        return loseNumber;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getClientID() {
        return clientID;
    }

    // Setter
    public void setNickName(String _nickName) {
        nickName = _nickName;
    }

    public void setWinNumber(int _winNumber) {
        winNumber = _winNumber;
    }

    public void setLoseNumber(int _loseNumber) {
        loseNumber = _loseNumber;
    }

    public void setRoomName(int _roomID) {
        roomID = _roomID;
    }

    public void setClientName(int _clientID) {
        clientID = _clientID;
    }

}
