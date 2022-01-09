/*
 * This Scala source file was generated by the Gradle 'init' task.
 */
package cz.jkozlovsky.scala.protobuf.field.extractor

import com.google.protobuf._

import java.io.InputStream
import java.nio.ByteBuffer
import scala.language.implicitConversions


object FieldExtractor {

  case class TagNotFoundException(tagId: Int) extends Exception(s"The tagId '$tagId' was not found within the provided inputStream.")

  /** Calling this should enable the unsafe mode, making it possible to further speed up the extraction process. */
  def enableUnsafe: sun.misc.Unsafe = {
    val f = classOf[sun.misc.Unsafe].getDeclaredField("theUnsafe")
    f.setAccessible(true)
    val unsafe = f.get(null).asInstanceOf[sun.misc.Unsafe]
    unsafe
  }

  /** Extracts the provided field by specifying its tag & extractor method. */
  protected def extract[T](codedInputStream: CodedInputStream, tagId: Int, extractionMethod: () => T): T = {
    while (!codedInputStream.isAtEnd) {
      if (WireFormat.getTagFieldNumber(codedInputStream.readTag()) == tagId) {
        return extractionMethod.apply()
      } else codedInputStream.skipField(codedInputStream.getLastTag)
    }
    // TODO: Tag won't be find in case it is set to a default value (which means being unset in an optional field)
    //       But we actually shouldn't blindly return the default if tag not found as the tag ID might be wrong
    throw TagNotFoundException(tagId)
  }

  /** Use with caution, it might unintentionally slow down the whole extraction process if used unwisely. */
  object WithImplicitCodedInputStream {
    implicit def codeInputStream(inputStream: InputStream): CodedInputStream = CodedInputStream.newInstance(inputStream)

    implicit def codeByteBuffer(byteBuffer: ByteBuffer): CodedInputStream = CodedInputStream.newInstance(byteBuffer)

    implicit def codeArrayOfBytes(arrayOfBytes: Array[Byte]): CodedInputStream = CodedInputStream.newInstance(arrayOfBytes)

    implicit def codeIterableOfByteBuffer(byteBuffers: java.lang.Iterable[ByteBuffer]): CodedInputStream = CodedInputStream.newInstance(byteBuffers)
  }

  def extractBool(codedInputStream: CodedInputStream, tagId: Int): Boolean = extract(codedInputStream, tagId, () => codedInputStream.readBool())

  def extractBytes(codedInputStream: CodedInputStream, tagId: Int): ByteString = extract(codedInputStream, tagId, () => codedInputStream.readBytes())

  def extractByteArray(codedInputStream: CodedInputStream, tagId: Int): Array[Byte] = extract(codedInputStream, tagId, () => codedInputStream.readByteArray())

  def extractByteBuffer(codedInputStream: CodedInputStream, tagId: Int): ByteBuffer = extract(codedInputStream, tagId, () => codedInputStream.readByteBuffer())

  def extractDouble(codedInputStream: CodedInputStream, tagId: Int): Double = extract(codedInputStream, tagId, () => codedInputStream.readDouble())

  def extractEnum(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readEnum())

  def extractFixed32(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readFixed32())

  def extractFixed64(codedInputStream: CodedInputStream, tagId: Int): Long = extract(codedInputStream, tagId, () => codedInputStream.readFixed64())

  def extractFloat(codedInputStream: CodedInputStream, tagId: Int): Float = extract(codedInputStream, tagId, () => codedInputStream.readFloat())

  def extractGroup(codedInputStream: CodedInputStream, tagId: Int, builder: MessageLite.Builder, extensionRegistry: ExtensionRegistryLite): Unit = extract(codedInputStream, tagId, () => codedInputStream.readGroup(tagId, builder, extensionRegistry))

  def extractGroup[T <: MessageLite](codedInputStream: CodedInputStream, tagId: Int, parser: Parser[T], extensionRegistry: ExtensionRegistryLite): T = extract(codedInputStream, tagId, () => codedInputStream.readGroup(tagId, parser, extensionRegistry))

  def extractInt32(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readInt32())

  def extractInt64(codedInputStream: CodedInputStream, tagId: Int): Long = extract(codedInputStream, tagId, () => codedInputStream.readInt64())

  def extractMessage(codedInputStream: CodedInputStream, tagId: Int, builder: MessageLite.Builder, extensionRegistry: ExtensionRegistryLite): Unit = extract(codedInputStream, tagId, () => codedInputStream.readMessage(builder, extensionRegistry))

  def extractMessage[T <: MessageLite](codedInputStream: CodedInputStream, tagId: Int, parser: Parser[T], extensionRegistry: ExtensionRegistryLite): T = extract(codedInputStream, tagId, () => codedInputStream.readMessage(parser, extensionRegistry))

  def extractRawByte(codedInputStream: CodedInputStream, tagId: Int): Byte = extract(codedInputStream, tagId, () => codedInputStream.readRawByte())

  def extractRawBytes(codedInputStream: CodedInputStream, tagId: Int, length: Int): Array[Byte] = extract(codedInputStream, tagId, () => codedInputStream.readRawBytes(length))

  def extractRawLittleEndian32(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readRawLittleEndian32())

  def extractRawLittleEndian64(codedInputStream: CodedInputStream, tagId: Int): Long = extract(codedInputStream, tagId, () => codedInputStream.readRawLittleEndian64())

  def extractRawVarint32(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readRawVarint32())

  def extractRawVarint64(codedInputStream: CodedInputStream, tagId: Int): Long = extract(codedInputStream, tagId, () => codedInputStream.readRawVarint64())

  def extractSFixed32(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readSFixed32())

  def extractSFixed64(codedInputStream: CodedInputStream, tagId: Int): Long = extract(codedInputStream, tagId, () => codedInputStream.readSFixed64())

  def extractSInt32(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readSInt32())

  def extractSInt64(codedInputStream: CodedInputStream, tagId: Int): Long = extract(codedInputStream, tagId, () => codedInputStream.readSInt64())

  def extractString(codedInputStream: CodedInputStream, tagId: Int): String = extract(codedInputStream, tagId, () => codedInputStream.readString())

  def extractStringRequireUtf8(codedInputStream: CodedInputStream, tagId: Int): String = extract(codedInputStream, tagId, () => codedInputStream.readStringRequireUtf8())

  def extractUInt32(codedInputStream: CodedInputStream, tagId: Int): Int = extract(codedInputStream, tagId, () => codedInputStream.readUInt32())

  def extractUInt64(codedInputStream: CodedInputStream, tagId: Int): Long = extract(codedInputStream, tagId, () => codedInputStream.readUInt64())

}