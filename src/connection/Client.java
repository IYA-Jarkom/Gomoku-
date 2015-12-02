package connection;

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
public class Client {

    public static void main(String args[]) {
        try {
         Socket skt = new Socket("localhost", 2000);
         BufferedReader in = new BufferedReader(new
            InputStreamReader(skt.getInputStream()));
         System.out.print("Received string: '");

         while (!in.ready()) {}
         System.out.println(in.readLine()); // Read one line and output it

         System.out.print("'\n");
         in.close();
      }
      catch(Exception e) {
         System.out.print("Whoops! It didn't work!\n");
      }
    }
}
