package com.politrons.avro.rpc;

import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.util.Utf8;



/**
 * Start a server, attach a client, and send a message.
 */
public class ServerAvroRPC {

    public static class CustomAvroRPCImpl implements CustomAvroRPC {
        // in this simple example just return details of the message
        public Utf8 send(Message message) {
            return new Utf8("Sending message to " + message.getTo().toString()
                    + " from " + message.getFrom().toString()
                    + " with body " + message.getBody().toString());
        }
    }

    public static Server server;

    public static void start(int port) {
        server = new NettyServer(new SpecificResponder(CustomAvroRPC.class, new CustomAvroRPCImpl()), new InetSocketAddress(port));
        // the server implements the CustomAvroRPC protocol (CustomAvroRPCImpl)
    }

}