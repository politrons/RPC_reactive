package com.politrons.grpc.reactive;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import rx.Observable;

import java.util.Arrays;

/**
 * Using StreamObserver we can use a communication stream to communicate client and server without close communication.
 * Having this stream open from the client and server side we can multiple communication until one of the side
 * decide to close the communication invoking onComplete.
 *
 * The invocation of the method streamCall it will send the Stream with the Response communication, and the Server
 * will response with the Stream of Request, then the communication can start using this channel,using the Request Stream
 * from client and Response stream from the server. Brilliant!!!
 */
public class ReactiveClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = getManagedChannel();
        ReactiveServiceGrpc.ReactiveServiceStub stub = ReactiveServiceGrpc.newStub(channel);

        runStream(createServerStream(stub));
        Thread.sleep(2000);
        runStream(createServerStream(stub));
        Thread.sleep(2000);
    }

    private static StreamObserver<ReactiveRequest> createServerStream(ReactiveServiceGrpc.ReactiveServiceStub stub) {
        return stub.myStreamChannel(new StreamObserver<ReactiveResponse>() {

            @Override
            public void onNext(ReactiveResponse value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream finish");
            }
        });
    }

    private static void runStream(StreamObserver<ReactiveRequest> serverStream) {
        Observable.from(Arrays.asList(getReactiveRequest("hello"),
                getReactiveRequest("reactive"),
                getReactiveRequest("gRPC"),
                getReactiveRequest("world")))
                .subscribe(serverStream::onNext,
                        serverStream::onError,
                        serverStream::onCompleted);
    }

    private static ReactiveRequest getReactiveRequest(String value) {
        return ReactiveRequest.newBuilder()
                .setAttr(value)
                .build();
    }

    /**
     * ManagedChannel is communication channel for the RPC
     */
    private static ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9991)
                .usePlaintext(true)
                .build();
    }

}
