/* Jannes Glaubitz, Firdevs Ugur
    used Pseudocode and help from ChatGPT */

import scala.collection.mutable.ListBuffer

class HashTable[K, V](val size: Int) {
  private val table: Array[ListBuffer[(K, V)]] = Array.fill(size)(ListBuffer.empty[(K, V)])

  private def compress1(hashCode: Int): Int = Math.abs(hashCode) % size


  private def compress2(hashCode: Int): Int = Math.abs(hashCode) % 5

  def put(key: K, value: V, useCompression1: Boolean): Unit = {
    val index = if (useCompression1) compress1(key.hashCode) else compress2(key.hashCode)
    val bucket = table(index)
    val existing = bucket.indexWhere(_._1 == key)

    if (existing >= 0) {
      bucket(existing) = (key, value)
    } else {
      bucket += ((key, value))
    }
  }


  def get(key: K, useCompression1: Boolean): V = {
    val index = if (useCompression1) compress1(key.hashCode) else compress2(key.hashCode)
    val bucket = table(index)
    val found = bucket.find(_._1 == key)
    
    found match {
      case Some((_, value)) => value
      case None => throw new NoSuchElementException(s"Key $key not found")
    }
  }


  def remove(key: K, useCompression1: Boolean): Unit = {
    val index = if (useCompression1) compress1(key.hashCode) else compress2(key.hashCode)
    val bucket = table(index)
    val existing = bucket.indexWhere(_._1 == key)

    if (existing >= 0) {
      bucket.remove(existing)
    } else {
      throw new NoSuchElementException(s"Key $key not found")
    }
  }
}

// Testen
object HashTableTest extends App {

  val hashTable = new HashTable[String, String](15) 


  hashTable.put("one", "Germany", useCompression1 = true)
  hashTable.put("two", "Spain", useCompression1 = true)
  hashTable.put("three", "Italy", useCompression1 = true)

  println(hashTable.get("one", useCompression1 = true))   
  println(hashTable.get("two", useCompression1 = true))   
  println(hashTable.get("three", useCompression1 = true)) 

  hashTable.remove("two", useCompression1 = true)
  try {
    println(hashTable.get("two", useCompression1 = true)) 
  } catch {
    case e: NoSuchElementException => println("not found")
  }
}