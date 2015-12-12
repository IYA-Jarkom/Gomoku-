package algorithm;


import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Room implements Serializable {
    // Atribut
    private String name;
    private Board board;
    private Player master;
    private ArrayList<Player> players;
    private Player turn;
    private boolean isGameStart;

    // Konstruktor
    public Room() {
        board = new Board();
        master = new Player();
        players = new ArrayList<Player>();
        turn = new Player();
    }

    public Room(String _name, Player _master) {
        name = _name;
        board = new Board();
        master = _master;
        players = new ArrayList<Player>();
        turn = new Player();
    }

    public Room(Room _room) {
        name = _room.getName();
        board = new Board();
        board.setBoard(_room.getBoard());
        master = _room.getMaster();
        players = new ArrayList<Player>();
        for (int i = 0; i < _room.countPlayers(); i++) {
            players.add(_room.getPlayer(i));
        }
        turn = _room.turn();
    }

    // Getter
    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public Player getMaster() {
        return master;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int i) {
        return players.get(i);
    }

    public Player turn() {
        return turn;
    }

    public boolean isGameStart() {
        return isGameStart;
    }

    // Setter
    public void setName(String _name) {
        name = _name;
    }

    public void setBoard(Board _board) {
        board = _board;
    }

    public void setMaster(Player _master) {
        master = _master;
    }

    public void setPlayer(int i, Player player) {
        players.set(i, player);
    }

    public void setTurn(Player _turn) {
        turn = _turn;
    }

    public void isGameStart(boolean truth) {
        isGameStart = truth;
    }

    // Method
    public int countPlayers() {
        return players.size();
    }

    public void addPlayers(Player player) {
        players.add(player);
    }
}