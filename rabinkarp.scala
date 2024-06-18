import scala.io.Source
import java.net.URL
import java.net.URI

// Funktion zum Herunterladen des Textes von einer URL
def getTextFromUrl(url: String): String = {
  val source = Source.fromURL(new URI(url).toURL())
  try source.mkString finally source.close()
}

def naiveAlgo(s: String, p: String): Int = {
    val n: Int = s.length()
    val m: Int = p.length()
    var i: Int = 0
    var j: Int = 0
    var count: Int = 0
    while (i <= n-m) {
        while (j < m && i + j < n && s(i + j) == p(j)) {
            j += 1
            if (j==m) {
                count += 1
            }
        }
        i += 1
        j = 0
    }
    return count
}

def rabinKarp(s: String, p: String): Int = {
    val n: Int = s.length()
    val m: Int = p.length()
    val patternHash = p.hashCode() // constant hash of pattern
    var count: Int = 0
    for (i <- 0 to n - m) {
      val textHash = s.substring(i, i + m).hashCode
      if (patternHash == textHash && s.substring(i, i + m) == p) {
        count += 1
      }
    }
    count
}

@main def run(): Unit = {
  val url = "https://www.gutenberg.org/files/2701/2701-0.txt"
  val mobyDickText = getTextFromUrl(url)
  println(s"Textlänge: ${mobyDickText.length}")
  
  val startTime = System.nanoTime()
  val foundNaive = naiveAlgo(mobyDickText, "whale")
  val endTime = System.nanoTime()
  val duration = (endTime - startTime) / 1e6 // Konvertiere die Zeit in Millisekunden


  println(s"Das Wort whale wurde im Text durch den Naiven Algo $foundNaive mal gefunden.")
  println(s"Dauer: $duration")

  val startTimeKarp = System.nanoTime()
  val foundRabinKarp = rabinKarp(mobyDickText, "whale")
  val endTimeKarp = System.nanoTime()
  val durationKarp = (endTimeKarp - startTimeKarp) / 1e6 // Konvertiere die Zeit in Millisekunden

  println(s"Das Wort whale wurde im Text durch den Rabin-Karp $foundRabinKarp mal gefunden.")
  println(s"Dauer: $durationKarp")
}