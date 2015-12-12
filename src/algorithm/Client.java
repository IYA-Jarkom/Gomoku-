package algorithm;

import java.awt.*;
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
 * TUGAS BESAR 2 IF3130 Jaringan Komputer -RETURN OF POI-
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Client {

    static public ArrayList<Room> listRoom;
    static public Socket clientSocket;
    static public ObjectInputStream objectFromServer;
    static public ObjectOutputStream objectToServer;
    static public int clientRoom;

    public static class RecListRoom
            extends Thread {

        public RecListRoom() {

        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ArrayList<Room> newListRoom = new ArrayList<>((ArrayList<Room>) objectFromServer.readObject());
                    if (newListRoom.size() != listRoom.size()) {
                        for (int i = listRoom.size(); i < newListRoom.size(); i++) {
                            int ID = i + 1;
                            System.out.println(ID + ". " + newListRoom.get(i).getName());
                        }
                    }
                    listRoom = new ArrayList<Room>(newListRoom);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Input server IP hostname : ");
            String host = scan.nextLine();
            clientSocket = new Socket(host, 2000);

            //SEND NAMA TO SERVER
            objectToServer = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.print(
                    "Enter Your Name: ");
            String name = scan.nextLine();

            objectToServer.writeObject(name);

            System.out.println(
                    "You have been connected to server");

            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            //TERIMA LISTROOM DARI SERVER
            int roomNumber;

            listRoom = new ArrayList<Room>((ArrayList<Room>) objectFromServer.readObject());
            System.out.println("Input Room Number to join the room or input 0 if you want to create a new room ");
            System.out.println("LIST of ROOM");
            for (int i = 0; i < listRoom.size(); i++) {
                int idroom = i + 1;
                System.out.println(idroom + ". " + listRoom.get(i).getName());
            }
            Thread recListRoom = new Thread(new RecListRoom());
            recListRoom.start();
            //System.out.println(recListRoom.getState());
            //System.out.println(recListRoom.isAlive());
            //KASIH ROOM NUMER KE SERVER
            roomNumber = Integer.parseInt(scan.nextLine()) - 1;
            objectToServer.writeObject(roomNumber);
            if (roomNumber < 0) {
                System.out.print("Input nama room : ");
                objectToServer.writeObject(scan.nextLine());
            }

            recListRoom.stop();
            // User masuk ke room
            ArrayList<Room> newListRoom = new ArrayList((ArrayList<Room>) objectFromServer.readObject());
            listRoom = new ArrayList<Room>(newListRoom);
            boolean isGameStart = false;

            // Menerima data room yang ditempati client
            clientRoom = (Integer) objectFromServer.readObject();

            while (true) {
                do {
                    if (roomNumber < 0) {  // Player adalah master di room
                        System.out.println("1 to start the game");
                        System.out.println("Other number to exit the room");
                        System.out.print("Input: ");
                        int roomMenu = Integer.parseInt(scan.nextLine());
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
                        System.out.println("Game Start");
                    }
                } while (!isGameStart);

                if (isGameStart) {
                    // Game dimulai
                    boolean isPlayerTurn = false;
                    int x, y;

                    do {
                        isPlayerTurn = objectFromServer.readBoolean();
                        System.out.println(isPlayerTurn);
                        if (isPlayerTurn) {
                            // Player memilih posisi board diinginkan
                            System.out.println("Insert board position");
                            System.out.print("x : ");
                            x = Integer.parseInt(scan.nextLine());
                            System.out.print("y : ");
                            y = Integer.parseInt(scan.nextLine());
                            Point position = new Point(x, y);

                            // Mengirim posisi board ke server
                            objectToServer.writeObject(position);
                            if ((boolean) objectFromServer.readObject()) {  // Posisi board belum terisi
                                System.out.println("Pengisian board berhasil");

                                // Mengecek apakah client menang
                                if ((boolean) objectFromServer.readObject()) {
                                    System.out.println("You win");
                                }

                                isPlayerTurn = false;
                            } else {
                                System.out.println("Posisi board sudah terisi. Pilih posisi lain");
                            }
                        }
                        isGameStart = (boolean) objectFromServer.readObject();
                    } while (isGameStart);
                    System.out.println("Game selesai");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
