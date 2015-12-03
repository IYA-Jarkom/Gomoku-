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

            public SendListRoom(int id, Socket socket) {
                this.id = id;
                this.socket = socket;
            }

            public void run() {
                while (!Thread.currentThread().isInterrupted()) {

                    if (lockSendListRoom.get(id)) {
                        try {
                            System.out.println("send!");
                            ObjectOutputStream objectToClient = new ObjectOutputStream(socket.getOutputStream());
                            objectToClient.writeObject(listRoom);
                            lockSendListRoom.set(id, false);
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                System.out.println(name + " has been connected" +id);
                player = new Player(name, 0, 0);

                //KASIH LISTROOM KE CLIENT TERSEBUT
                objectToClient.writeObject(listRoom);
                lockSendListRoom.set(id, false);
                Thread sendListRoom = new Thread(new SendListRoom(id, socket));
                sendListRoom.start();

                //DAPET ROOM YANG DIINGINKAN USER
                int roomNumber;

                roomNumber = (Integer) objectFromClient.readObject();

                if (roomNumber >= 0) {
                    listRoom.get(roomNumber).addPlayers(player);
                    player.setRoomName(roomNumber);
                } else {
                    Room newRoom = new Room(player.getNickName(), player);
                    newRoom.addPlayers(player);
                    listRoom.add(newRoom);
                    player.setRoomName(listRoom.size() - 1);
                    System.out.println(listRoom.size());
                }

                sendListRoom.stop();
                for (int i = 0; i < lockSendListRoom.size(); i++) {
                    lockSendListRoom.set(i, true);
                    System.out.println(lockSendListRoom.get(i));
                }

                // User sudah berada di room
                do {
                    if (roomNumber < 0) {
                        boolean isGameStart = (boolean) objectFromClient.readObject();
                        if (isGameStart && (listRoom.get(player.getRoomID()).countPlayers() >= 3)) {
                            // Game boleh dimulai
                            objectToClient.writeObject(true);
                            listRoom.get(player.getRoomID()).setTurn(player);
                            listRoom.get(player.getRoomID()).isGameStart(true);
                        } else if (isGameStart && (listRoom.get(player.getRoomID()).countPlayers() < 3)) {
                            // Game belum boleh dimulai
                            objectToClient.writeObject(false);
                        }
                    }
                } while (!listRoom.get(player.getRoomID()).isGameStart());

                // Game dimulai
                int turnIndex = listRoom.get(player.getRoomID()).getPlayers().indexOf(player);
                Point position;
                while (listRoom.get(player.getRoomID()).isGameStart()) {
                    if (listRoom.get(player.getRoomID()).turn().equals(player)) {
                        // Giliran player
                        objectToClient.writeObject(true);
                        position = (Point) objectFromClient.readObject();
                        // Mengubah isi board
                        listRoom.get(player.getRoomID()).getBoard().setBoardElement(position, turnIndex);

                        // Mengganti giliran
                        if ((turnIndex + 1) >= listRoom.get(player.getRoomID()).countPlayers()) {
                            turnIndex = 0;
                        } else {
                            turnIndex++;
                        }
                        listRoom.get(player.getRoomID()).setTurn(listRoom.get(player.getRoomID()).getPlayer(turnIndex));
                    } else {
                        // Belum giliran player
                        objectToClient.writeObject(false);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        server = new ServerSocket(2000);
        while (true) {
            Socket serversocket = server.accept();
            Boolean bool = false;
            lockSendListRoom.add(bool);
            Thread t = new Thread(new ClientController(serversocket, clientNumber));
            clientNumber++;
            t.start();
        }
    }
}
