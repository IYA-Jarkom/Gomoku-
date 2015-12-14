/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * TUGAS BESAR 2 IF3130 Jaringan Komputer -RETURN OF POI-
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Client2 {

    static public Socket clientSocket;
    static public ArrayList<Room> listRoom; 
    static public BufferedReader objectFromServer;
    static public PrintWriter objectToServer;
    static public Scanner scan;
    static public int clientRoom;
    static public Player player;
    static public Room room;
    public static class StringGetter
            extends Thread {

        public void run() {
            try {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String response;
                while (true) {
                    response = inFromServer.readLine();
                    System.out.println(response);
                    parse(response);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static String[] command;
    // Method
    public static void parse(String req) throws IOException {
        // Memisahkan req berdasarkan spasi
        command = req.split("\\s+");
        
        if (command[1].equals("add-user")) {
            if (command[0].equals("success")) {
                // Penambahan client ke dalam game berhasil
                player = new Player(command[2], 0, 0);
                player.setClientName(Integer.parseInt(command[3]));
            }
        } else if (command[1].equals("create-room")) {
            if (command[0].equals("success")) {
                // Pembuatan room berhasil
                room = new Room(command[2], player);
                player.setRoomName(Integer.parseInt(command[3]));
            }
        } else if (command[1].equals("join-room")) {
            if (command[0].equals("success")) {
                // Join room berhasil
                room = new Room(command[2], new Player(command[3], 0, 0));
                player.setRoomName(Integer.parseInt(command[4]));
            }
        } else if (command[0].equals("players")) {
            // Menerima data player dalam room
            int playerIndex = 2;
            
            room.clearPlayers();
            for (int i = 0; i < Integer.parseInt(command[1]); i++) {
                room.addPlayers(new Player(command[playerIndex], Integer.parseInt(command[playerIndex+1]), Integer.parseInt(command[playerIndex+2])));
                playerIndex += 3;
            }
        } else if (command[0].equals("update-board")) {
            // Menerima isi board yang diupdate
            Point position = new Point();
            int boardIndex = 1;
            
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    position.setLocation(i, j);
                    room.getBoard().setBoardElement(position, Integer.parseInt(command[boardIndex]));
                    boardIndex++;
                }
            }
        } else if (command[1].equals("start-game")) {
            if (command[0].equals("success")) {
                // Game dimulai
                room.isOpen(false);
                room.isGameStart(true);
            } else {
                // Game belum boleh dimulai
                room.isGameStart(false);
            }
        } else if (command[0].equals("turn")) {
            // Menerima indeks player yang mendapat turn
            room.setTurn(room.getPlayer(Integer.parseInt(command[1])));
        }
    }

    private static void sendToServer(String msg) throws Exception {
        //create output stream attached to socket
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //send msg to server
        outToServer.print(msg + '\n');
        outToServer.flush();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        Scanner scan = new Scanner(System.in);

        System.out.print("Input server IP hostname : ");
        String host = scan.nextLine();
        clientSocket = new Socket(host, 2000);
        Thread t = new Thread(new StringGetter());
        t.start();
        while (true) {
            System.out.print("COMMAND : ");
            //send msg to server
            String msg = scan.nextLine();
            sendToServer(msg);
        }
    }
}
