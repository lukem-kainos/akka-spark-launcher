import akka.actor.ActorSystem

object Main {
  def main(args: Array[String]) {
    if (args.length == 0) {
      System.out.println("Please supply a string of arguments. One for each actor and directory that you are creating")
      System.exit(-1)
    }
    val inputArgList = args.toList
    val system = ActorSystem("MyActorSystem")
    val supervisionActor = system.actorOf(SupervisionActor.props, "supervisionActor")
    supervisionActor ! SupervisionActor.RunJob(inputArgList)
    system.awaitTermination()
  }
}
