package pl.edu.icm.tools.sequencefile.browser.interpreter

import org.apache.hadoop.io.Writable

/**
 * Calls toString on a Writable.
 *
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class BasicInterpreter extends Interpreter {
  def interpret(writable: Writable) = writable.toString
}
