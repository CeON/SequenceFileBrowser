package pl.edu.icm.tools.sequencefile.browser.interpreter

import org.apache.hadoop.io.Writable
import java.io.{StringWriter, StringReader}
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.{OutputKeys, TransformerFactory}
import javax.xml.parsers.SAXParserFactory
import org.xml.sax.{InputSource, EntityResolver}
import javax.xml.transform.sax.SAXSource

/**
 * Assumes a string stored in writable is XML and pretty-prints it.
 *
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class XmlInterpreter extends Interpreter {
  def interpret(writable: Writable) = {
    val indent = 4
    val xmlString = writable.toString

    val xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader
    xmlReader.setEntityResolver(new EntityResolver {
      def resolveEntity(publicId: String, systemId: String) = new InputSource(new StringReader(""))
    })
    val xmlInput = new SAXSource(xmlReader, new InputSource(new StringReader(xmlString)))

    val stringWriter = new StringWriter()
    val xmlOutput = new StreamResult(stringWriter)

    val transformerFactory = TransformerFactory.newInstance()
    transformerFactory.setAttribute("indent-number", indent)
    val transformer = transformerFactory.newTransformer()
    transformer.setOutputProperty(OutputKeys.INDENT, "yes")
    transformer.transform(xmlInput, xmlOutput)

    xmlOutput.getWriter.toString
  }
}
