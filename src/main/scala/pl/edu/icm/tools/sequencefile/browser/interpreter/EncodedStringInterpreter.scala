package pl.edu.icm.tools.sequencefile.browser.interpreter

import org.apache.hadoop.io.{BytesWritable, Writable}

/**
 * Assumes it gets BytesWritable with string encoded in UTF-8.
 *
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class EncodedStringInterpreter extends Interpreter {
  val charset = "UTF-8"

  def interpret(writable: Writable) =
    new String(writable.asInstanceOf[BytesWritable].copyBytes(), charset)
}
