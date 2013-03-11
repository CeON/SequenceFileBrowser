package pl.edu.icm.tools.sequencefile.browser

import swing._
import event.ButtonClicked
import java.awt.Dimension

/**
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
object SequenceFileBrowser extends SimpleSwingApplication {
  val model = new AppModel()

  def top = new MainFrame{
    val leftPreviewPanel = new PreviewPanel(this, model.keyPreviewModel)
    val rightPreviewPanel = new PreviewPanel(this, model.valuePreviewModel)
    val positionTextField = new TextField() {
      def refresh() {
        text = model.readerPosition.toString
      }
    }
    title = "SequenceFile Browser"
    contents = new BorderPanel {
      val uriPanel = new BoxPanel(Orientation.Horizontal) {
        val uriTextField = new TextField()
        contents += uriTextField
        contents += new Button("Open") {
          reactions += {
            case ButtonClicked(_) =>
              try {
                model.setupReader(uriTextField.text)
                leftPreviewPanel.refresh()
                rightPreviewPanel.refresh()
                positionTextField.refresh()
              } catch {
                case ex:Exception =>
                  ex.printStackTrace()
                  Dialog.showMessage(this, ex)
              }
          }
        }
      }
      val positionPanel = new BoxPanel(Orientation.Horizontal) {

        contents += new Button("Next") {
          reactions += {
            case ButtonClicked(_) =>
              model.readNext()
              leftPreviewPanel.refresh()
              rightPreviewPanel.refresh()
              positionTextField.refresh()
          }
        }
        contents += positionTextField
        contents += new Button("Sync") {
          reactions += {
            case ButtonClicked(_) =>
              try {
                model.syncReader(positionTextField.text.toLong)
                leftPreviewPanel.refresh()
                rightPreviewPanel.refresh()
                positionTextField.refresh()
              } catch {
                case ex:NumberFormatException =>
                  ex.printStackTrace()
                  Dialog.showMessage(this, ex)
              }
          }
        }
      }
      val previewPanel = new SplitPane(Orientation.Vertical) {
        var classProperties: Option[ClassProperties] = None
        resizeWeight = 0.5
        leftComponent = leftPreviewPanel
        rightComponent = rightPreviewPanel
      }
      layout(uriPanel) = BorderPanel.Position.North
      layout(previewPanel) = BorderPanel.Position.Center
      layout(positionPanel) = BorderPanel.Position.South
    }
    size = new Dimension(700, 400)
  }

}
