package com.github.jlavigueure;

import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Server class inherits from Connection and gives a way to start a server
 * on a predefined range of ports, and wait for a single connection.
 */
public class Server extends Connection{
    private int[] ports;

    /**
     * Constructor for Server class. Defines range of ports to use. 
     * @param minPort is the inclusive minimum port value.
     * @param maxPort is the inclusive maxiumum port value.
     */
    public Server(int minPort, int maxPort){
        super();
        ports = new int[maxPort - minPort + 1];
        for(int i = 0; i < ports.length; i++){
            ports[i] = minPort + i;
        }
    }

    /**
     * Starts a server on first available port in range and waits for single connection.
     * @throws IOException if failed to start server on all defined ports.
     */
    public void connect() throws IOException{
        for(int i = 0; i < ports.length; i++){
            socket = connect(ports[i]);
            if (socket != null) {
                out = socket.getOutputStream();
                in = socket.getInputStream();
                return;
            }
        }
        throw new IOException("Failed to start server.");
    }

    /**
     * Helper method for connect which atttmempts to start server on a single given port
     * and waits for a single connection.
     * @param port number to start server on.
     * @return socket with connection to client or null if unable to start server.
     */
    private Socket connect(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server running on IP: " + serverSocket.getInetAddress().getHostAddress() + ", Port: " + serverSocket.getLocalPort());
            System.out.println("Server started. Waiting for a connection...");
            socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());
            serverSocket.close();
        }catch(IOException e){
            return null;
        }
        return socket;
    }
}
