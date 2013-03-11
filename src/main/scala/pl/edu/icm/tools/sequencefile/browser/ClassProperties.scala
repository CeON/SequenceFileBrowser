package pl.edu.icm.tools.sequencefile.browser

/**
 * @author Mateusz Fedoryszak (m.fedoryszak@icm.edu.pl)
 */
class ClassProperties(val configurableClass: Class[_]) {
  val setterPrefix = "set"
  val setupMethod = "setup"

  private val properties =
    configurableClass.getMethods
      .filter(m => m.getParameterTypes.toSeq == Seq(classOf[String]) && m.getName.startsWith(setterPrefix))
      .map(m => m.getName.substring(3)).toArray
  private val values = Array.tabulate(properties.length)(_ => "")

  def length = properties.length

  def apply(index: Int) = values(index)
  def update(index: Int, value: String) { values(index) = value }
  def label(index: Int) = properties(index)

  def newClassInstance = {
    val instance = configurableClass.newInstance()
    for ((name, value) <- properties zip values) {
      val method = configurableClass.getMethod(setterPrefix + name, classOf[String])
      method.invoke(instance, value)
    }
    try {
      configurableClass.getMethod(setupMethod).invoke(instance)
    } catch {
      case ex: NoSuchMethodException => //intentionally left blank
    }
    instance
  }
}
