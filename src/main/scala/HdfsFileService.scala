import java.io.{File, InputStream}

  import org.apache.hadoop.fs.{FileSystem, Path, FileUtil}
import org.apache.hadoop.conf.Configuration


object HdfsFileService {
  private val conf = new Configuration()
  private val hdfsCoreSitePath = new Path("core-site.xml")
  private val hdfsHDFSSitePath = new Path("hdfs-site.xml")

  org.apache.log4j.BasicConfigurator.configure() // StackOverflow told me to do this
  System.setProperty("hadoop.home.dir", "/") // more dirty hacking
  System.setProperty("HADOOP_USER_NAME", "cloudera")

  conf.addResource(hdfsCoreSitePath)
  conf.addResource(hdfsHDFSSitePath)

  private val fileSystem = FileSystem.get(conf)

  def saveFile(filepath: String, defaultString: String = "input string"): Unit = {
    val file = new File(filepath)
    val out = fileSystem.create(new Path(file.getName))
    out.writeBytes(defaultString)
    out flush()
    out close()
    fileSystem close()

  }

  def removeFile(filename: String): Boolean = {
    val path = new Path(filename)
    fileSystem.delete(path, true)
  }

  def getFile(filename: String): InputStream = {
    val path = new Path(filename)
    fileSystem.open(path)
  }

  def createFolder(folderPath: String): Unit = {
    val path = new Path(folderPath)
    if (!fileSystem.exists(path)) {
      fileSystem.mkdirs(path)
    }
  }

  def copyFile(from: String, to: String): Unit = {
    val pathFrom = new Path(from)
    val pathTo = new Path(to)

    FileUtil.copy(fileSystem, pathFrom,fileSystem,pathTo,false,false,conf)
  }

  def moveFile(from: String, to: String): Unit = {
    val pathTo = new Path(to)
    val pathFrom = new Path(from)

    fileSystem.rename(pathFrom, pathTo)
  }
}
