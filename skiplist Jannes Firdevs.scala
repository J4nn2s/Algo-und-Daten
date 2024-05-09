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
        var n: Node = head
        while (n != null) do {
            while (n.succ.key <= key) do {
                n = n.succ
            }
            Q :+= n  // still need to look at that 
            n = n.down
        }
        for (node <- Q) do {
            println(s"Key: ${node.key}, Value: ${node.value}")
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
        while (muenzwurf == "Kopf") do {
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
    }
    // still work left to be done here
    private def remove(k: Int): Unit = {
        var Q: List[Node] = search(k)
        var n: Node = Q.last
        println(s"${n.key}")
        
        if (n.key != k) {
            throw Exception("Element gibt es nicht")
        }
        while (n != null && n.key ==k) do {
            n.pred.succ = n.succ // neue Links setzen
            n.succ.pred = n.pred
            n = n.down
        }
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
        print("-------SEARCH START von 15-------")
        search(15)
        search(25)
        println("-------SEARCH START von 25-------")
        printAllKeysAndVals()
        remove(15)
        println("15 entfernt")
        printAllKeysAndVals()

        println()
        printSkiplist()

    }


    // in Empfehlung von Kommilitonen :)
    def printSkiplist(): Unit = {
        print("So sehen die keys aus \n")
        var start: Node = head
        while (start != null) {
            var current: Node = start
            while (current != null) {
                print(s"${current.key} ")
                current = current.succ
            }
            start = start.down
            println()
        }
    }
    def printAllKeysAndVals(): Unit = {
        var start: Node = head
        while (start != null) {
            print(s"Key: ${start.key} + Val: ${start.value}")
            println("")
            start = start.succ
        }
    
    }
}

object Main extends App {
    val list = new skipList()
    list.test
}