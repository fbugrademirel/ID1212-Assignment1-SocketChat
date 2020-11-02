package com.company;

import java.io.*;
import java.net.Socket;
public class ChatClient {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("localhost", 6666);
            PrintStream out = new PrintStream(clientSocket.getOutputStream());
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader inData = new BufferedReader(reader);
            String text;
            System.out.println("Enter text to send: ");
            while((text = inData.readLine()) != null) {
                out.println(text);
                System.out.println("Enter text to send: ");
            }
            clientSocket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
