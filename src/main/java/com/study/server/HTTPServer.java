package com.study.server;

import java.io.*;
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
            try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                OutputStream out = socket.getOutputStream();
            ) {
                String firstLine = in.readLine();
                System.out.println("firstLine = " + firstLine);

                String headers;
                while(!(headers = in.readLine()).isEmpty()) {
                    System.out.println("headers = " + headers);
                }

                String body = "<html><body><h1>Hello, Java HTTP Server!</h1></body></html>";

                String response = """
                HTTP/1.1 200 OK
                Content-Type: text/html; charset=utf-8
                Content-Length: %d
    
                %s
                """.formatted(body.getBytes().length, body);

                out.write(response.getBytes());
                out.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
