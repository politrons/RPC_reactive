package com.politrons.grpc.simple;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * After we create the classes through the contract that we deifne(rcp_contract.proto) we can use already the
 * RpcServiceGrpc which is used to create a stub in the client, to communicate with server as a remote procedure call.
 * In order to create the stub we need first a channel, which has been created through the ManagedChannelBuilder.
 * <p>
 * Once that you have the stub, you can decide which strategy do you want for the communication, creating:
 * <p>
 * newBlockingStub --> Return a RpcServiceBlockingStub which can be used to invoke the remote method synchronously
 * newFutureStub   --> Return a RpcServiceFutureStub which can be used to invoke the remote method asynchronously returning a ListenableFuture
 * newStub         --> Return a RpcServiceFutureStub which can be used to invoke the remote method asynchronously returning a Stub
 */
public class RpcClient {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ManagedChannel channel = getManagedChannel();
        RpcServiceGrpc.RpcServiceFutureStub stub = getRpcServiceStub(channel);

        //Create custom header
        Metadata header=new Metadata();
        Metadata.Key<String> key =
                Metadata.Key.of("Custom_header", Metadata.ASCII_STRING_MARSHALLER);
        header.put(key, "This is working!!");


        stub = MetadataUtils.attachHeaders(stub, header);
        //

        ListenableFuture<RpcContract.RpcResponse> future = stub.connect(RpcContract.RpcRequest.newBuilder()
                                                                .setAttr("hello world")
                                                                .build());
        RpcContract.RpcResponse response = future.get(10, TimeUnit.SECONDS);
        System.out.println(response.getValue());
        channel.shutdown();
    }

    /**
     * From the contract of the proto we create the FutureStub. There´re other strategies as Sync communication.
     */
    private static RpcServiceGrpc.RpcServiceFutureStub getRpcServiceStub(ManagedChannel channel) {
        return RpcServiceGrpc.newFutureStub(channel);
    }

    /**
     * ManagedChannel is communication channel for the RPC
     */
    private static ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9999)
                .usePlaintext(true)
                .build();
    }
}