package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            Socket s;
            String text;
            while((s = serverSocket.accept()) != null) {
                BufferedReader inData = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while((text = inData.readLine()) != null) {
                    System.out.println("Received: " + text);
                }
            }
            s.shutdownInput();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
