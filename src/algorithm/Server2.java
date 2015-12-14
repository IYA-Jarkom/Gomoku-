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

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TUGAS BESAR 2 IF3130 Jaringan Komputer -RETURN OF POI-
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Server2 {

    static public ServerSocket server;
    static public ArrayList<Room> listRoom = new ArrayList();
    static public ArrayList<Player> listPlayer = new ArrayList();
    static public ArrayList<ClientController> listClient = new ArrayList();
    static public int clientNumber = 0;

    // Kelas
    public static class ClientController
            extends Thread {

        public Socket socket;
        public int idClient;
        public int idPlayer;
        public int idRoom;

        public ClientController(Socket clientSocket) {
            this.socket = clientSocket;
            idClient = clientNumber;
        }

        public void Parse(String req) throws Exception {
            String[] command = req.split("\\s+");
            
            if (command[0].equals("create-room")) {
                String stringToClient;
                
                listRoom.add(new Room(command[1], listPlayer.get(idPlayer)));
                listRoom.get(listRoom.size() - 1).addPlayers(listPlayer.get(idPlayer));
                idRoom = listRoom.size() - 1;
                listPlayer.get(idPlayer).setRoomName(idRoom);
                SendToClient("success create-room "+command[1]+" "+idRoom);
                
                // Mengirim data player dalam room
                stringToClient = "players "+listRoom.get(idRoom).countPlayers()+" ";
                for (int i = 0; i < listRoom.get(idRoom).countPlayers(); i++) {
                    stringToClient += listRoom.get(idRoom).getPlayer(i).getNickName()+" "+listRoom.get(idRoom).getPlayer(i).getWinNumber()+" "+listRoom.get(idRoom).getPlayer(i).getLoseNumber()+" ";
                }
                SendToClient(stringToClient);
            } else if (command[0].equals("add-user")) {
                boolean permit = true;
                for (int i = 0; i < listPlayer.size(); i++) {
                    if (command[1].equals(listPlayer.get(i).getNickName())) {
                        permit = false;
                        break;
                    }
                }
                if (!permit) {
                    idPlayer = listPlayer.size();
                    listPlayer.add(new Player(command[1], 0, 0));
                    listPlayer.get(idPlayer).setClientName(idClient);

                    SendToClient("success add-user "+command[1]+" "+idClient);
                } else {
                    SendToClient("fail add-user");
                }
            } else if (command[0].equals("get-room")) {
                String str = "List of Room \n";
                for (int i = 0; i < listRoom.size(); i++) {
                    str = str + i + ". " + listRoom.get(i).getName() + "\n";
                }
                SendToClient(str);
            } else if (command[0].equals("join-room")) {
                int id = Integer.parseInt(command[1]);
                if (listRoom.get(id).isOpen()) {
                    // Client boleh masuk ke dalam room
                    String stringToClient;
                    idRoom = id;
                    listRoom.get(idRoom).addPlayers(listPlayer.get(idPlayer));
                    listPlayer.get(idPlayer).setRoomName(idRoom);
                    
                    // Mengirim nama room, nama masternya, dan id room
                    SendToClient("success join-room " + listRoom.get(idRoom).getName() + " " + listRoom.get(idRoom).getMaster().getNickName()+" "+idRoom);
                    // Mengirim data player dalam room ke semua client
                    stringToClient = "players "+listRoom.get(idRoom).countPlayers()+" ";
                    for (int i = 0; i < listRoom.get(idRoom).countPlayers(); i++) {
                        stringToClient += listRoom.get(idRoom).getPlayer(i).getNickName()+" "+listRoom.get(idRoom).getPlayer(i).getWinNumber()+" "+listRoom.get(idRoom).getPlayer(i).getLoseNumber()+" ";
                    }
                    
                    for (int i = 0; i < listClient.size(); i++) {
                        if (listClient.get(i).idRoom == idRoom) {
                            sendToSpesificClient(stringToClient, i);
                        }
                    }

//                    // Mengirim isi board di room
//                    stringToClient = "board ";
//                    for (int i = 0; i < 20; i++) {
//                        for (int j = 0; j < 20; j++) {
//                            position.setLocation(i, j);
//                            stringToClient += listRoom.get(Integer.parseInt(command[1])).getBoard().getBoardElement(position)+" ";
//                        }
//                    }
//                    SendToClient(stringToClient);
                }
            } else if (command[0].equals("start-game")) {
                if (listRoom.get(idRoom).getMaster().getNickName().equals(listPlayer.get(idPlayer).getNickName())) {
                    // Client adalah master, boleh menginisiasi game
                    if (listRoom.get(idRoom).countPlayers() >= 3) {
                        // Pemain sudah cukup untuk memulai game
                        listRoom.get(idRoom).isOpen(false);
                        listRoom.get(idRoom).isGameStart(true);
                        listRoom.get(idRoom).setTurn(listPlayer.get(idPlayer));
                        SendToClient("success start-game");
                        // Mengirim indeks player yang mendapat turn
                        SendToClient("turn "+"0");
                    } else {
                        // Pemain belum cukup untuk memulai game
                        listRoom.get(idRoom).isGameStart(false);
                        SendToClient("fail start-game");
                    }
                }
            } else if (command[0].equals("board")) {
                Point position = new Point();
                int turn = Integer.parseInt(command[4]);
                
                // Mengupdate board sesuai permintaan client
                position.setLocation(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                if (listRoom.get(Integer.parseInt(command[3])).getBoard().getBoardElement(position) == -1) {
                    // Posisi board boleh diisi
                    listRoom.get(Integer.parseInt(command[3])).getBoard().setBoardElement(position, Integer.parseInt(command[4]));
                    
                    // Mengirim isi board yang diupdate ke setiap client
                    for (int i = 0; i < listClient.size(); i++) {
                        sendToSpesificClient("board update", i);
                    }
                    
                    for (int i = 0; i < 20; i++) {
                        String boardElement = "";
                        
                        for (int j = 0; j < 20; j++) {
                            position.setLocation(i, j);
                            boardElement += listRoom.get(Integer.parseInt(command[3])).getBoard().getBoardElement(position);
                            boardElement += " ";
                        }
                        
                        for (int j = 0; j < listClient.size(); j++) {
                            sendToSpesificClient(boardElement, j);
                        }
                        
                        // Mengecek status game
                        if (listRoom.get(Integer.parseInt(command[3])).getBoard().checkWinner(position) >= 0) {
                            listRoom.get(Integer.parseInt(command[3])).getPlayer(turn).setWinNumber(listRoom.get(Integer.parseInt(command[3])).getPlayer(turn).getWinNumber()+1);
                            listRoom.get(Integer.parseInt(command[3])).isGameStart(false);
                        } else {
                            listRoom.get(Integer.parseInt(command[3])).isGameStart(true);
                            
                            // Giliran pemain selanjutnya
                            if ((turn+1) == listRoom.get(Integer.parseInt(command[3])).countPlayers()) {
                                turn = 0;
                            } else {
                                turn++;
                            }
                        }
                    }
                } else {
                    // Posisi board tidak boleh diisi
                    // Mengirim tanda bahwa board tidak diupdate
                    for (int i = 0; i < listClient.size(); i++) {
                        sendToSpesificClient("boardnotupdate", i);
                    }
                }
                
                // Mengirim status game
                if (listRoom.get(Integer.parseInt(command[3])).isGameStart()) {
                    // Game masih berjalan
                    for (int i = 0; i < listClient.size(); i++) {
                        sendToSpesificClient("notstop", i);
                    }
                } else {
                    // Game berhenti
                    for (int i = 0; i < listClient.size(); i++) {
                        sendToSpesificClient("stop "+turn+" "+listRoom.get(Integer.parseInt(command[3])).getPlayer(turn).getNickName()+" "+listRoom.get(Integer.parseInt(command[3])).getPlayer(turn).getWinNumber(), i);
                    }
                }
                
                // Mengirim turn player untuk bermain
                SendToClient("turn "+turn);
            } else {

                SendToClient("unknown command");

            }
        }

        void SendToClient(String msg) throws Exception {
            //create output stream attached to socket
            PrintWriter outToClient = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            //send msg to server
            outToClient.print(msg + '\n');
            outToClient.flush();
        }

        public void run() {
            try {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request;
                while ((request = inFromClient.readLine()) != null) {
                    System.out.println(request);
                    if (request.equals("")) {
                        System.out.println("kosong");
                    }
                    Parse(request);
                }
            } catch (IOException ex) {
                Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void sendToSpesificClient(String str, int x) throws Exception {
        //untuk kirim move dari 1 client ke semua client dalam room
        listClient.get(x).SendToClient(str);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Server IP address : " + ip);
            System.out.println("Server IP hostname : " + hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        server = new ServerSocket(2000);
        while (true) {
            Socket socket = server.accept();
            System.out.println("connected");
            ClientController clientcontroller = new ClientController(socket);

            listClient.add(clientcontroller);
            clientNumber++;
            Thread t = new Thread(clientcontroller);
            t.start();
        }
    }

}
