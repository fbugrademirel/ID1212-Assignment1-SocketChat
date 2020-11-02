package com.company;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

public class ChatClient {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("localhost", 6666);

            ExecutorService service = Executors.newFixedThreadPool(2);
            service.submit(new ChatReceiver(clientSocket));
            service.submit(new ChatSender(clientSocket));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class ChatReceiver implements Runnable {

    private final Socket socket;

    public ChatReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            BufferedReader inData = new BufferedReader(reader);
            String text;
            while((text = inData.readLine()) != null) {
                System.out.println("Received:" + text);
            }
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ChatSender implements Runnable {

    private final Socket socket;

    public ChatSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader inData = new BufferedReader(reader);
            String text;
            System.out.println("Enter text to send: ");
            while((text = inData.readLine()) != null) {
                out.println(text);
                System.out.println("Enter text to send: ");
            }
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
