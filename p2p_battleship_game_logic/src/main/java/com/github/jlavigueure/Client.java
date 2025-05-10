
package com.github.jlavigueure;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Client class inherits from Connection and gives a way for
 * a client to connect to a predefined host and port number.
 */
public class Client extends Connection{
    private String host;
    private int port;

    /**
     * Constructor for client.
     * @param host IP address to connect on.
     * @param port number for host connection. 
     */
    public Client(String host, int port){
        super();
        this.host = host;
        this.port = port; 
    }

    /**
     * Connect to remote connection on host and port given in constructor. 
     * @throws IOException if fails to connect.
     */
    public void connect() throws IOException {
        socket = new Socket(host, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }
}

