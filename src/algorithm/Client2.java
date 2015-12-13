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
    
    // Method
    public static void parse(String req) throws IOException {
        // Memisahkan req berdasarkan spasi
        String[] command = req.split("\\s+");
        
        if (command[0].equals("room")) {
            String stringFromServer;
            Point position = new Point();
            
            room.setName(command[1]);
            
            // Menerima data player dalam room
            for (int i = 0; i < Integer.parseInt(command[2]); i++) {
                stringFromServer = objectFromServer.readLine();
                String[] playerData = stringFromServer.split("\\s+");
                room.addPlayers(new Player(playerData[0], Integer.parseInt(playerData[1]), Integer.parseInt(playerData[2])));
            }
            
            room.setMaster(new Player(room.getPlayer(0).getNickName(), room.getPlayer(0).getWinNumber(), room.getPlayer(0).getLoseNumber()));
            
            // Menerima isi board di room
            for (int i = 0; i < 20; i++) {
                stringFromServer = objectFromServer.readLine();
                String[] boardElement = stringFromServer.split("\\s+");
                for (int j = 0; j < 20; j++) {
                    position.setLocation(i, j);
                    room.getBoard().setBoardElement(position, Integer.parseInt(boardElement[j]));
                }
            }
        } else if (command[0].equals("turn")) {
            
        }
    }

    private static void sendToServer(String msg) throws Exception {
        //create output stream attached to socket
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //send msg to server
        outToServer.print(msg + '\n');
        outToServer.flush();
    }
    
    private static void waitInRoom() throws Exception {
        String stringFromServer, inputFromUser;
        
        sendToServer("enter "+player.getRoomID()+" "+player.getNickName());
        System.out.println(player.getNickName()+" masuk room");
        // Menerima nama room dan jumlah player dalam room
        stringFromServer = objectFromServer.readLine();
        parse(stringFromServer);

        if (room.getMaster().getNickName().equals(player.getNickName())) {
            // Client adalah room master
            while (!room.isGameStart()) {
                inputFromUser = scan.nextLine();
                sendToServer("start "+player.getRoomID());
                
                // Menerima keputusan start game dari server
                stringFromServer = objectFromServer.readLine();
                if (stringFromServer.equals("yes")) {
                    System.out.println("Game dimulai");
                    room.isOpen(false);
                    room.isGameStart(true);
                } else {
                    System.out.println("Game tidak boleh dimulai");
                }
            }
        }
    }
    
    private static void playingGame() throws Exception {
        String stringFromServer;
        
        while (room.isGameStart()) {
            sendToServer("play "+player.getRoomID());
            stringFromServer = objectFromServer.readLine();
            parse(stringFromServer);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.print("Input server IP hostname : ");
        String host = scan.nextLine();
        clientSocket = new Socket(host, 2000);
        Thread t = new Thread(new StringGetter());
        t.start();
        while (true) {
            PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            System.out.print("COMMAND : ");
            //send msg to server
            String msg = scan.nextLine();
            outToServer.print(msg + '\n');
            outToServer.flush();
        }
    }

/*
    public static void main(String[] args) {
        try {
            scan = new Scanner(System.in);
            String stringFromServer;

            System.out.print("Input server IP hostname : ");
            String host=scan.nextLine();
            clientSocket = new Socket(host, 2000);

            objectFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //----------------------------
            // Untuk testing
            String inputFromUser;
            // Meminta nama player
            System.out.println("Masukkan nama");
            inputFromUser = scan.nextLine();
            player = new Player(inputFromUser, 0, 0);
            // Meminta nomor room
            System.out.println("Masukkan nomor room. -1 jika ingin membuat room baru");
            inputFromUser = scan.nextLine();
            if (Integer.parseInt(inputFromUser) < 0) {
                // Membuat room baru
                System.out.println("Masukkan nama room");
                inputFromUser = scan.nextLine();
                sendToServer("create-room "+inputFromUser+" "+player.getNickName());
                stringFromServer = objectFromServer.readLine();
                player.setRoomName(Integer.parseInt(stringFromServer));
                System.out.println("Berhasil membuat room baru");
            } else {
                player.setRoomName(Integer.parseInt(inputFromUser));
            }
            room = new Room();
            //----------------------------
            
            // User berada di room
//            while (true) {
                waitInRoom();
                //playingGame();
//            }
        } catch (Exception e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }
*/
}
