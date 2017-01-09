
//import akka.actor.{Actor, ActorLogging}
//import sys.process._
//
//class CDCRunner extends Actor with ActorLogging {
//
//  val CDCRunnerName = self.path.name
//
//  def receive = {
//    case ProcessTable(randomNumber) =>
//      if (randomNumber % 3 == 0) {
//        sender ! Result(s"$CDCRunnerName thinks $randomNumber IS divisible by 3.")
//      } else {
//        sender ! Error(s"$CDCRunnerName thinks $randomNumber is not divisible by 3.")
//      }
//    case ShellCommand(command: String) =>
//      log.info(s"attempting $command")
//      try {
//        val resultOfCommand = command !!;
//        sender ! Result(s"$CDCRunnerName says: $resultOfCommand")
//      } catch {
//        case foo: Exception => sender ! Error(foo.toString)
//      }
//    case MakeFile(i) => HdfsFileService.removeFile(i.toString)
//
//    case _ => sender ! Error(s"$CDCRunnerName Did not receive an appropriate message.")
//  }
//}