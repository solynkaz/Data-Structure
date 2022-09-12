import kotlin.random.Random

fun main(args: Array<String>) {
    var list1 = mutableListOf(1,2,3)
    var list2 = list1.toMutableList()

    println("DO: ${list1[0]}")
    list2[0] = 10
    println("POSLE: ${list1[0]}")

}