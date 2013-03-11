package pl.edu.icm.tools.sequencefile.browser.interpreter

import org.apache.hadoop.io.Writable

/**
 * All interpreters should extend this trait. Additionally they may contain multiple setAnything(s: String): Unit
 * methods which describe class properties that can be set during runtime. After setting all properties, the
 * setup(): Unit method is called. To interpret a single writable, interpret(writable: Writable): String is called.
 *
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
trait Interpreter {
  def interpret(writable: Writable): String
  def setup() {}
}
