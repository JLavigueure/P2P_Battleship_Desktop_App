package com.github.jlavigueure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServerTest {

    @Test
    public void testClientServerCommunication() throws Exception {
        int testPort = 5060;
        final String[] message = new String[2];
        final Exception[] exception = new Exception[2];

        Thread serverThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Server server = new Server(testPort, testPort);
                    server.connect();
                    message[0] = server.receiveMessage();
                    server.sendMessage("pong");
                    server.close();
                } catch (Exception e) {
                    exception[0] = e;
                }
            }
        });
        serverThread.start();
        Thread.sleep(200); // Wait briefly for server to start

        try{
            Client client = new Client("localhost", testPort);
            client.connect();
            client.sendMessage("ping");
            message[1] = client.receiveMessage();
            client.close();
        }catch(Exception e){
            exception[1] = e;
        }

        serverThread.join(2000); // wait up to 2s for server thread to finish

        if (exception[0] != null) {
            fail("Server threw an exception: " + exception[0].getMessage());
        } else if(exception[1] != null) {
            fail("Client threw an exception: " + exception[1].getMessage());
        }

        assertEquals("ping", message[0]);
        assertEquals("pong", message[1]);
    }
}