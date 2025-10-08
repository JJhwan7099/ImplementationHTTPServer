package com.study.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

    private static final int port = 7099;

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                System.out.println("Connection successful");
                RequestHandler requestHandler = new RequestHandler(socket);
                Thread thread = new Thread(requestHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class RequestHandler implements Runnable {
        private final Socket socket;
        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

        }
    }
}
