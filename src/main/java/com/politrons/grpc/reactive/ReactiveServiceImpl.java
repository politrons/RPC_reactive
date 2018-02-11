package com.politrons.grpc.reactive;

import io.grpc.stub.StreamObserver;

/**
 * From the server side we will receive the entry stream which is the response type class [ReactiveResponse]
 * and we will return a new stream with the request type class [ReactiveRequest] that contains the response Stream.
 *
 * Now that client and server side share the [StreamObserver<ReactiveRequest>] the communication can start.
 * All client actions agains the callbacks [onNext][onError][onComplete] will use the response stream to response
 * the client and keep communication open, until one of the side decide to close the communication invoking onCompleted.
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