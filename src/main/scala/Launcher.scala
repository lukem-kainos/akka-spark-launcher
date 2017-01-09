import java.io.{BufferedReader, InputStreamReader}
import org.apache.spark.launcher.SparkLauncher
import java.util.logging.Logger

object Launcher extends App {
  val sparkSuccessLauncher = new SparkLauncher()
    .setAppResource("/home/cloudera/Read-Write.jar")
    .setMainClass("SimpleSparkApp")
    .setSparkHome("/usr/lib/spark")
    .addAppArgs("succeed")
    .launch()

  val reader = new BufferedReader(new InputStreamReader(sparkSuccessLauncher.getErrorStream))

  sparkSuccessLauncher.waitFor()

  var line: String = _
  while({line = reader.readLine(); line != null} ) {
    println(line)
  }
}
