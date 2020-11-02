package com.company;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class MainChatServer {

    private static final int port = 6666;
    private final static ArrayList<Socket> clientList = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newCachedThreadPool();
            ServerSocket ss = new ServerSocket(port);
            while(true) {
                System.out.println("Server running...");
                Socket s = ss.accept();
                executorService.submit(new ChatServer(s, clientList));
                clientList.add(s);
                System.out.println("Current clients:\n" + clientList.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (Thread.activeCount() != 0) {
                if(executorService != null) {
                    executorService.shutdown();
                }
            }
        }
    }
}

class ChatServer implements Runnable {

    private final Socket s;
    private final ArrayList<Socket> clientList;

    public ChatServer(Socket socket, ArrayList<Socket> clientList) {
        this.s = socket;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        try {
            BufferedReader inData = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String text;
            /**
             * When you read from BufferedReader, a remotely closed socket will return null.
             */
            while((text = inData.readLine()) != null) {
                System.out.println("Forwarded to all clients: " + text);
                for (Socket s : clientList) {
                    if(s != this.s) {
                        PrintStream out = new PrintStream(s.getOutputStream());
                        out.println(text);
                    }
                }
            }
            clientList.remove(s);
            s.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
