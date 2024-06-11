// mit Hilfe von Chat GPT und GEMINI

import scala.collection.mutable.PriorityQueue

case class HuffmanNode(frequency: Int, byte: Option[Byte], left: Option[HuffmanNode], right: Option[HuffmanNode])

object HuffmanNode {
  implicit val ordering: Ordering[HuffmanNode] = Ordering.by(-_.frequency)
}

object HuffmanTree {
  def buildTree(frequencies: Map[Byte, Int]): HuffmanNode = {
    val pq = PriorityQueue.empty[HuffmanNode]
    frequencies.foreach { case (byte, freq) =>
      pq.enqueue(HuffmanNode(freq, Some(byte), None, None))
    }

    while (pq.size > 1) {
      val left = pq.dequeue()
      val right = pq.dequeue()
      val merged = HuffmanNode(left.frequency + right.frequency, None, Some(left), Some(right))
      pq.enqueue(merged)
    }

    pq.dequeue()
  }

  def generateCodes(node: HuffmanNode, prefix: String, codeTable: collection.mutable.Map[Byte, String]): Unit = {
    node match {
      case HuffmanNode(_, Some(byte), None, None) => codeTable(byte) = prefix
      case HuffmanNode(_, None, Some(left), Some(right)) =>
        generateCodes(left, prefix + "0", codeTable)
        generateCodes(right, prefix + "1", codeTable)
      case _ => 
    }
  }
}

object HuffmanEncode {
  def encode(data: Array[Byte]): (Array[Byte], Map[Byte, String]) = {
    val frequencies = data.groupBy(identity).view.mapValues(_.length).toMap
    val huffmanTree = HuffmanTree.buildTree(frequencies)
    val codeTable = collection.mutable.Map[Byte, String]()
    HuffmanTree.generateCodes(huffmanTree, "", codeTable)

    val encodedData = data.flatMap(byte => codeTable(byte).map(_.asDigit.toByte)).toArray
    (encodedData, codeTable.toMap)
  }
}

object HuffmanDecode {
  def decode(encodedData: Array[Byte], codeTable: Map[Byte, String]): Array[Byte] = {
    val reversedCodeTable = codeTable.map(_.swap)
    var currentCode = ""
    val decodedData = encodedData.flatMap { bit =>
      currentCode += bit.toString
      reversedCodeTable.get(currentCode) match {
        case Some(byte) =>
          currentCode = ""
          Some(byte)
        case None => None
      }
    }.toArray
    decodedData
  }
}

object HuffmanExample {
  def main(args: Array[String]): Unit = {
    // Beispiel 1
    val exampleData1 = "this is an example for huffman encoding".getBytes
    println("Original data (Example 1):")
    println(new String(exampleData1))

    val (encodedData1, codeTable1) = HuffmanEncode.encode(exampleData1)
    println("Encoded data (Example 1):")
    println(encodedData1.mkString(" "))

    val decodedData1 = HuffmanDecode.decode(encodedData1, codeTable1)
    println("Decoded data (Example 1):")
    println(new String(decodedData1))

    // Beispiel 2
    val exampleData2 = "another example with different text".getBytes
    println("Original data (Example 2):")
    println(new String(exampleData2))

    val (encodedData2, codeTable2) = HuffmanEncode.encode(exampleData2)
    println("Encoded data (Example 2):")
    println(encodedData2.mkString(" "))

    val decodedData2 = HuffmanDecode.decode(encodedData2, codeTable2)
    println("Decoded data (Example 2):")
    println(new String(decodedData2))
  }
}
