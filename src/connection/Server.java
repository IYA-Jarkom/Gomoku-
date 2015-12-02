package connection;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yoga
 */
public class Server {
 static ArrayList<Socket> skt=new ArrayList();
    public static void main(String args[]) {
        String data = "Toobie ornaught toobie";
        try {
            
            ServerSocket srvr = new ServerSocket(2000);
            
            while(true){
                Socket sk=srvr.accept();
                skt.add(sk);
                System.out.println("asdasdasdasdsa");
            } 
          
        } catch (Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }
    }
}
