package pl.edu.icm.tools.sequencefile.browser

import collection.JavaConversions._
import swing._
import swing.event._
import com.typesafe.config.ConfigFactory

/**
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class PreviewPanel(parent: Window, model: PreviewModel) extends BorderPanel {
  private val textArea = new TextArea()
  private val transformerPanel = new BorderPanel {
    val interpreterTextField = new ComboBox[String](ConfigFactory.load().getStringList("interpreter.hints").toSeq) {
      makeEditable()
    }
    val applyButton = new Button("Apply") {
      reactions += {
        case ButtonClicked(_) => try {
          val classProperties = model.properties(Class.forName(interpreterTextField.item))
          if (classProperties.length > 0) {
            new ClassConfigurationDialog(parent, classProperties).open()
          }
          model.updatedProperties()
          refresh()
        } catch {
          case ex: Throwable =>
            ex.printStackTrace()
            Dialog.showMessage(this, ex)
        }
      }
    }
    layout(interpreterTextField) = BorderPanel.Position.Center
    layout(applyButton) = BorderPanel.Position.East
  }
  layout(new ScrollPane(textArea)) = BorderPanel.Position.Center
  layout(transformerPanel) = BorderPanel.Position.South

  def refresh() {
    textArea.text = model.displayString
  }
}
