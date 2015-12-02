package user;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class PlayerList {
    // Atribut
    private ArrayList<Player> players;
    
    // Konstruktor
    public PlayerList() {
        players = new ArrayList<Player>();
    }
    
    // Getter
    public Player getPlayer(int i) {
        return players.get(i);
    }
    
    // Setter
    public void setPlayer(int i, Player player) {
        players.set(i, player);
    }
    
    // Method
    public int countPlayers() {
        return players.size();
    }
    
    public void load() {
        
    }
    
    public void save() {
        try {
            PrintWriter fileWriter = new PrintWriter("playerlist.txt");
            for (int i = 0; i < 5; i++) {
                fileWriter.println("1 2 3 4 5");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayerList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
