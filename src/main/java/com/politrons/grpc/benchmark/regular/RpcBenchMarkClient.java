package com.politrons.grpc.benchmark.regular;

import com.google.common.util.concurrent.ListenableFuture;
import com.politrons.grpc.benchmark.BenchmarkUtils;
import com.politrons.grpc.simple.RpcBenchmark;
import com.politrons.grpc.simple.RpcBenchmarkServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.politrons.grpc.benchmark.BenchmarkUtils.*;
import static com.politrons.grpc.simple.RpcBenchmark.RpcBenchMarkRequest;
import static com.politrons.grpc.simple.RpcBenchmark.RpcBenchMarkResponse;
import static com.politrons.grpc.simple.RpcBenchmarkServiceGrpc.RpcBenchmarkServiceFutureStub;
import static com.politrons.grpc.simple.RpcBenchmarkServiceGrpc.newFutureStub;

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
public class RpcBenchMarkClient {

    public static void run() {
        ManagedChannel channel = getManagedChannel();
        RpcBenchmarkServiceFutureStub stub = getRpcServiceStub(channel);
        LongStream.range(1, requestNumber).forEach(index -> {
            try {
                makeRequest(stub);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        });
        channel.shutdown();
    }

    private static void makeRequest(RpcBenchmarkServiceFutureStub stub) throws InterruptedException, ExecutionException, TimeoutException {
        ListenableFuture<RpcBenchMarkResponse> future = stub.benchmark(RpcBenchMarkRequest.newBuilder()
                .setAttr("hello world")
                .build());
        future.get(10, TimeUnit.SECONDS);
    }

    /**
     * From the contract of the proto we create the FutureStub. There´re other strategies as Sync communication.
     */
    private static RpcBenchmarkServiceFutureStub getRpcServiceStub(ManagedChannel channel) {
        return newFutureStub(channel);
    }

    /**
     * ManagedChannel is communication channel for the RPC
     */
    private static ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9998)
                .usePlaintext(true)
                .build();
    }
}