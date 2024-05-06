file:///C:/Users/janne/OneDrive/Desktop/SoSe2024/AuD/Blätter/skiplist%20Jannes%20Firdevs.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.3\scala3-library_3-3.3.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.12\scala-library-2.13.12.jar [exists ]
Options:



action parameters:
offset: 335
uri: file:///C:/Users/janne/OneDrive/Desktop/SoSe2024/AuD/Blätter/skiplist%20Jannes%20Firdevs.scala
text:
```scala
class skiplist:
    private class Node(key: Int, var value: Any, var pred: Node, var succ: Node, var down: Node)
    private val head = Node(Int.MinValue, null, null, null, null)
    private val bottom = Node(Int.MaxValue, null, null, null, null)
    head.succ = bottom
    bottom.pred = head

    def search(key: Int): List[INt@@





    search(k)
Q <- new Stack
n <- -INFTY pseudonode of highest list
do
while n.next.k <= k do
n <- n.next
Q.push(n)
n <- n.down
while n != NULL
return Q
```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2607)
	scala.meta.internal.pc.SignatureHelpProvider$.isValid(SignatureHelpProvider.scala:83)
	scala.meta.internal.pc.SignatureHelpProvider$.notCurrentApply(SignatureHelpProvider.scala:94)
	scala.meta.internal.pc.SignatureHelpProvider$.$anonfun$1(SignatureHelpProvider.scala:48)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile(LinearSeq.scala:280)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile$(LinearSeq.scala:278)
	scala.collection.immutable.List.dropWhile(List.scala:79)
	scala.meta.internal.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:48)
	scala.meta.internal.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:414)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner