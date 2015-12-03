

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class PlayerList {
    // Atribut
    private ArrayList<Player> players;
    private File file;
    
    // Konstruktor
    public PlayerList() {
        players = new ArrayList<Player>();
        file = new File("playerlist.txt");
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
        try {
            FileReader fileReader = new FileReader(file.getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line = "";
            ArrayList<String> temp = new ArrayList<String>();
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    // Get file content here
                }
            } catch (IOException ex) {
                Logger.getLogger(PlayerList.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayerList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // Put file content here
            // bufferedWriter.write();
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(PlayerList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
