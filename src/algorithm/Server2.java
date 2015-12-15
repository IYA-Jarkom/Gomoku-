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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
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
    static public int MAX_PLAYER = 6;

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

            if (command[0].equals("chat")){
                String str=": ";
                str=str+listPlayer.get(idPlayer).getNickName()+" "+command[1];
                for(int i=0;i<listRoom.get(idRoom).getPlayers().size();i++){
                    sendToSpesificClient(str,listRoom.get(idRoom).getPlayers().get(i).getClientID());
                }
            }else if (command[0].equals("create-room")) {
                String stringToClient;
                if (command.length > 2) {
                    // Create room berhasil
                    listRoom.add(new Room(command[1], listPlayer.get(idPlayer)));
                    idRoom = listRoom.size() - 1;
                    listPlayer.get(idPlayer).setRoomName(idRoom);
                    listPlayer.get(idPlayer).setCharacter(Integer.parseInt(command[2]));
                    listRoom.get(idRoom).addPlayers(listPlayer.get(idPlayer));
                    SendToClient("success create-room "+command[1]+" "+idRoom + " " + command[2]);
                } else {
                    // Mengecek apakah nama room sudah dipakai
                    boolean permit = true;
                    for (int i = 0; i < listRoom.size(); i++) {
                        if (command[1].equals(listRoom.get(i).getName())) {
                            permit = false;
                            break;
                        }
                    }

                    if (permit) {
                        SendToClient("success-room create-room");
                    } else {
                        SendToClient("fail-room create-room");
                    }
                }
            } else if (command[0].equals("add-user")) {
                boolean permit = true;
                for (int i = 0; i < listPlayer.size(); i++) {
                    if (command[1].equals(listPlayer.get(i).getNickName())) {
                        permit = false;
                        break;
                    }
                }
                if (permit) {
                    idPlayer = listPlayer.size();
                    listPlayer.add(new Player(command[1], 0, 0));
                    listPlayer.get(idPlayer).setClientName(idClient);

                    SendToClient("success add-user "+command[1]+" "+idClient);
                } else {
                    SendToClient("fail add-user");
                }
            } else if (command[0].equals("get-room")) {
                String str = "list-of-room ";
                for (int i = 0; i < listRoom.size(); i++) {
                    str = str + listRoom.get(i).getName() + " " + listRoom.get(i).countPlayers()  + " ";
                }
                SendToClient(str);
            } else if (command[0].equals("get-board")) {
                // Mengirim isi board yang diupdate ke setiap client
                Point position = new Point();
                String stringToClient = "update-board ";

                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        position.setLocation(i, j);
                        stringToClient += listRoom.get(idRoom).getBoard().getBoardElement(position)+" ";
                    }
                }
                for (int i = 0; i < listClient.size(); i++) {
                    if (listClient.get(i).idRoom == idRoom) {
                        sendToSpesificClient(stringToClient, i);
                    }
                }
            } else if (command[0].equals("get-players")) {
                // Mengirim data player dalam room ke semua client
                String stringToClient = "players "+listRoom.get(idRoom).countPlayers()+" ";
                for (int i = 0; i < listRoom.get(idRoom).countPlayers(); i++) {
                    stringToClient += listRoom.get(idRoom).getPlayer(i).getNickName()+" "+listRoom.get(idRoom).getPlayer(i).getWinNumber()+" "+listRoom.get(idRoom).getPlayer(i).getCharacter()+" ";
                }
                SendToClient(stringToClient);
            } else if (command[0].equals("join-room")) {
                // Mencari indeks room yang akan dijoin pada listRoom
                int id = 0;
                for (int i = 0; i < listRoom.size(); i++) {
                    if (command[1].equals(listRoom.get(i).getName())) {
                        id = i;
                        break;
                    }
                }

                if (command.length > 2) {
                    // Mengecek apakah character yang dipilih player sudah dipakai
                    boolean permit = true;
                    for (int i = 0; i < listRoom.get(id).countPlayers(); i++) {
                        if (command[3].equals(""+listRoom.get(id).getPlayer(i).getCharacter())) {
                            permit = false;
                            break;
                        }
                    }
                    if (permit) {
                        // Client bisa join room
                        String stringToClient;
                        idRoom = id;
                        listPlayer.get(idPlayer).setRoomName(idRoom);
                        listPlayer.get(idPlayer).setCharacter(Integer.parseInt(command[3]));
                        listRoom.get(idRoom).addPlayers(listPlayer.get(idPlayer));

                        // Mengirim nama room, nama masternya, dan id room
                        SendToClient("success join-room " + listRoom.get(idRoom).getName() + " " + listRoom.get(idRoom).getMaster().getNickName()+" "+idRoom + " " + command[3]);
                    } else {
                        SendToClient("fail-character join-room");
                    }
                } else {
                    // Mengecek apakah room masih bisa dimasuki dan jumlah pemain < MAX_PLAYER
                    if (listRoom.get(id).isOpen() && listRoom.get(id).countPlayers() < MAX_PLAYER) {
                        // Room bisa dimasuki
                        SendToClient("success-room join-room");
                    } else {
                        // Room tidak bisa dimasuki
                        SendToClient("fail-room join-room");
                    }
                }
            } else if (command[0].equals("start-game")) {
                if (!listRoom.get(idRoom).isGameStart() && listRoom.get(idRoom).getMaster().getNickName().equals(listPlayer.get(idPlayer).getNickName())) {
                    // Client adalah master, boleh menginisiasi game
                    if (listRoom.get(idRoom).countPlayers() >= 3) {
                        // Pemain sudah cukup untuk memulai game
                        listRoom.get(idRoom).isOpen(false);
                        listRoom.get(idRoom).isGameStart(true);
                        listRoom.get(idRoom).setTurn(listPlayer.get(idPlayer));
                        for (int i = 0; i < listClient.size(); i++) {
                            if (listClient.get(i).idRoom == idRoom) {
                                sendToSpesificClient("success start-game", i);
                                // Mengirim indeks player yang mendapat turn
                                sendToSpesificClient("turn "+"0", i);
                            }
                        }
                    } else {
                        // Pemain belum cukup untuk memulai game
                        listRoom.get(idRoom).isGameStart(false);
                        SendToClient("fail start-game");
                    }
                }
            } else if (command[0].equals("update-board")) {
                int turnIndex = 0;
                Point position = new Point();
                System.out.println("Turn--" + listPlayer.get(idPlayer).getNickName());
                if (listRoom.get(idRoom).turn().getNickName().equals(listPlayer.get(idPlayer).getNickName())) {
                    // Client boleh mengupdate board
                    position.setLocation(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    if (listRoom.get(idRoom).isGameStart() && listRoom.get(idRoom).getBoard().getBoardElement(position) == -1) {
                        // Posisi board boleh diisi
                        // Mencari indeks client pada list player di room
                        String stringToClient;

                        for (int i = 0; i < listRoom.get(idRoom).countPlayers(); i++) {
                            if (listRoom.get(idRoom).turn().getNickName().equals(listRoom.get(idRoom).getPlayer(i).getNickName())) {
                                turnIndex = i;
                                break;
                            }
                        }
                        listRoom.get(idRoom).getBoard().setBoardElement(position, listPlayer.get(idPlayer).getCharacter());

                        // Mengecek apakah client menang
                        if (listRoom.get(idRoom).getBoard().checkWinner(position) >= 0) {
                            // Client menang. Game berhenti
                            listRoom.get(idRoom).getPlayer(turnIndex).setWinNumber(listRoom.get(idRoom).getPlayer(turnIndex).getWinNumber()+1);
                            listPlayer.get(idPlayer).setWinNumber(listPlayer.get(idPlayer).getWinNumber()+1);
                            listRoom.get(idRoom).clearBoard();
                            listRoom.get(idRoom).isGameStart(false);

                            // Mengirim data pemenang ke semua client di room
                            for (int i = 0; i < listClient.size(); i++) {
                                if (listClient.get(i).idRoom == idRoom) {
                                    sendToSpesificClient("win-game "+listPlayer.get(idPlayer).getNickName()+" "+turnIndex, i);
                                }
                            }
                        } else {
                            // Client belum menang. Game masih berlanjut
                            listRoom.get(idRoom).isGameStart(true);

                            // Mengganti giliran pemain
                            if ((turnIndex+1) == listRoom.get(idRoom).countPlayers()) {
                                turnIndex = 0;
                            } else {
                                turnIndex++;
                            }
                            listRoom.get(idRoom).setTurn(listRoom.get(idRoom).getPlayer(turnIndex));

                            // Mengirim data giliran pemain selanjutnya ke semua client di room
                            for (int i = 0; i < listClient.size(); i++) {
                                if (listClient.get(i).idRoom == idRoom) {
                                    sendToSpesificClient("turn "+turnIndex, i);
                                }
                            }
                        }
                    }
                } else {
                    // Bukan giliran client untuk mengupdate board
                    SendToClient("fail update-board");
                }
            }else if(command[0].equals("get-highscore")){
                String str="highscore ";
                for(int i=0; i<listPlayer.size();i++){
                    str=str+listPlayer.get(i).getNickName()+" "+listPlayer.get(i).getWinNumber()+" ";
                }
                SendToClient(str);
            }else if (command[0].equals("")) {

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
                    if (!request.equals("get-board") && !request.equals("get-players"))
                        System.out.println(request);
                    if (request.equals("")) {
                        System.out.println("kosong");
                    }
                    Parse(request);
                }
            } catch (SocketException ex) {
                // Client disconnect
                if (idRoom >= 0) {
                    // Client sudah berada di suatu room
                    System.out.println(listPlayer.get(idPlayer).getNickName()+" disconnect");
                    // Menghentikan game
                    listRoom.get(idRoom).isGameStart(false);
                    // Mencari indeks client pada arraylist di room
                    int id = -1;
                    for (int i = 0; i < listRoom.get(idRoom).countPlayers(); i++) {
                        if (listRoom.get(idRoom).getPlayer(i).getNickName().equals(listPlayer.get(idPlayer).getNickName())) {
                            id = i;
                            break;
                        }
                    }
                    // Menghapus client dari arraylist di room
                    listRoom.get(idRoom).getPlayers().remove(id);
                    if (listRoom.get(idRoom).countPlayers() > 0) {
                        // Room masih ada player
                        // Mengeset ulang master
                        listRoom.get(idRoom).setMaster(listRoom.get(idRoom).getPlayer(0));
                        // Mengirim disconnect ke semua client di room
                        for (int i = 0; i < listClient.size(); i++) {
                            if (listClient.get(i).idRoom == idRoom) {
                                try {
                                    sendToSpesificClient("stop-game "+listPlayer.get(idPlayer).getNickName()+" "+id, i);
                                } catch (Exception ex1) {
                                    Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex1);
                                }
                            }
                        }
                    } else {
                        // Room kosong
                        listRoom.get(idRoom).isOpen(false);
                    }
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
