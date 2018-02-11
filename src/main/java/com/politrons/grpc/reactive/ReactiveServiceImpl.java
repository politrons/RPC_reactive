package com.politrons.grpc.reactive;

import io.grpc.stub.StreamObserver;

/**
 * From the server side we will get the entry stream which is the response and we will return a new stream with the request
 * to be used from the client to start the communication.
 */
public class ReactiveServiceImpl extends ReactiveServiceGrpc.ReactiveServiceImplBase {

    @Override
    public StreamObserver<ReactiveRequest> myStreamChannel(StreamObserver<ReactiveResponse> responseObserver) {
        return createReactiveRequestStreamObserver(responseObserver);
    }

    private StreamObserver<ReactiveRequest> createReactiveRequestStreamObserver(StreamObserver<ReactiveResponse> responseObserver) {
        return new StreamObserver<ReactiveRequest>() {

            @Override
            public void onNext(ReactiveRequest value) {
                System.out.println("value from client: " + value.getAttr());
                ReactiveResponse response = ReactiveResponse.newBuilder()
                        .setValue(value.getAttr().toUpperCase())
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}