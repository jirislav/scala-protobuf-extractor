# scala-protobuf-extractor
Fast Protobuf field extractor written in Scala. Its purpose is to read fast a given tag from serialized message without the need to deserialize whole message.

## Motivation
It would be nice to have a fast and reliable method for single field extraction from Google Protocol Buffer message, without the need to deserialize whole message into an object in JVM.

It might be used with success in high throughput applications (e.g. metrics extraction using Apache Flink for streamed data).

## Implementation Resources

- Encoding Introduction
  - https://developers.google.com/protocol-buffers/docs/encoding
- Wire Format
  - https://developers.google.com/protocol-buffers/docs/reference/csharp/class/google/protobuf/wire-format
- Reading Byte Stream
  - https://developers.google.com/protocol-buffers/docs/reference/csharp/class/google/protobuf/coded-input-stream

## Usage

```scala

// First, get the PB field descriptor
val fieldDescriptor = YourGeneratedMessageV3.Builder.getDescriptor.findFieldByName("fieldName")

// And provide the input stream (holding the bytes with the message definition) to method for particular field type extraction
val serializedBoolean = FieldExtractor.extractBool(
  codedInputStream,
  fieldDescriptor.getNumber
)
```

### Gradle

```gradle
dependencies {
    // ...

    // Scala 2.13
    implementation 'cz.jkozlovsky:scala-protobuf-field-extractor_2.13:0.1.0'

    // Scala 2.12
    implementation 'cz.jkozlovsky:scala-protobuf-field-extractor_2.12:0.1.0'

    // ...
}
```

### Sbt

```sbt
// Scala 2.13
libraryDependencies += cz.jkozlovsky % scala-protobuf-field-extractor_2.13 % 0.1.0

// Scala 2.12
libraryDependencies += cz.jkozlovsky % scala-protobuf-field-extractor_2.12 % 0.1.0
```
