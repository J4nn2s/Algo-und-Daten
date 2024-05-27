class HashTable[K, V](val size: Int) {
  private val table: Array[Array[(K, V)]] = Array.fill(size)(Array.empty[(K, V)])

  // Erste Kompressionsfunktion: Modulo der Hash-Codes
  private def compress1(hashCode: Int): Int = Math.abs(hashCode) % size

  // Zweite Kompressionsfunktion: Modulo einer Primzahl
  private def compress2(hashCode: Int): Int = Math.abs(hashCode) % 7

  // Methode zum Hinzufügen eines Elements
  def put(key: K, value: V, useFirstCompression: Boolean): Unit = {
    val index = if (useFirstCompression) compress1(key.hashCode) else compress2(key.hashCode)
    var i = index
    while (i < size && table(i).nonEmpty) { // check for nonEmpty before accessing head
      if (table(i).head._1 == key) {
        table(i) = Array((key, value))
        return
      }
      i = (i + 1) % size // wrap around the table if necessary
    }
    if (i < size) table(i) = Array((key, value))
  }

  // Methode zum Abrufen eines Elements
  def get(key: K, useFirstCompression: Boolean): Option[V] = {
    val index = if (useFirstCompression) compress1(key.hashCode) else compress2(key.hashCode)
    var i = index
    while (i < size && table(i).nonEmpty) {
      if (table(i).head._1 == key) return Some(table(i).head._2)
      i += 1
    }
    None
  }

  // Methode zum Entfernen eines Elements
  def remove(key: K, useFirstCompression: Boolean): Unit = {
    val index = if (useFirstCompression) compress1(key.hashCode) else compress2(key.hashCode)
    var i = index
    while (i < size && table(i).nonEmpty) {
      if (table(i).head._1 == key) {
        table(i) = Array.empty[(K, V)]
        return
      }
      i += 1
    }
  }
}

// Testen der Hash-Tabelle
object HashTableTest extends App {
  val hashTable = new HashTable[String, String](10) // Specify types for test

  // Test mit der ersten Kompressionsfunktion
  hashTable.put("one", "1", useFirstCompression = true)
  hashTable.put("two", "2", useFirstCompression = true)
  hashTable.put("three", "3", useFirstCompression = true)

  println(hashTable.get("one", useFirstCompression = true))   // Ausgabe: Some(1)
  println(hashTable.get("two", useFirstCompression = true))   // Ausgabe: Some(2)
  println(hashTable.get("three", useFirstCompression = true)) // Ausgabe: Some(3)

  hashTable.remove("two", useFirstCompression = true)
  println(hashTable.get("two", useFirstCompression = true))   // Ausgabe: None

  // Test mit der zweiten Kompressionsfunktion
  hashTable.put("one", "1", useFirstCompression = false)
  hashTable.put("two", "2", useFirstCompression = false)
  hashTable.put("three", "3", useFirstCompression = false)

  println(hashTable.get("one", useFirstCompression = false))   // Ausgabe: Some(1)
  println(hashTable.get("two", useFirstCompression = false))   // Ausgabe: Some(2)
  println(hashTable.get("three", useFirstCompression = false)) // Ausgabe: Some(3)

  hashTable.remove("two", useFirstCompression = false)
  println(hashTable.get("two", useFirstCompression = false))   // Ausgabe: None
}
