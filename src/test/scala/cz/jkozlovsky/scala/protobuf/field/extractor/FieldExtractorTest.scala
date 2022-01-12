package cz.jkozlovsky.scala.protobuf.field.extractor

import com.google.protobuf.{CodedInputStream, GeneratedMessageV3}
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner
import protobuf_unittest.Unittest.TestAllTypes

import java.io.{PipedInputStream, PipedOutputStream}
import collection.JavaConverters._

@RunWith(classOf[JUnitRunner])
class FieldExtractorTest extends AnyFlatSpec {
  final val OptionalInt32FieldDescriptor = TestAllTypes.Builder.getDescriptor.findFieldByName("optional_int32")
  final val OptionalInt64FieldDescriptor = TestAllTypes.Builder.getDescriptor.findFieldByName("optional_int64")
  final val OptionalBoolFieldDescriptor = TestAllTypes.Builder.getDescriptor.findFieldByName("optional_bool")
  final val RepeatedBoolFieldDescriptor = TestAllTypes.Builder.getDescriptor.findFieldByName("repeated_bool")

  final val BasicMessage = TestAllTypes.newBuilder()
    .setOptionalInt32(1000)
    .setOptionalInt64(10000L)
    .setOptionalBool(true)
    .addAllRepeatedBool(List(true, false, true).map(_.asInstanceOf[java.lang.Boolean]).asJava)
    .build()

  final val DefaultMessage = TestAllTypes.newBuilder().build()

  def createCodedInputStream(message: GeneratedMessageV3): CodedInputStream = {
    val in = new PipedInputStream()
    val out = new PipedOutputStream(in)
    message.writeTo(out)
    out.close()
    CodedInputStream.newInstance(in)
  }

  "staticExtract" should "be working with bool" in assert {
    FieldExtractor.staticExtract(
      createCodedInputStream(BasicMessage),
      OptionalBoolFieldDescriptor,
      _.readBool()
    )
  }

  it should "be working with int32" in assert {
    FieldExtractor.staticExtract(
      createCodedInputStream(BasicMessage),
      OptionalInt32FieldDescriptor,
      _.readInt32()
    ) == 1000
  }

  it should "be working with int64" in assert {
    FieldExtractor.staticExtract(
      createCodedInputStream(BasicMessage),
      OptionalInt64FieldDescriptor,
      _.readInt64()
    ) == 10000L
  }

  it should "not fail with default values" in assert {
    !FieldExtractor.staticExtract(
      createCodedInputStream(DefaultMessage),
      OptionalBoolFieldDescriptor,
      _.readBool()
    )
  }

  "dynamicExtract" should "be working with bool" in assert {
    FieldExtractor.dynamicExtract[Boolean](
      createCodedInputStream(BasicMessage),
      OptionalBoolFieldDescriptor
    )
  }

  it should "be working with int32" in assert {
    FieldExtractor.dynamicExtract[Int](
      createCodedInputStream(BasicMessage),
      OptionalInt32FieldDescriptor
    ) == 1000
  }

  it should "be working with int64" in assert {
    FieldExtractor.dynamicExtract[Long](
      createCodedInputStream(BasicMessage),
      OptionalInt64FieldDescriptor
    ) == 10000L
  }

  it should "not fail with default values" in assert {
    !FieldExtractor.dynamicExtract[Boolean](
      createCodedInputStream(DefaultMessage),
      OptionalBoolFieldDescriptor
    )
  }
}
