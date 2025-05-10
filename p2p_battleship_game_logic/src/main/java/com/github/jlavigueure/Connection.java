package com.github.jlavigueure;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Abstract Connection class represents a remote connection and gives basic
 * data members and methods to send and receive messages.
 */
public abstract class Connection {
    protected Socket socket;
    protected OutputStream out;
    protected InputStream in;

    /**
     * Constructor for the Connection class. Initialize all data members to null.
     */
    public Connection(){
        socket = null;
        out = null;
        in = null;
    }

    /**
     * Establishes a connection to a remote endpoint.
     * Subclasses must implement this method to define how the connection is established.
     * 
     * @throws IOException if an I/O error occurs while attempting to connect.
     */
    public abstract void connect() throws IOException;

    /**
     * Send a message through current connected socket.
     * @param message to send through socket.
     * @throws IOException if no current connection.
     */
    public void sendMessage(String message) throws IOException {
        if (socket == null || socket.isClosed()){
            throw new IOException("Socket is not connected or is closed.");
        }
        out.write((message + "\n").getBytes());
        out.flush();
    }

    /**
     * Receive a synchronous message through connected socket. 
     * @return the message received.
     * @throws IOException if no current connection.
     */
    public String receiveMessage() throws IOException {
        if (socket == null || socket.isClosed()){
            throw new IOException("Socket is not connected or is closed.");
        }
        byte[] buffer = new byte[1024];
        int bytesRead = in.read(buffer);
        if (bytesRead == -1) {
            throw new IOException("End of stream reached, client may have disconnected.");
        }
        return new String(buffer, 0, bytesRead).trim();
    }

    /**
     * Close current connected socket.
     * @throws IOException if no current connection or connection already closed.
     */
    public void close() throws IOException {
        if (socket == null || socket.isClosed()){
            throw new IOException("Socket is not connected or is closed.");
        }
        out.close();
        in.close();
        socket.close();
    }
}
