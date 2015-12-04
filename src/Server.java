import java.awt.Point;
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
 * TUGAS BESAR 2 IF3130 Jaringan Komputer -RETURN OF POI-
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Server {

    // Atribut
    static public ArrayList<Boolean> lockSendListRoom = new ArrayList();
    static public ArrayList<Boolean> lockPlay = new ArrayList();
    static public ServerSocket server;
    static public ArrayList<Room> listRoom = new ArrayList();
    static public int clientNumber = 0;

    // Kelas
    private static class ClientController
            extends Thread {

        public Socket socket;
        public Player player;
        public int id;
        public ObjectInputStream objectFromClient;
        public ObjectOutputStream objectToClient;

        private static class SendListRoom
                extends Thread {

            public int id;
            public Socket socket;
            public ObjectOutputStream oos;

            public SendListRoom(int id, Socket socket,ObjectOutputStream _oos) {
                this.id = id;
                this.socket = socket;
                oos=_oos;
            }

            public void run() {
                while (!Thread.currentThread().isInterrupted()) {

                    while(!lockSendListRoom.get(id)) ;
                        try {
                            oos.writeObject(listRoom);
                            lockSendListRoom.set(id, false);
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                }
            }
        }

        public ClientController(Socket clientSocket, int ID) {
            this.socket = clientSocket;
            id = ID;
        }

        public Player getPlayer() {
            return this.player;
        }

        public void run() {
            try {
                objectToClient = new ObjectOutputStream(socket.getOutputStream());

                //TUNGGU NAMA DARI CLIENT
                objectFromClient = new ObjectInputStream(socket.getInputStream());
                String name = (String) objectFromClient.readObject();
                System.out.println(name + " has been connected");
                player = new Player(name, 0, 0);
                player.setClientName(id);
                //KASIH LISTROOM KE CLIENT TERSEBUT
                objectToClient.writeObject(listRoom);
                objectToClient.reset();
                lockSendListRoom.set(id, false);
//                Thread sendListRoom = new Thread(new SendListRoom(id, socket,objectToClient));
//                sendListRoom.start();

                //DAPET ROOM YANG DIINGINKAN USER
                int roomNumber;

                roomNumber = (Integer) objectFromClient.readObject();
             

                if (roomNumber >= 0) {
                    listRoom.get(roomNumber).addPlayers(player);
                    player.setRoomName(roomNumber);
                } else {
                    Room newRoom = new Room((String)objectFromClient.readObject(), player);
                    newRoom.addPlayers(player);
                    listRoom.add(newRoom);
                    player.setRoomName(listRoom.size() - 1);
                }
                
                for (int i = 0; i < lockSendListRoom.size(); i++) {
                    lockSendListRoom.set(i, true);
                }
//                sendListRoom.stop();
                objectToClient.reset();
                objectToClient.writeObject(listRoom);
                
                // Mengirim data Player dan Room yang ditempati Player, ke client
                objectToClient.writeObject(player.getRoomID());
                
                // User sudah berada di room
                listRoom.get(player.getRoomID()).isGameStart(false);
                do {
                    if (roomNumber < 0) {
                        boolean isGameStart = (boolean) objectFromClient.readObject();
                        if (isGameStart && (listRoom.get(player.getRoomID()).countPlayers() >= 1)) {
                            // Game boleh dimulai
                            objectToClient.writeObject(true);
                            
//                            ArrayList<Player> listRoomPlayer = new ArrayList((ArrayList<Player>) objectFromClient.readObject());
//                            for (int j = 0; j < listRoomPlayer.size(); j++) {
//                                lockPlay.set(listRoomPlayer.get(j).getClientID(), true);
//                            }
                            listRoom.get(player.getRoomID()).setTurn(player);
                            listRoom.get(player.getRoomID()).isGameStart(true);
                        } else if (isGameStart && (listRoom.get(player.getRoomID()).countPlayers() < 1)) {
                            // Game belum boleh dimulai
                            objectToClient.writeObject(false);
                        }
                    } //jika bukan master
                    else {
                        //while (lockPlay.get(id) == false);
                        while (!listRoom.get(player.getRoomID()).isGameStart()) {
                            System.out.print("");
                        }
                        objectToClient.writeObject(true);
                        //lockPlay.set(id, false);
                    }
                } while (!listRoom.get(player.getRoomID()).isGameStart());

                // Game dimulai
                while (listRoom.get(player.getRoomID()).isGameStart()) {
                    if (listRoom.get(player.getRoomID()).turn().equals(player)) {
                        // Giliran player
                        objectToClient.writeObject(true);
                        // Menerima posisi board dari client
                        Point position = (Point) objectFromClient.readObject();
                        if (listRoom.get(player.getRoomID()).getBoard().getBoardElement(position) == -1) {
                            // Mengubah isi board
                            listRoom.get(player.getRoomID()).getBoard().setBoardElement(position, listRoom.get(player.getRoomID()).getPlayers().indexOf(listRoom.get(player.getRoomID()).turn()));
                            objectToClient.writeObject(true);
                            
                            // Memeriksa board apakah player menang
                            if (listRoom.get(player.getRoomID()).getBoard().checkWinner(position) != -1) {
                                objectToClient.writeObject(true);
                                listRoom.get(player.getRoomID()).isGameStart(false);
                            } else {
                                objectToClient.writeObject(false);
                            }
                            
                            // Mengganti giliran
                            if ((listRoom.get(player.getRoomID()).getPlayers().indexOf(listRoom.get(player.getRoomID()).turn()) + 1) < listRoom.get(player.getRoomID()).countPlayers()) {
                                listRoom.get(player.getRoomID()).setTurn(listRoom.get(player.getRoomID()).getPlayer(listRoom.get(player.getRoomID()).getPlayers().indexOf(listRoom.get(player.getRoomID()).turn()) + 1));
                            } else {
                                listRoom.get(player.getRoomID()).setTurn(listRoom.get(player.getRoomID()).getPlayer(0));
                            }
                        } else {
                            objectToClient.writeObject(false);
                        }
                    } else {
                        // Belum giliran player
                        objectToClient.writeObject(false);
                    }
                    objectToClient.writeObject(listRoom.get(player.getRoomID()).isGameStart());
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        InetAddress ip;
        String hostname;
        try{
            ip=InetAddress.getLocalHost();
            hostname= ip.getHostName();
            System.out.println("Server IP address : "+ip);
            System.out.println("Server IP hostname : "+hostname);
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        server = new ServerSocket(2000);
        while (true) {
            Socket serversocket = server.accept();
            Boolean bool = false;
            lockSendListRoom.add(bool);
            lockPlay.add(bool);
            Thread t = new Thread(new ClientController(serversocket, clientNumber));
            clientNumber++;
            t.start();
        }
    }
}
