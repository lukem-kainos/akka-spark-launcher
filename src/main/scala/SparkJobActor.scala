import akka.actor.{Actor, ActorLogging, ActorRef}
import org.apache.spark.launcher.{SparkAppHandle, SparkLauncher}

class SparkJobActor extends Actor with ActorLogging {

  import SparkJobActor._

  def receive: Receive = {
    case Launch(appResource, mainClass, sparkHome, maybeMaster, args, conf) => {
      val launcher = new SparkLauncher()
        .setAppResource(appResource)
        .setMainClass(mainClass)
        .setSparkHome(sparkHome)

      for (mm <- maybeMaster) {
        launcher.setMaster(mm)
      }
      for (arg <- args) {
        launcher.addAppArgs(arg)
      }
      for ((key, value) <- conf.toList) {
        launcher.setConf(key, value)
      }

      val listener = new SparkAppHandle.Listener {
        override def infoChanged(handle: SparkAppHandle): Unit = {}

        override def stateChanged(handle: SparkAppHandle): Unit = self ! StateChanged
      }

      val handle = launcher.startApplication(listener)
      context become launched(handle, sender)
    }
  }

  def launched(handle: SparkAppHandle, origSender: ActorRef): Receive = {
    case StateChanged => {
      if (handle.getState.isFinal) {
        origSender ! Finished(handle.getState)
        context stop self
      }
    }
  }
}

object SparkJobActor {

  case class Launch(appResource: String,
                    mainClass: String,
                    sparkHome: String,
                    master: Option[String],
                    args: List[String],
                    conf: Map[String, String])

  case object StateChanged

  case class Finished(state: SparkAppHandle.State)

}