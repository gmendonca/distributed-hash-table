# Distributed Hashtable

This project's intention is to provide a simple Distributed Hashtable using Java sockets
and Java inbuilt Hashtable for store key-value pairs.

## Configure

There is a [JSON file]() in the system. The prebuilt configuration is 8 servers running in localhost,
from ports 13000 to 13007. If you need to run in a different situation, you have to change the values
there. It's a simple array of strings using the format "address:port".

## Build
### Maven

There is a maven option, but just for the main functionality of the system.
For benchmarking, go ahead and use the Ant option.

```sh
$ mvn package
```

```sh
$ java -jar target/distributed-hash-table-0.0.1.jar 0 localhost 13000
```

### Ant

For the Ant option, there is three options to run the program: as a client with an user
interface, and for doing benchmarking there is two approaches, one open and closing the socket
per task and the another one leaving the socket open all the time. The second approach is way
faster, but I leave it there for evaluation purposes.

For this project, I used a JSON as the config file, if is not on the `lib/` folder, you have to download the [JSON.simple](https://code.google.com/p/json-simple/) and put the jar file and put it there. For the Maven,
is already on the Maven dependencies, so you don't need to worry about it.

After that, you can run the following commands to build your system:

```sh
$ ant clean
$ ant compile
$ ant jar
```
