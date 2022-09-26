import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
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
        if (choice == 0) return
        else if (choice == 999) {
            //Очистить файлы
            var file = File("timing1k.txt")
            file.delete()
            file = File("timing10k.txt")
            file.delete()
            file = File("timing30k.txt")
            file.delete()
            file = File("timing50k.txt")
            file.delete()
            file = File("timing100k.txt")
            file.delete()
            break
        }
        println("Поиск неудачный?")
        var isFail = sc.nextInt() == 1
        if (choice == -1) return
        val text: Array<String> = File("data${choice}k.txt").readText().trim().split(" ").toTypedArray()
        var list = mutableListOf<Int>()
        for (line in text) {
            list.add(line.toInt())
        }
        val elapsedTime1 = measureNanoTime {
            val itemToFind =
                if (isFail) {
                    100000000
                } else {
                    list.last()
                }
            for ((i, k) in list.withIndex()) {
                if (k == itemToFind) {
                    break
                }
            }
        }
        timingMap["linear"] = elapsedTime1
        //timingMap["linear"] = TimeUnit.MILLISECONDS.convert(elapsedTime1, TimeUnit.NANOSECONDS)

        val initialListCopy = list
        val elapsedTime2 = measureNanoTime {
            val itemToFind = if (isFail) {
                100000000
            } else {
                initialListCopy.last()
            }
            initialListCopy.add(itemToFind)
            for ((i, k) in initialListCopy.withIndex()) {
                if (k == itemToFind) {
                    if (i > initialListCopy.size) {
                        break
                    } else {
                        break
                    }
                }
            }
        }
        timingMap["flinear"] = elapsedTime2
        //timingMap["flinear"] = TimeUnit.MILLISECONDS.convert(elapsedTime2, TimeUnit.NANOSECONDS)

        var binaryList = list.toMutableList()
        binaryList.sort()
        var itemToFind: Int = if (isFail) {
            100000000
        } else {
            binaryList.last()
        }
        val elapsedTime3 = measureNanoTime {
            try {
                var index = -1;
                var low = 0
                var high = binaryList.size
                while (low <= high) {
                    var mid = (low + high) / 2
                    if (binaryList[mid] < itemToFind) {
                        low = mid + 1;
                    } else if (binaryList[mid] > itemToFind) {
                        high = mid - 1;
                    } else if (binaryList[mid] == itemToFind) {
                        index = mid;
                        break;
                    }
                }
            } catch (exc: java.lang.Exception) {
            }
        }
        timingMap["binary"] = elapsedTime3
        //timingMap["binary"] = TimeUnit.MILLISECONDS.convert(elapsedTime3, TimeUnit.NANOSECONDS)
        saveRecord(timingMap, choice, isFail)
    }
}

fun saveRecord(timingMap: MutableMap<String, Long>, type: Int, isFail: Boolean) {
    FileOutputStream(File("timing${type}k.txt"), true).bufferedWriter().use { writer ->
        if (!isFail) writer.append("---Поиск удачный---\n")
        else writer.append("---Поиск неудачный---\n")
        writer.append("Последовательный поиск: ${timingMap["linear"]}\nБыстрый последовательный: ${timingMap["flinear"]}\nБинарный: ${timingMap["binary"]}\n")
        writer.append("----END----\n")
        writer.flush()
    }
}


