package com.politrons.avro.rpc;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.politrons.avro.rpc.ServerAvroRPC.startServer;


/**
 * Start a server, attach a client, and send a message.
 */
public class ClientAvroRPC {

    public static void main(String[] args) throws IOException {
        // usually this would be another app, but for simplicity
        startServer();

        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(65111));
        // client code - attach to the server and send a message
        CustomAvroRPC proxy = SpecificRequestor.getClient(CustomAvroRPC.class, client);
        System.out.println("Client built, got proxy");

        // fill in the Message record and send it
        Message message = new Message();
        message.setTo("Receiver");
        message.setFrom("Sender");
        message.setBody("Body message");
        System.out.println("Calling proxy.send with message:  " + message.toString());
        System.out.println("Result: " + proxy.send(message));

        // cleanup
        client.close();
        ServerAvroRPC.server.close();
    }
}