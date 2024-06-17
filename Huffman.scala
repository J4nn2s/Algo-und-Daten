// Jannes Glaubitz und Firdevs Ugur
// ChatGPT wurde verwendet beim Programmieren

class HuffmanNode(val frequency: Int, val byte: Option[Byte], val left: Option[HuffmanNode], val right: Option[HuffmanNode])

object HuffmanTree {
  def buildTree(frequencies: Map[Byte, Int]): HuffmanNode = {
    val pq = frequencies.map { case (byte, freq) =>
      new HuffmanNode(freq, Some(byte), None, None)
    }.toList.sortBy(-_.frequency)

    def mergeNodes(nodes: List[HuffmanNode]): HuffmanNode = {
      if (nodes.length == 1) nodes.head
      else {
        val left = nodes.last
        val right = nodes.init.last
        val newNode = new HuffmanNode(left.frequency + right.frequency, None, Some(left), Some(right))
        mergeNodes((newNode :: nodes.init.init).sortBy(-_.frequency))
      }
    }

    mergeNodes(pq)
  }

  def generateCodes(node: x, prefix: String): Map[Byte, String] = {
    if (node.byte.isDefined) {
      Map(node.byte.get -> prefix)
    } else {
      val leftCodes = generateCodes(node.left.get, prefix + "0")
      val rightCodes = generateCodes(node.right.get, prefix + "1")
      leftCodes ++ rightCodes
    }
  }
}

object HuffmanEncode {
  def encode(data: Array[Byte]): (List[Byte], Map[Byte, String]) = {
    val frequencies = data.groupBy(identity).mapValues(_.length).toMap
    val huffmanTree = HuffmanTree.buildTree(frequencies)
    val codeTable = HuffmanTree.generateCodes(huffmanTree, "")

    var encodedData = List[Byte]()
    for (byte <- data) {
      val code = codeTable(byte)
      for (bit <- code) {
        encodedData = encodedData :+ bit.asDigit.toByte
      }
    }
    (encodedData, codeTable)
  }
}

object HuffmanDecode {
  def decode(encodedData: List[Byte], codeTable: Map[Byte, String]): List[Byte] = {
    val reversedCodeTable = codeTable.map(_.swap)
    var decodedData = List[Byte]()
    var currentCode = ""
    
    for (bit <- encodedData) {
      currentCode += bit.toString
      if (reversedCodeTable.contains(currentCode)) {
        decodedData = decodedData :+ reversedCodeTable(currentCode)
        currentCode = ""
      }
    }
    decodedData
  }
}

object HuffmanExample {
  def main(args: Array[String]): Unit = {
    val exampleData1 = "algorithmen und datenstrukturen".getBytes
    println("Original data (Example 1):")
    println(new String(exampleData1))

    val (encodedData1, codeTable1) = HuffmanEncode.encode(exampleData1)
    println("Encoded data (Example 1):")
    println(encodedData1.mkString(" "))

    val decodedData1 = HuffmanDecode.decode(encodedData1, codeTable1)
    println("Decoded data (Example 1):")
    println(new String(decodedData1.toArray))

    val exampleData2 = "aabbbccbddbbcbaadcbda".getBytes
    println("Original data (Example 2):")
    println(new String(exampleData2))

    val (encodedData2, codeTable2) = HuffmanEncode.encode(exampleData2)
    println("Encoded data (Example 2):")
    println(encodedData2.mkString(" "))

    val decodedData2 = HuffmanDecode.decode(encodedData2, codeTable2)
    println("Decoded data (Example 2):")
    println(new String(decodedData2.toArray))

    val exampleData3 = "barbarasrababerbar".getBytes
    println("Original data (Example 3):")
    println(new String(exampleData3))

    val (encodedData3, codeTable3) = HuffmanEncode.encode(exampleData3)
    println("Encoded data (Example 3):")
    println(encodedData3.mkString(" "))

    val decodedData3 = HuffmanDecode.decode(encodedData3, codeTable3)
    println("Decoded data (Example 3):")
    println(new String(decodedData3.toArray))

  }
}
