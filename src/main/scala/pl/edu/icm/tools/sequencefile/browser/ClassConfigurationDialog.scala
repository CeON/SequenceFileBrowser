package pl.edu.icm.tools.sequencefile.browser

import swing._
import swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

/**
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class ClassConfigurationDialog(owner: Window, properties: ClassProperties) extends Dialog(owner) {
  contents = new BorderPanel {
    modal = true
    val table = new Table {
      model = new AbstractTableModel {
        def getRowCount = properties.length
        def getColumnCount = 2
        def getValueAt(rowIndex: Int, columnIndex: Int) = columnIndex match {
          case 0 => properties.label(rowIndex)
          case 1 => properties(rowIndex)
        }
        override def setValueAt(aValue: Object, rowIndex: Int, columnIndex: Int) {
          properties(rowIndex) = aValue.asInstanceOf[String].trim
        }
        override def isCellEditable(rowIndex: Int, colIndex: Int): Boolean =
          colIndex != 0
      }
      peer.putClientProperty("terminateEditOnFocusLost", true);
    }
    layout(table) = BorderPanel.Position.Center
    layout(new Button("OK"){
      reactions += {
        case ButtonClicked(_) =>
          ClassConfigurationDialog.this.close()
      }
    }) = BorderPanel.Position.South

  }
}
