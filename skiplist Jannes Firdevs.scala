import scala.util.Random
// Firdevs Ugur und Jannes Glaubitz
// chatgpt was used to create the cool test object :) & for many compiling errors

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
            while (n.succ.key <= key && n.succ != null) do {
                n = n.succ
            }
            Q = n :: Q  // Hier wird der Knoten n an die Liste Q vorne angefügt
            n = n.down
        }
        return Q
    }   

    private def put(k: Int, v: Any): Unit = {
        var Q: List[Node] = search(k)
        var n: Node = Q.last // aus abstract class list -> last , right etc
        if (n.key == k) then { // Wert updaten , wenn k bereits vorhanden
            println("Ich habe erkannt das der Wert gleich ist")
            for (i <- Q) {
                if (i.key == k) {
                    i.value = v
                }
            }
            return
        }
        var newNode = Node(k, v, n, n.succ, null) // neuer Knoten mit pred "n"
        n.succ.pred = newNode
        if (Q.length >1){
            n.pred.succ = newNode // n also der Knoten davor ist jetzt der Vorgänger von newNode und der newNode der Nachfolger von n
        }
        while (muenzwurf == "Kopf") do {
            if (Q.isEmpty) then {
                var headNew = Node(Int.MinValue, null, null, newNode, head)
                var bottomNew = Node(Int.MaxValue, null, newNode, null, bottom)
                
                headNew.succ = newNode
                bottomNew.pred = newNode
                head = headNew // experiment, damit bei nächsten insert Vorgängen auf diese heads / bottoms verwiesen wird 
                bottom = bottomNew
            }
            else { // Q was not empty, also newNode einfügen in bestehende Liste nach n
                var n : Node = Q.last
                val newNodeAfter = Node(k, v, n, n.succ, newNode)

                n.succ.pred = newNodeAfter
                n.succ = newNodeAfter
            }
        }
    }
    // still work left to be done here
    private def remove(k: Int): Unit = {
        var q = search(k)
        var n = q.last

        if (n.key != k)
        throw new NoSuchElementException

        q = q.init // vorimplementierte removeLast[A] Funktion

        while (n != null && n.key == k) {
            n.pred.succ = n.succ
            n.succ.pred = n.pred
            n = n.down

            if (q.nonEmpty)
                n = q.last
            else
                n = null
        }

        while (head.key == Int.MinValue && head.succ.key == Int.MaxValue && head.down != null) do {
            head = head.down
        }
     }

    def muenzwurf: String = {
        val kopfOderZahl: List[String] = List("Kopf", "Zahl") 
        val wurf: Int = Random.between(0,2)
        kopfOderZahl(wurf)
    }
    def test: Unit = {
        // print(r.last.key)
        put(15, "C")
        // printSkiplist()
        printAllKeysAndVals()
        // search(15, "C")
        
        put(20, "D")
        put(25, "E")
        put(15, "l")
        println()
        printAllKeysAndVals()

        // var r = search(15)
        // for (i <- r) {
        //     println(i.key)
        // }
        // println()
        // println(r.last.key)
        // println()
        // println(r.last.value)

        
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