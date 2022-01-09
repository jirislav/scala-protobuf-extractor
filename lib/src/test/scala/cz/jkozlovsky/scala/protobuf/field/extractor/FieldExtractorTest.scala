package cz.jkozlovsky.scala.protobuf.field.extractor

import com.google.protobuf.{CodedInputStream, GeneratedMessageV3}
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner
import protobuf_unittest.Unittest.TestAllTypes

import java.io.{PipedInputStream, PipedOutputStream}
import scala.jdk.CollectionConverters._

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
    .build();

  final val DefaultMessage = TestAllTypes.newBuilder().build()

  def createCodedInputStream(message: GeneratedMessageV3): CodedInputStream = {
    val in = new PipedInputStream()
    val out = new PipedOutputStream(in)
    message.writeTo(out)
    out.close()
    CodedInputStream.newInstance(in)
  }

  "extractBool" should "be working" in assert {
    FieldExtractor.extractBool(
      createCodedInputStream(BasicMessage),
      OptionalBoolFieldDescriptor.getNumber
    )
  }

  "extractInt" should "be working" in assert {
    FieldExtractor.extractInt32(
      createCodedInputStream(BasicMessage),
      OptionalInt32FieldDescriptor.getNumber
    ) == 1000
  }

  "extractLong" should "be working" in assert {
    FieldExtractor.extractInt64(
      createCodedInputStream(BasicMessage),
      OptionalInt64FieldDescriptor.getNumber
    ) == 10000L
  }

  // TODO: Tag won't be find in case it is set to a default value (which means being unset in an optional field)
  //       But we actually shouldn't blindly return the default if tag not found as the tag ID might be wrong
  "extractDefaultBool" should "not fail with default values" ignore assert {
    !FieldExtractor.extractBool(
      createCodedInputStream(DefaultMessage),
      OptionalBoolFieldDescriptor.getNumber
    )
  }
}
