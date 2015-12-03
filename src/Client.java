import java.awt.Point;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Client {

    static public ArrayList<Room> listRoom;
    static public Room room;
    static public Player player;
    static public Socket clientSocket;
    static public ObjectInputStream objectFromServer;
    static public ObjectOutputStream objectToServer;
    
    private static class RecListRoom
            extends Thread {

        public RecListRoom() {

        }

        public void run() {
            while (true) {
                try {
                    System.out.println("wait room list");
                    ArrayList<Room> newListRoom = new ArrayList<Room>((ArrayList<Room>) objectFromServer.readObject());
                    System.out.println("receiver room list");
                    System.out.println(newListRoom.size());
                    System.out.println(listRoom.size());
                    if (newListRoom.size() != listRoom.size()) {
                        for (int i = listRoom.size(); i < newListRoom.size(); i++) {
                            int ID = i + 1;
                            System.out.println(ID + ". " + newListRoom.get(i).getName());
                        }
                    }
                    listRoom = new ArrayList<Room>(newListRoom);
                } catch (IOException ex) {
                    System.out.println("1");
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    System.out.println("2");
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String args[]) {
        try {
            Scanner scan = new Scanner(System.in);
            Socket clientSocket = new Socket("localhost", 2000);

            //SEND NAMA TO SERVER
            objectToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.print("Enter Your Name: ");
            String name = scan.nextLine();
            objectToServer.writeObject(name);
            player = new Player(name, 0, 0);

            System.out.println("You have been connected to server");
            
            //TERIMA LISTROOM DARI SERVER
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            
            int roomNumber;
            do {
                System.out.println("asdasdas");
                listRoom = new ArrayList<Room>((ArrayList<Room>) objectFromServer.readObject());
                System.out.println("Input Room Number to join the room or input 0 if you want to create a new room & 101 to refresh");
                System.out.println("LIST of ROOM");
                for (int i = 0; i < listRoom.size(); i++) {
                    int idroom = i + 1;
                    System.out.println(idroom + ". " + listRoom.get(i).getName());
                }
                 // Thread recListRoom = new Thread(new RecListRoom());
                //recListRoom.start();
                //KASIH ROOM NUMER KE SERVER   
                roomNumber = scan.nextInt() - 1;
                objectToServer.writeObject(roomNumber);
            } while (roomNumber == 100);
            //recListRoom.stop();
            //System.out.print("asasasasad");

            // User masuk ke room
            boolean isGameStart = false;
            do {
                if (roomNumber < 0) {  // Player adalah master di room
                    System.out.println("1 to start the game");
                    System.out.println("2 to exit the room");
                    System.out.print("Input: ");
                    int roomMenu = scan.nextInt();

                    if (roomMenu == 1) {
                        objectToServer.writeObject(true);
                        isGameStart = (boolean) objectFromServer.readObject();
                        if (isGameStart) {
                            System.out.println("Game Start");
                        }
                    }
                } else {    // Player bukan master di room
                    System.out.println("Wait for the Master to start the game");
                    isGameStart = (boolean) objectFromServer.readObject();
                }
            } while (!isGameStart);

            if (isGameStart) {
                // Game dimulai
                boolean isPlayerTurn = false;
                int x, y;
                Point position = new Point();

                do {
                    isPlayerTurn = (boolean) objectFromServer.readObject();
                    if (isPlayerTurn) {
                        // Player memilih posisi board diinginkan
                        System.out.println("Insert board position");
                        System.out.print("x : ");
                        x = scan.nextInt();
                        System.out.print("y : ");
                        y = scan.nextInt();
                        position.setLocation(x, y);

                        if (room.getBoard().getBoardElement(position) != -1) {  // Posisi board belum terisi
                            // Mengirim posisi board yang dipilih player ke server
                            objectToServer.writeObject(position);
                            isPlayerTurn = false;
                        } else {
                            System.out.println("Posisi board sudah terisi. Pilih posisi lain");
                        }
                    }
                    isGameStart = (boolean) objectFromServer.readObject();
                } while (isGameStart);
            }
        } catch (Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }
    }
}