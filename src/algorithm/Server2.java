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
 *
 * @author yoga
 */
public class Server2 {

    static public ServerSocket server;
    static public ArrayList<Room> listRoom = new ArrayList();
    static public ArrayList<ClientController> listClient= new ArrayList();
    static public int clientNumber = 0;

    // Kelas
    public static class ClientController
            extends Thread {

        public Socket socket;
        public int idClient;

        public ClientController(Socket clientSocket) {
            this.socket = clientSocket;
            idClient = clientNumber;
        }

        public void Parse(String req) {
            //isi prosesnya disini
        }

        void SendToServer(String msg) throws Exception {
            //create output stream attached to socket
            PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            //send msg to server
            outToServer.print(msg + '\n');
            outToServer.flush();
        }

        public void run() {
            try {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request;
                while ((request = inFromClient.readLine()) != null) {
                    Parse(request);
                }
            } catch (IOException ex) {
                Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendToSpesificCLient(String str,int x) throws Exception{
        //untuk kirim move dari 1 client ke semua client dalam room
        listClient.get(x).SendToServer(str);
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
            Thread t = new Thread(new ClientController(socket));
            clientNumber++;
            t.start();
        }
    }

}
