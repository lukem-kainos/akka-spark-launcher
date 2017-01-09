
import akka.actor.{Actor, ActorLogging, Props}
import org.apache.spark.launcher.SparkLauncher

class SupervisionActor extends Actor with ActorLogging {

  def receive = {

    case SupervisionActor.RunJob(args) => {
      val numChildren = args.length

      for (arg <- args) {
        val sparkJobActor = context.actorOf(Props[SparkJobActor], name = arg)
        val launchMsg = SparkJobActor.Launch(
          "/home/cloudera/Read-Write.jar",
          "SimpleSparkApp",
          "/usr/lib/spark",
          Some("local"),
          List(arg),
          Map(SparkLauncher.DRIVER_MEMORY -> "2g")
        )
        sparkJobActor ! launchMsg
      }
      context become running(0, numChildren)
    }
  }

  def running(numFinished: Int, numChildren: Int): Receive = {
    case SparkJobActor.Finished(state) => {

      val newNumFinished = numFinished + 1
      println("Supervisor notified finished: " + state.name + " from: " + sender)
      if (newNumFinished == numChildren) context.system.shutdown()
      else context become running(newNumFinished, numChildren)
    }
  }
}

object SupervisionActor {
  val props = Props[SupervisionActor]

  case class RunJob(args: List[String])

}

