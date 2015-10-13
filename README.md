# Distributed Hashtable

This project's intention is to provide a simple Distributed Hashtable using Java sockets
and Java inbuilt Hashtable for store key-value pairs.

## Configure

There is a [JSON file](https://code.google.com/p/json-simple/) in the system. The prebuilt configuration is 8 servers running in localhost,
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

## How to use

I provided a simple script to make it easier to run the program. You need to run it this way:

```sh
source script.sh
```

And then you can type `help` to see the options:

```
run_openbench - Run Benchmarking leaving the socket open
run_client - Run a Client and Server with an user interface
run_bench - Run Benchmarking closing and opening the socket
```

Or you can run the jar files like this:
```sh
java -jar build/OpenBench.jar <PeerId> <Address> <Port>

java -jar build/OpenBench.jar <Number of operations> <Number of Clients>

java -jar build/OpenBench.jar <Number of operations> <Number of Clients>
```

The options for both option are explained above:

* PeerId - It should be a number starting from 0 and going until the limit of
the number of Peers provided in the config file. Keep in mind, that there isn't
any sort of verification here. So in order to use the program, keep it fair.

* Address - Should be localhost or an valid ip address.

* Port - A valid port number for the provided address.

* Number Operations - Number of Put, Get and Del operations to run in each client.

* Number of Clients - Number of Clients that you will run the benchmarking.

P.S.: The number of server will be the number provided in the config file.
And the operations will Put, Get, and Del from all of them depending on the key value.
