import scala.io.Source
import java.net.URL

// Funktion zum Herunterladen des Textes von einer URL
def getTextFromUrl(url: String): String = {
  val source = Source.fromURL(new URL(url))
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
            j = j + 1
            if (j==m) {
                count = count +1
            }
        }
        i = i + 1
        j = 0
    }
    return count
}

@main def run(): Unit = {
  val url = "https://www.gutenberg.org/files/2701/2701-0.txt"
  val mobyDickText = getTextFromUrl(url)
  println(s"Textlänge: ${mobyDickText.length}")
  val found = naiveAlgo(mobyDickText, "whale")
  println(s"Das Wort whale wurde im Text $found mal gefunden.")

}


def rabinkarp(s: String, p:String): Boolean = {
    return true
}