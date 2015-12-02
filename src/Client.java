import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yoga
 */
public class Client {

    static ArrayList<Room> listRoom;
    static Room room;
    static Player player;

    public static void main(String args[]) {
        try {
            Scanner scan = new Scanner(System.in);
            Socket clientSocket = new Socket("localhost", 2000);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            //SEND NAMA TO SERVER
            System.out.print("Enter Your Name: ");
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            String name=inFromUser.readLine();
            outToServer.writeBytes(name + "\n");
            player = new Player(name, 0, 0);

            System.out.println("You have been connected to server");
            //TERIMA LISTROOM DARI SERVER
            ObjectInputStream objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            listRoom = new ArrayList<Room>((ArrayList<Room>) objectFromServer.readObject());
            if (listRoom.isEmpty()) {
                System.out.println("There are no room available");
            } else {
                System.out.println("LIST of ROOM");
                for (int i = 0; i < listRoom.size(); i++) {
                    int ID = i + 1;
                    System.out.println(ID + ". " + listRoom.get(i).getName());
                }
            }
            //KASIH ROOM NUMER KE SERVER
            System.out.println("Input Room Number to join the room or input 0 if you want to create a new room");
            System.out.print("Input : ");
            int roomNumber = scan.nextInt() - 1;

            ObjectOutputStream objectToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            objectToServer.writeObject(roomNumber);

        } catch (Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }
    }
}
