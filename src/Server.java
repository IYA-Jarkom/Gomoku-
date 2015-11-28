import java.io.*;
import java.net.*;
import java.lang.*;

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

    public static void main(String args[]) {
        String data = "Toobie ornaught toobie";
        try {
            
            ServerSocket srvr = new ServerSocket(2000);
            Socket skt[] = null;
            skt[0] = srvr.accept();
            System.out.print("Server has connected!\n");
            skt[1]= srvr.accept();
            
            System.out.print("Server has connected!\n");
            PrintWriter out = new PrintWriter(skt[0].getOutputStream(), true);
            System.out.print("Sending string: '" + data + "'\n");
            out.print(data);
            out.close();
            skt[0].close();
            
            skt[1].close();
            srvr.close();
        } catch (Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }
    }
}
