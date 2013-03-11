package pl.edu.icm.tools.sequencefile.browser

import interpreter.{BasicInterpreter, Interpreter}
import org.apache.hadoop.io.Writable
import com.typesafe.config.ConfigFactory

/**
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class PreviewModel {
  private var classProperties: Option[ClassProperties] = None
  private var writable: Option[Writable] = None
  private var interpreter: Interpreter = new BasicInterpreter

  def properties(c: Class[_]): ClassProperties = {
    classProperties =
      classProperties.filter(_.configurableClass == c).orElse(Some(new ClassProperties(c)))
    classProperties.get
  }

  def updatedProperties() {
    interpreter = classProperties.get.newClassInstance.asInstanceOf[Interpreter]
  }

  def displayString =
    writable.map(interpreter.interpret(_)).getOrElse("")

  def element = writable.get
  def element_=(w: Writable) {
    writable = Some(w)
  }

}
