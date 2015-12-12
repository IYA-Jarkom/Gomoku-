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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yoga
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
            Scanner scan = new Scanner(req);
            String command = scan.next();//ambil kata 1 1
            if (command.equals("create-room")) {
                listRoom.add(new Room(scan.next(), listPlayer.get(idPlayer)));
                listRoom.get(listRoom.size() - 1).addPlayers(listPlayer.get(idPlayer));
                idRoom = listRoom.size() - 1;
                SendToClient("success create-room");
            } else if (command.equals("add-user")) {
                idPlayer = listPlayer.size();
                listPlayer.add(new Player(scan.next(), 0, 0));

                SendToClient("success add-user");
            } else if (command.equals("get-room")) {
                String str = "List of Room \n";
                for (int i = 0; i < listRoom.size(); i++) {
                    str = str + i + ". " + listRoom.get(i).getName() + "\n";
                }
                SendToClient(str);
            } else if (command.equals("join-room")) {
                int id = Integer.parseInt(scan.next());
                listRoom.get(id).addPlayers(listPlayer.get(idPlayer));
            } else {

                SendToClient("unknown command");
            }
            //if lain lainnya
            //isi prosesnya disini
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

    public void sendToSpesificCLient(String str, int x) throws Exception {
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
