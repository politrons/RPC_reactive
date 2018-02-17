Author Pablo Pérez García 

![My image](src/main/resources/img/simple.svg)

## Reactive gRPC

Here we cover with some examples and explanations how [gRPC](https://grpc.io/docs/quickstart/) works.

#### Simple gRCP

![My image](src/main/resources/img/grpc.png)

An example of how gRPC works between client-server

* [client](src/main/java/com/politrons/grpc/simple/RpcClient.java)

* [Service](src/main/java/com/politrons/grpc/simple/RpcServiceImpl.java)

* [proto](src/main/proto/rpc_contract.proto)

#### Reactive 

![My image](src/main/resources/img/flatMap.png)

An example of how to use streams gRPC between client-server

* [client](src/main/java/com/politrons/grpc/reactive/ReactiveClient.java)

* [service](src/main/java/com/politrons/grpc/reactive/ReactiveServiceImpl.java)

* [proto](src/main/proto/rpc_reactive.proto)


#### Benchmarks

![My image](src/main/resources/img/benchmark.png)

For this benchmark we made 10k request and calc the response time

* [Rest](src/main/scala/benchmark) Http finagle client against Grizzly server.

* [gRPC](src/main/java/com/politrons/grpc/benchmark/regular) using standard implementation.

* [gRPC Reactive](src/main/java/com/politrons/grpc/benchmark/reactive) using reactive ObservableStream.

##### Results

```.bash
Rest Grizzly response time:17207 millis
RPC regular response time:8639 millis
RPC Reactive response time:1331 millis
```

#### Configuration

Once that you have your contracts(proto) ready, you need to build your classes which will 
be used for the communication between client and server.
In these examples we decide to use the maven plugin.

The plugin you need to add in your pom is

```
  <plugin>
         <groupId>org.xolstice.maven.plugins</groupId>
         <artifactId>protobuf-maven-plugin</artifactId>
         <version>0.5.0</version>
         <configuration>
              <protocArtifact>
                        com.google.protobuf:protoc:3.3.0:exe:${os.detected.classifier}
              </protocArtifact>
              <pluginId>grpc-java</pluginId>
              <pluginArtifact>
                        io.grpc:protoc-gen-grpc-java:1.4.0:exe:${os.detected.classifier}
              </pluginArtifact>
              </configuration>
              <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>compile-custom</goal>
                        </goals>
                    </execution>
              </executions>
  </plugin>


```