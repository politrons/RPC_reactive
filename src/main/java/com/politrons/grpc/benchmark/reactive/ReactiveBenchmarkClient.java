package com.politrons.grpc.benchmark.reactive;

import com.politrons.grpc.reactive.ReactiveRequest;
import com.politrons.grpc.reactive.ReactiveResponse;
import com.politrons.grpc.reactive.ReactiveServiceGrpc;
import finagle.BenchmarkUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.stream.LongStream;

import static finagle.BenchmarkUtils.*;


/**
 * Using StreamObserver we can use a communication stream to communicate client and server without close communication.
 * Having this stream open from the client and server side we can multiple communication until one of the side
 * decide to close the communication invoking onComplete.
 * <p>
 * The invocation of the method streamCall it will send the Stream with the Response communication, and the Server
 * will response with the Stream of Request, then the communication can start using this channel,using the Request Stream
 * from client and Response stream from the server. Brilliant!!!
 */
public class ReactiveBenchmarkClient {

    static boolean streamFinish = false;

    public static void run(int port) throws InterruptedException {
        ManagedChannel channel = getManagedChannel(port);
        ReactiveServiceGrpc.ReactiveServiceStub stub = ReactiveServiceGrpc.newStub(channel);
        runStream(createServerStream(stub));
        while (!streamFinish) {
            Thread.sleep(1);
        }
    }

    private static StreamObserver<ReactiveRequest> createServerStream(ReactiveServiceGrpc.ReactiveServiceStub stub) {
        return stub.myStreamChannel(new StreamObserver<ReactiveResponse>() {

            @Override
            public void onNext(ReactiveResponse value) {
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }

            @Override
            public void onCompleted() {
                streamFinish = true;
            }
        });
    }

    private static void runStream(StreamObserver<ReactiveRequest> serverStream) {
        LongStream.range(0, requestNumber())
                .forEach(index -> serverStream.onNext(getReactiveRequest(getPayload())));
        serverStream.onCompleted();
    }

    private static ReactiveRequest getReactiveRequest(String value) {
        return ReactiveRequest.newBuilder()
                .setAttr(value)
                .build();
    }

    /**
     * ManagedChannel is communication channel for the RPC
     */
    private static ManagedChannel getManagedChannel(int port) {
        return ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext(true)
                .build();
    }

}
