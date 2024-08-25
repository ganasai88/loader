package src.main.scala.Loader

import java.io.File
import org.apache.cassandra.io.sstable.SSTableLoader
import org.apache.cassandra.utils.OutputHandler
import org.apache.hadoop.conf.Configuration
import org.apache.cassandra.hadoop.cql3.CqlBulkRecordWriter.ExternalClient
import scala.util.Random

object Loader {

  def main(args: Array[String]): Unit = {
    val exportConf = ExportConfiguration(
      cassHosts = "127.0.0.1", // Replace with your Cassandra contact points (comma-separated)
      throughputInMBits = 1024,         // Replace with your desired throughput in Mbits
      throughputBufferSize = 65536      // Replace with your desired buffer size
    )

    // Update this path to the new network location
    val sstableDirectory = "\\apache-cassandra-4.0.13\\data\\data\\cqlkeyspace\\newtable-850b07b02d9e11ef99655b1a35a12391\\"

    flushSSTableToCassandra(exportConf, sstableDirectory)
  }

  case class ExportConfiguration(cassHosts: String, throughputInMBits: Int, throughputBufferSize: Int)

  def flushSSTableToCassandra(exportConf: ExportConfiguration, dir: String): Unit = {
    val conf = new Configuration()
    val shuffledCassHosts = Random.shuffle(exportConf.cassHosts.split(",").toList)
    val selectedCassHost = shuffledCassHosts.head
    conf.set("cassandra.output.thrift.address", selectedCassHost)
    conf.set("mapreduce.output.bulkoutputformat.streamthrottlembits", exportConf.throughputInMBits.toString)
    conf.set("mapreduce.output.bulkoutputformat.buffersize", exportConf.throughputBufferSize.toString)

    // Ensure the directory exists and is accessible
    val sstableDirFile = new File(dir)
    if (!sstableDirFile.exists() || !sstableDirFile.isDirectory) {
      throw new IllegalArgumentException(s"Invalid SSTable directory: $dir")
    }

    // Initialize SSTableLoader with the directory, ExternalClient, and OutputHandler
    val sSTableLoader = new SSTableLoader(sstableDirFile,
      new ExternalClient(conf),
      new OutputHandler.LogOutput)

    try {
      sSTableLoader.stream().get()
      println(s"SSTables flushed to Cassandra node: $selectedCassHost")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        println(s"Failed to stream SSTables: ${e.getMessage}")
    }
  }
}

