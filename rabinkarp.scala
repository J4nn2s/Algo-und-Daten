
def naiveAlgo(s: String, p: String): Boolean = {
    val n: Int = s.length()
    val m: Int = p.length()
    var i: Int = 0
    var j: Int = 0
    while (i < n-m) {
        while (s(i+j) == p(j)) {
            j = j + 1
            if (j==m) {
                return true
            }
        }
        i = i + 1
        j = 0
    }
    return false
}

@main def run(): Unit = {
  println(naiveAlgo(s = "RHABARBERBARBARBARENBARTBARBIER", p = "BARBIE"))
}
