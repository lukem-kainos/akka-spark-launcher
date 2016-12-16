import akka.actor.ActorSystem

object Main extends App {

  val system = ActorSystem("POC")
  // Create the 'top level' actor
  val manager = system.actorOf(Manager.props(List("Luke", "Ash", "Caoimhe", "Siona", "Stephanie", "Terence", "Adam")), "manager")
  manager ! Start
}
