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
                listRoom.add(new Room(command[1], listPlayer.get(idPlayer)));
                listRoom.get(listRoom.size() - 1).addPlayers(listPlayer.get(idPlayer));
                idRoom = listRoom.size() - 1;
                SendToClient("success create-room");
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

                    SendToClient("success add-user");
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
                listRoom.get(id).addPlayers(listPlayer.get(idPlayer));

            } else if (command[0].equals("enter")) {
                String stringToClient;
                Point position = new Point();

                System.out.println(command[2] + " masuk room " + listRoom.get(Integer.parseInt(command[1])).getName());

                // Menambahkan player ke dalam list player di room
                listRoom.get(Integer.parseInt(command[1])).addPlayers(new Player(command[2], 0, 0));

                // Mengirim nama room dan jumlah player dalam room
                SendToClient("room " + listRoom.get(Integer.parseInt(command[1])).getName() + " " + listRoom.get(Integer.parseInt(command[1])).countPlayers());

                // Mengirim data player dalam room
                for (int i = 0; i < listRoom.get(Integer.parseInt(command[1])).countPlayers(); i++) {
                    SendToClient(listRoom.get(Integer.parseInt(command[1])).getPlayer(i).getNickName() + " " + listRoom.get(Integer.parseInt(command[1])).getPlayer(i).getWinNumber() + " " + listRoom.get(Integer.parseInt(command[1])).getPlayer(i).getLoseNumber());
                }

                // Mengirim isi board di room
                for (int i = 0; i < 20; i++) {
                    stringToClient = "";
                    for (int j = 0; j < 20; j++) {
                        position.setLocation(i, j);
                        stringToClient += listRoom.get(Integer.parseInt(command[1])).getBoard().getBoardElement(position);
                        stringToClient += " ";
                    }
                    SendToClient(stringToClient);
                }
            } else if (command[0].equals("start")) {
                // Jika jumlah pemain >= 3, maka game boleh dimulai
                if (listRoom.get(Integer.parseInt(command[1])).countPlayers() >= 3) {
                    SendToClient("yes");
                } else {
                    SendToClient("no");
                }
            } else if (command[0].equals("play")) {
                // Giliran pertama adalah room master, selanjutnya mengikuti arraylist
                listRoom.get(Integer.parseInt(command[1])).setTurn(listRoom.get(Integer.parseInt(command[1])).getMaster());
                SendToClient("turn " + listRoom.get(Integer.parseInt(command[1])).turn().getNickName());

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

    public void sendToSpesificClient(String str, int x) throws Exception {
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
