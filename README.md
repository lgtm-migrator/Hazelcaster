[![...](.resources/image/readme-header.png)](https://github.com/agustin-golmar/Hazelcaster/blob/master/.resources/doc/(2018)%20Map-Reduce%20On%20Hazelcast.pdf)
[![...](https://img.shields.io/badge/hazelcast-v3.7.8-red.svg)](https://hazelcast.org/)
[![...](https://img.shields.io/badge/release-v1.0-blue.svg)](https://github.com/agustin-golmar/Hazelcaster/releases)
[![...](https://www.travis-ci.com/agustin-golmar/Hazelcaster.svg?branch=master)](https://www.travis-ci.com/agustin-golmar/Hazelcaster)

# Hazelcaster

A map-reduce implementation in Hazelcast, for querying airports.

## Project Structure

The project split in several modules:

* `abstractions`: the models and _POJOs_ of the entire system.
* `interfaces`: the API, shared by the client and the server.
* `backend`: concrete implementation of the services defined in the interfaces
module. This is a runtime dependency, forcing other modules to apply _DI_
(dependency injection).
* `server`: the server, _a.k.a._ cluster node. Basically a _Hazelcast_
instance.
* `client`: the frontend. It uses the injected backend for submit map-reducing
operations to the cluster (made by multiple _server_ instances).

## Build

To build the project, it is necessary to have _Maven +3.5.0_, and
_Java SE 8 Release_ installed. Then, run:

```
$ mvn clean package
```

This will generate a _\*.jar_ in the root folder. If you find any issues with
the building, remove the _\*.jar_ files from the _Maven_ local repository
with:

```
$ rm -fr ~/.m2/repository/ar/edu/itba/pod/*
```

Or do it manually, if you prefer.

## Cluster Deploy and Querying

In the root folder, type:

```
$ java -jar -Dinterfaces=127.0.0.1,10.17.0.* -Dcoordinator=127.0.0.1 hazelcaster-server.jar
```

This will deploy a new node in the cluster, listening on the provided
interfaces (the default is _localhost_ and _192.168.0.\*_, so you can change
it). The coordinator node is the __master node__. You need this in every node
except in the master, because _Hazelcaster_ use _TCP discovery_ instead of
_multicasting_. After that, you can run the client with:

```
$ java <options> -jar hazelcaster-client.jar
```

The client and server support an additional option: the _logging level_. The
default is DEBUG, so maybe you should use INFO or something higher:

```
$ java -Dlog.level=INFO ... -jar hazelcaster-server.jar
$ ...
$ java -Dlog.level=INFO ... -jar hazelcaster-client.jar
```

You can change the number of synchronous replicas. By default, the cluster will
use only _1_ replica:

```
$ java -Dreplicas=0 ... -jar hazelcaster-server.jar
```

## Options

The following options are required (they must be set, always):

* `movementsInPath`: the _CSV_ file with airports movements.
* `airportsInPath`: the _CSV_ file with airpots description.

This options are optional, but you probably want to change them:

* `addresses`: a list of cluster node addresses (comma or semicolon separated).
The client will try to connect to at least with one of them.
* `query`: the query number to run. Must be in range _[1, ..., 6]_.
* `n`: the number of lines to retrieve in queries _4_ and _5_.
* `min`: the minimum movements for query _6_.
* `oaci`: the _OACI_ airport identifier for query _4_.
* `outPath`: the output filename, for saving the result of the map-reduce
query.
* `timeOutPath`: the output filename, for saving the times of principal events.

## Querying Example

To connect to the cluster (with a listening node in _127.0.0.1:5701_), and run
query _1_, use:

```
$ java -Daddresses=127.0.0.1:5701 \
       -DmovementsInPath=movements.csv \
       -DairportsInPath=airports.csv \
       -Dquery=1 \
       -jar hazelcaster-client.jar
```

## Designers

This project has been built, designed and maintained by:

* [Martín Biagini](https://github.com/mbiagini)
* [Joaquín Filipic](https://github.com/joaquinfilipic)
* [Agustín Golmar](https://github.com/agustin-golmar)

## Bibliography

__"MapReduce: Simplified Data Processing on Large Clusters"__. Jeffrey Dean,
Sanjay Ghemawat. _Google, Inc. OSDI, 2004_.
