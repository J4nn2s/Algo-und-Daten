import scala.util.Random
// Firdevs Ugur und Jannes Glaubitz
// just type Main in REPL to execute test object
// chatgpt was used to create the cool test object :) & compiling errors

// skipList utilises a List of Nodes instead of a Stack

class skipList{
    private class Node(val key: Int, var value: Any, var pred: Node, var succ: Node, var down: Node)
    private var head = Node(Int.MinValue, null, null, null, null)
    private var bottom = Node(Int.MaxValue, null, null, null, null)
    head.succ = bottom
    bottom.pred = head
    
    private def search(key: Int): List[Node] = {
        var Q: List[Node] = List()
        var n: Node = head // of highest list ?!
        while n != null do {
            while n.succ.key <= key do {
                n = n.succ
            }
            Q = n::Q 
            n = n.down
        }
        return Q
    }

    private def put(k: Int, v: Any): Unit = {
        var Q: List[Node] = search(k)
        var n: Node = Q.last // aus abstract class list -> last , right etc
        if (n.key == k) then { // Wert updaten , wenn k bereits vorhanden
            n.value = v
        }
        var newNode = Node(k, v, n, n.succ, null) // neuer Knoten mit pred "n"
        newNode.succ.pred = newNode
        newNode.pred.succ = newNode // n also der Knoten davor ist jetzt der Vorgänger von newNode und der newNode der Nachfolger von n
        while (muenzwurf == "Kopf") do 
            if (Q.isEmpty) then {
                var headNew = Node(Int.MinValue, Int.MinValue, null, newNode, head)
                var bottomNew = Node(Int.MaxValue, Int.MaxValue, newNode, null, bottom)
                //
                head = headNew // experiment, damit bei nächsten insert Vorgängen auf diese heads / bottoms verwiesen wird 
                bottom = bottomNew

            }
            else { // Q was not empty, also newNode einfügen in bestehende Liste nach n
                var n : Node = Q.last
                val newNodeAfter = Node(k, v, n, n.succ, newNode)

                n.succ.pred = newNodeAfter
                n.succ = newNodeAfter
                // vertical
                // newNodeAfter.down = newNode i think irrelevant due to val newNodeAfter
                newNode = newNodeAfter
            }
    }
    // still work left to be done here
    private def remove(k: Int): Unit = {
        var Q: List[Node] = search(k)
        var n: Node = Q.last
        // hier anders als Thomas
    }
    def muenzwurf: String = {
        val kopfOderZahl: List[String] = List("Kopf", "Zahl") 
        val wurf: Int = Random.between(0,2)
        kopfOderZahl(wurf)
    }
    def test: Unit = {
        
        put(5, "A")
        put(10, "B")
        put(15, "C")
        put(15 ,"l")
        put(20, "D")
        put(25, "E")


        val searchResult = search(15)

        // Ergebnis der Suche ausgeben
        if (searchResult.nonEmpty) {
        println("Suchergebnis:")
        searchResult.foreach(node => println(s"Key: ${node.key}, Value: ${node.value}"))
        } else {
        println("Schlüssel nicht gefunden.")
        }
    }
}

object Main extends App {
    val list = new skipList()
    list.test
}