package server;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import client.Client;

public class TestServer {
	
    @Test
    public void testSimplePayload() {
        byte[] emptyPayload = new byte[1001];

        // Using Mockito
        final Socket socket = mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

        Message text = new Message(emptyPayload) {
            protected Socket createSocket() {
                return socket;
            }
        };

        Assert.assertTrue("Message sent successfully", text.sendTo("localhost", "1234"));
        Assert.assertEquals("whatever you wanted to send".getBytes(), byteArrayOutputStream.toByteArray());
    }
}
