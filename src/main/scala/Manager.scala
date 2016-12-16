import akka.actor.{Actor, ActorLogging, Props}
import scala.collection.mutable.ListBuffer

object Manager {
  def props(tableNames: List[String]): Props = Props(new Manager(tableNames: List[String]))
}

class Manager(tableNames: List[String]) extends Actor with ActorLogging {

  var output = ListBuffer[String]()
  var errorAndResultCount = 0

  def receive = {
    case Start =>
      // create an actor for each tableName
      // send it either:
      // a random int between 1 and 100,
      // or if it is "luke", a shell command
      for (tableName <- tableNames) {
        val cdcRunnerActor = context.actorOf(Props[CDCRunner], name = tableName)
        tableName match {
          case "Luke" => cdcRunnerActor ! ShellCommand("ls -al")
          case _ => cdcRunnerActor ! ProcessTable(scala.util.Random.nextInt(100))
        }
      }
    case Result(message) =>
      // log the message, append it to output and check if all replies have been received
      log.info(message)
      output = output += message
      incrementCounterAndTestDone()
    case Error(message) =>
      // log the error and check if all replies have been received
      log.error(message)
      incrementCounterAndTestDone()
    case _ => log.error("Unknown message")
  }

  def incrementCounterAndTestDone() = {
    errorAndResultCount += 1
    if (errorAndResultCount == tableNames.size) {
      log.info("\nall DONE: " + output.mkString("\n"))
      context.system.terminate
    }
  }
}