import java.io.FileWriter

fun main(args: Array<String>) {
    makeRandomFiles(1)
    makeRandomFiles(10)
    makeRandomFiles(30)
    makeRandomFiles(50)
    makeRandomFiles(100)

}

fun makeRandomFiles(k : Int) {
    val writer = FileWriter("data${k}k.txt")
    var arr = mutableListOf<Int>()
    for (i in 0..k*1000) {
        arr.add(i)
        arr.add(i*-1)
    }
    arr.shuffle()
    for (i in arr) writer.append("$i ")
    writer.flush()
}