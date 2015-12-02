package environment;


import user.Player;
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
public class Room {
    // Atribut
    private String name;
    private Board board;
    private Player master;
    private ArrayList<Player> players;
    private ArrayList<Integer> playerSymbols;
    private Player turn;
    
    // Konstruktor
    public Room() {
        board = new Board();
        master = new Player();
        players = new ArrayList<Player>();
        playerSymbols = new ArrayList<Integer>();
        turn = new Player();
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
    
    public Player getPlayer(int i) {
        return players.get(i);
    }
    
    public int getPlayerSymbol(int i) {
        return playerSymbols.get(i);
    }
    
    public Player turn() {
        return turn;
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
    
    public void setPlayerSymbol(int i, int playerSymbol) {
        playerSymbols.set(i, playerSymbol);
    }
    
    public void setTurn(Player _turn) {
        turn = _turn;
    }
    
    // Method
    public int countPlayers() {
        return players.size();
    }
}
