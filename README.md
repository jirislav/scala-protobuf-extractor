# scala-protobuf-extractor
Fast Protobuf field extractor written in Scala. Its purpose is to read fast a given tag from serialized message without the need to deserialize whole message.

## Motivation
It would be nice to have a fast and reliable method for single field extraction from Google Protocol Buffer message, without the need to deserialize whole message into an object in JVM.

It might be used with success in high throughput applications (e.g. metrics extraction using Apache Flink for streamed data).
