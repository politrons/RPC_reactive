Author Pablo Pérez García 

![My image](src/main/resources/img/simple.svg)

## Reactive RPC

Here we cover with some examples and explanations how most famous RPC as [gRPC](https://grpc.io/docs/quickstart/) or
 [Thrift](https://thrift.apache.org/) works.

### gRPC

##### Simple gRCP

![My image](src/main/resources/img/grpc.png)

An example of how gRPC works between client-server

* [client](src/main/java/com/politrons/grpc/simple/RpcClient.java)

* [Service](src/main/java/com/politrons/grpc/simple/RpcServiceImpl.java)

* [proto](src/main/proto/rpc_contract.proto)

##### Reactive

![My image](src/main/resources/img/flatMap.png)

An example of how to use streams gRPC between client-server

* [client](src/main/java/com/politrons/grpc/reactive/ReactiveClient.java)

* [service](src/main/java/com/politrons/grpc/reactive/ReactiveServiceImpl.java)

* [proto](src/main/proto/rpc_reactive.proto)

##### Configuration

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

### Thrift

![My image](src/main/resources/img/apache.png)

An example of how thrift RPC works between client-server

* [client](src/main/scala/finagle/thrift/rpc/ThriftRPCClient.scala)

* [Service](src/main/scala/finagle/thrift/rpc/ThriftRPCServer.scala)

* [thrift](src/main/scala/finagle/thrift/idl/finagle_scrooge.thrift)

##### Configuration

Just like with gRPC once that you have your contracts(thrift) ready, you need to build your classes which will
be used for the communication between client and server.
In these examples we decide to use the twitter scrooge maven plugin.

The plugin you need to add in your pom is

```
          <plugin>
                <groupId>com.twitter</groupId>
                <artifactId>scrooge-maven-plugin</artifactId>
                <version>18.2.0</version>
                <configuration>
                    <thriftSourceRoot>src/main/scala/finagle/thrift/idl/</thriftSourceRoot>
                    <thriftNamespaceMappings>
                        <thriftNamespaceMapping>
                            <from>finagle.thrift.idl</from>
                            <to>finagle.thrift</to>
                        </thriftNamespaceMapping>
                    </thriftNamespaceMappings>
                    <language>scala</language> <!-- default is scala -->
                    <thriftOpts>
                        <!-- add other Scrooge command line options using thriftOpts -->
                        <thriftOpt>--finagle</thriftOpt>
                    </thriftOpts>
                    <!-- tell scrooge to not to build the extracted thrift files (defaults to true) -->
                    <buildExtractedThrift>false</buildExtractedThrift>
                </configuration>
                <executions>
                    <execution>
                        <id>thrift-sources</id>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>thrift-test-sources</id>
                        <phase>generate-test-sources</phase>
                        <goals>
                            <goal>testCompile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>


```

### Avro

![My image](src/main/resources/img/avro.png)

An example of how avro encoder/decoder works between client-server

* [encoder](src/main/java/com/politrons/avro/SerializeAvro.java)

* [decoder](src/main/java/com/politrons/avro/DeserializeAvro.java)

* [avro](src/main/avro/person.avsc)

An example of how avro RPC works between client-server

* [client](src/main/java/com/politrons/avro/rpc/ClientAvroRPC.java)

* [Service](src/main/java/com/politrons/avro/rpc/ServerAvroRPC.java)

* [avro](src/main/avro/avro_rpc.avpr)

##### Configuration

Just like with gRPC once that you have your contracts(avro) ready, you need to build your classes which will
be used for the communication between client and server.
In these examples we use avro-maven-plugin<.

The plugin you need to add in your pom is

```
           <plugin>
                <groupId>org.apache.avro</groupId>
                <artifactId>avro-maven-plugin</artifactId>
                <version>1.8.2</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>schema</goal>
                            <goal>protocol</goal>
                            <goal>idl-protocol</goal>
                        </goals>
                        <configuration>
                            <sourceDirectory>${project.basedir}/src/main/avro/</sourceDirectory>
                            <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```


## Benchmarks

![My image](src/main/resources/img/benchmark.png)

For this benchmark we made 1000 request with Json body for Rest and proto and thrift for RPC.

* [Rest](src/main/scala/benchmark) Http finagle client against Grizzly server.

* [Rest](src/main/scala/benchmark) Http finagle client against Finagle server.

* [gRPC](src/main/java/com/politrons/grpc/benchmark/regular) using standard implementation.

* [gRPC Reactive](src/main/java/com/politrons/grpc/benchmark/reactive) using reactive StreamObserver.

* [Thrift RPC](src/main/scala/finagle/thrift/rpc) using Apache thrift.

* [Avro RPC](src/main/java/com/politrons/avro/rpc) Using Apache Avro.



##### Results

![My image](src/main/resources/img/benchmark_result.png)
