

import java.io.*;
import java.net.*;
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
 * @author yoga
 */
public class Server {
    // Atribut
    static ServerSocket server;
    static public Socket socket;
    static ArrayList<Room> listRoom = new ArrayList();

    // Kelas
    private static class GetPlayerRequest
            extends Thread {

        public Socket socket;
        public Player player;

        public GetPlayerRequest(Socket clientSocket) {
            this.socket = clientSocket;
        }

        public void run() {
            try {
                DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
                //TUNGGU NAMA DARI CLIENT
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String name=inFromClient.readLine();
                System.out.println(name + " has been connected");
                player=new Player(name,0,0);
                //KASIH LISTROOM KE CLIENT TERSEBUT
                ObjectOutputStream objectToClient = new ObjectOutputStream(socket.getOutputStream());
                objectToClient.writeObject(listRoom);
                //DAPET ROOM YANG DIINGINKAN USER
                ObjectInputStream objectFromClient = new ObjectInputStream(socket.getInputStream());
                int roomNumber = (Integer) objectFromClient.readObject();
                if (roomNumber >= 0) {
                    listRoom.get(roomNumber).addPlayers(player);
                }else{
                    Room newRoom= new Room(player.getNickName());
                    listRoom.add(newRoom);
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static class GetBoardMoveRequest extends Thread {
        // Atribut
        Board board;
        
        // Konstruktor
        public GetBoardMoveRequest(Board board) {
            this.board = board;
        }
        
        // Method
        public void run() {
            try {
                BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String playerSymbol = clientReader.readLine();
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        server = new ServerSocket(2000);
        while (true) {

            socket = server.accept();
            Thread t = new Thread(new GetPlayerRequest(socket));
            t.start();
        }
    }
}
