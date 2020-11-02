package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainChatServer {

    private static final int port = 6666;

    public static void main(String[] args) {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newCachedThreadPool();
            ServerSocket ss = new ServerSocket(port);
            while(true) {
                Socket s = ss.accept();
                executorService.submit(new ChatServer(s));
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

    public ChatServer(Socket socket) {
        this.s = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inData = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            String text = "";
            while((text = inData.readLine()) != null) {
                System.out.println("Received: " + text);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
