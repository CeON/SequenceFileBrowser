package pl.edu.icm.tools.sequencefile.browser

import org.apache.hadoop.io.{Writable, SequenceFile}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.conf.Configuration
import com.typesafe.config.{ConfigFactory, ConfigException}
import org.apache.commons.io.IOUtils

/**
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class AppModel {
  val conf: Configuration = new Configuration {
    set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem")
    set("dfs.client.use.legacy.blockreader", "true")

    try {
      set("hadoop.socks.server", ConfigFactory.load().getString("socks.server"))
      set("hadoop.rpc.socket.factory.class.default", "org.apache.hadoop.net.SocksSocketFactory")
    } catch {
      case ex: ConfigException.Missing => //Don't set socks
    }
  }

  var reader: Option[SequenceFile.Reader] = None
  val keyPreviewModel = new PreviewModel
  val valuePreviewModel = new PreviewModel
  var key: Writable = null
  var value: Writable = null

  def readNext() {
    reader.get.next(key, value)
    keyPreviewModel.element = key
    valuePreviewModel.element = value
  }

  def setupReader(uri: String) {
    reader.foreach(IOUtils.closeQuietly)

    reader = Some(new SequenceFile.Reader(conf, SequenceFile.Reader.file(new Path(uri))))
    key = reader.get.getKeyClass.newInstance.asInstanceOf[Writable]
    value = reader.get.getValueClass.newInstance.asInstanceOf[Writable]
    readNext()
  }

  def syncReader(position: Long) {
    reader.get.sync(position)
    readNext()
  }

  def readerPosition = reader.get.getPosition

}
