import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    while (true) {
        var timingMap = mutableMapOf<String, Long>()
        timingMap["linear"] = 0L
        timingMap["flinear"] = 0L
        timingMap["binary"] = 0L
        val sc = Scanner(System.`in`)
        println("В каком файле ищем?")
        var choice = sc.nextInt()
        println("Поиск удачный?")
        var isFail = sc.nextInt() == 1
        if (choice == -1) return
        val text: Array<String> = File("data${choice}k.txt").readText().split(" ").toTypedArray()
        println(text[0])
        val elapsedTime1 = measureNanoTime {
            val itemToFind =
                if (!isFail) {
                    text.last()
                }
                else {
                    "fail"
                }
            for ((i, k) in text.withIndex()) {
                if (k == itemToFind) {
                    break
                }
            }
        }
        timingMap["linear"] = elapsedTime1
        //timingMap["linear"] = TimeUnit.MILLISECONDS.convert(elapsedTime1, TimeUnit.NANOSECONDS)
        val elapsedTime2 = measureNanoTime {
            val itemToFind =
            if (!isFail) {
                text.last()
            }
            else {
                "fail"
            }
            val initialListCopy = text.toMutableList()
            initialListCopy.add(itemToFind)
            for ((i, k) in initialListCopy.withIndex()) {
                if (k == itemToFind) {
                    if (i > text.size) {
                        break
                    } else {
                        break
                    }
                }
            }
        }
        timingMap["flinear"] = elapsedTime2
        //timingMap["flinear"] = TimeUnit.MILLISECONDS.convert(elapsedTime2, TimeUnit.NANOSECONDS)
        val elapsedTime3 = measureNanoTime {
            var index = -1;
            val itemToFind =
                if (!isFail) {
                    text.last()
                }
                else {
                    "fail"
                }
            var low = 0
            var high = text.size
            while (low <= high) {
                var mid = (low + high) / 2
                if (text[mid] < itemToFind) {
                    low = mid + 1;
                } else if (text[mid] > itemToFind) {
                    high = mid - 1;
                } else if (text[mid] == itemToFind) {
                    index = mid;
                    break;
                }
            }
        }
        timingMap["binary"] = elapsedTime3
        //timingMap["binary"] = TimeUnit.MILLISECONDS.convert(elapsedTime3, TimeUnit.NANOSECONDS)
        saveRecord(timingMap, choice)
    }
}

fun saveRecord(timingMap: MutableMap<String, Long>, type: Int) {
    FileOutputStream(File("timing${type}k.txt"), true).bufferedWriter().use { writer ->
        writer.append("${timingMap["linear"]}         ${timingMap["flinear"]}         ${timingMap["binary"]}\n")
        writer.flush()
    }
}


