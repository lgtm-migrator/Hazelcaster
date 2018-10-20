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
$ java -jar hazelcaster-server.jar
```

This will deploy a new node in the cluster. After that, you can run the client
with:

```
$ java -jar hazelcaster-client.jar <options>
```

## Options

...

## Designer

This project has been built, designed and maintained by:

* [Martín Biagini](https://github.com/mbiagini)
* [Joaquín Filipic](https://github.com/joaquinfilipic)
* [Agustín Golmar](https://github.com/agustin-golmar)

## Bibliography

__"MapReduce: Simplified Data Processing on Large Clusters"__. Jeffrey Dean,
Sanjay Ghemawat. _Google, Inc. OSDI, 2004_.
