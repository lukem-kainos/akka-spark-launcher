sealed trait POCMessage

case object Start extends POCMessage

case class ProcessTable(randomNumber: Int) extends POCMessage

case class Error(message: String) extends POCMessage

case class Result(message: String) extends POCMessage

case class ShellCommand(message: String) extends POCMessage
