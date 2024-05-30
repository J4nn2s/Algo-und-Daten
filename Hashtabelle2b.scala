import scala.collection.mutable.ListBuffer

class HashTable[K, V](var size: Int) { 

  private var numEntries = 0
  private val maxLoadFactor = 3
  private val minLoadFactor = 1
  private val threshold = 20
  private var table: Array[ListBuffer[(K, V)]] = Array.fill(size)(ListBuffer.empty[(K, V)])

  private def compress1(hashCode: Int): Int = Math.abs(hashCode) % size

  private def compress2(hashCode: Int): Int = Math.abs(hashCode) % 5 

  private def loadFactor: Double = numEntries.toDouble / size
  private def resize(newSize: Int): Unit = {
    println("Wir benötigen einen resize")
    val oldTable = table
    size = newSize
    table = Array.fill(size)(ListBuffer.empty[(K, V)])
    numEntries = 0

    for (bucket <- oldTable; entry <- bucket) {
      put(entry._1, entry._2, useCompression1 = true) 
    }
  }

  private def checkAndResize(): Unit = {
    if (numEntries >= threshold && loadFactor > maxLoadFactor) {
      resize(size * 2)
    } else if (size > threshold && loadFactor < minLoadFactor) {
      resize(size / 2)
    }
  }

  def put(key: K, value: V, useCompression1: Boolean = true): Unit = { 
    checkAndResize()

    val index = if (useCompression1) compress1(key.hashCode) else compress2(key.hashCode)
    val bucket = table(index)
    val existing = bucket.indexWhere(_._1 == key)

    if (existing >= 0) {
      bucket(existing) = (key, value)
    } else {
      bucket += ((key, value))
      numEntries += 1
    }
  }

  def get(key: K, useCompression1: Boolean = true): V = { 
    val index = if (useCompression1) compress1(key.hashCode) else compress2(key.hashCode)
    val bucket = table(index)
    val found = bucket.find(_._1 == key)

    found match {
      case Some((_, value)) => value
      case None => throw new NoSuchElementException(s"Key $key not found")
    }
  }

  def remove(key: K, useCompression1: Boolean = true): Unit = {
    val index = if (useCompression1) compress1(key.hashCode) else compress2(key.hashCode)
    val bucket = table(index)
    val existing = bucket.indexWhere(_._1 == key)

    if (existing >= 0) {
      bucket.remove(existing)
      numEntries -= 1
      checkAndResize() 
    } else {
      throw new NoSuchElementException(s"Key $key not found")
    }
  }
}

// Testen 
object HashTableTest extends App {

  val hashTable = new HashTable[String, String](5) 
  hashTable.put("one", "Germany")
  hashTable.put("two", "Spain")
  hashTable.put("three", "Italy")
  hashTable.put("four", "France")
  hashTable.put("five", "Portugal")
  hashTable.put("six", "Belgium")
  hashTable.put("seven", "Netherlands")
  hashTable.put("eight", "Greece")
  hashTable.put("nine", "Sweden")
  hashTable.put("ten", "Norway")
  hashTable.put("eleven", "Denmark")
  hashTable.put("twelve", "Finland")
  hashTable.put("thirteen", "Austria")
  hashTable.put("fourteen", "Switzerland")
  hashTable.put("fifteen", "Poland")
  hashTable.put("sixteen", "Czech Republic")
  hashTable.put("seventeen", "Hungary")
  hashTable.put("eighteen", "Slovakia")
  hashTable.put("nineteen", "Slovenia")
  hashTable.put("twenty", "Croatia")
  // hier erster Resize theoretisch
  hashTable.put("twentyone", "Senegal")
  hashTable.put("twentytwo", "Afghanistan")

  println(hashTable.get("one"))
  println(hashTable.get("two"))
  println(hashTable.get("three"))
  hashTable.remove("two")
  try {
    println(hashTable.get("two"))
  } catch {
    case _: NoSuchElementException => println("not found")
  }
}
