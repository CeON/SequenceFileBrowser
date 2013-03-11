package pl.edu.icm.tools.sequencefile.browser.interpreter

import org.apache.hadoop.io.{BytesWritable, Writable}
import java.lang.reflect.Method

/**
 * Uses specified class to deserialise ProtoBuf message stored in a writable.
 *
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class ProtoBufInterpreter extends Interpreter {
  var className: String = null
  var parser: Method = null

  def setClass(c: String) {
    className = c
  }

  override def setup() {
    parser = Class.forName(className).getMethod("parseFrom", classOf[Array[Byte]])
  }

  def interpret(writable: Writable) =
    parser.invoke(null, writable.asInstanceOf[BytesWritable].copyBytes).toString
}
