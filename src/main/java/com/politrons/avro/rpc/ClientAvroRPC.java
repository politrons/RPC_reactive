package com.politrons.avro.rpc;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.stream.LongStream;

import static finagle.BenchmarkUtils.requestNumber;


/**
 * Start a server, attach a client, and send a message.
 */
public class ClientAvroRPC {

    public static void run(int port) throws IOException {
        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(port));
        // client code - attach to the server and send a message
        CustomAvroRPC proxy = SpecificRequestor.getClient(CustomAvroRPC.class, client);
        LongStream.range(1, requestNumber()).forEach(index -> {
                    // fill in the Message record and send it
                    Message message = new Message();
                    message.setTo("Receiver");
                    message.setFrom("Sender");
                    message.setBody("Body message");
            try {
                CharSequence result = proxy.send(message);
//                System.out.println("Result: " + result);
            } catch (AvroRemoteException e) {
                e.printStackTrace();
            }
        });
        // cleanup
        client.close();
        ServerAvroRPC.server.close();
    }
}