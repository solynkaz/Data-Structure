import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    while (true) {
        val timingMap = mutableListOf(0L,0L,0L)
        val sc = Scanner(System.`in`)
        println("В каком файле ищем?")
        val choice = sc.nextInt()
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
        val isFail = sc.nextInt() == 1
        if (choice == -1) return
        val text: Array<String> = File("data${choice}k.txt").readText().trim().split(" ").toTypedArray()
        val list = mutableListOf<Int>()
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
        timingMap[0] = elapsedTime1
        //timingMap[0] = TimeUnit.MILLISECONDS.convert(elapsedTime1, TimeUnit.NANOSECONDS)
        println(timingMap[0])
        val initialListCopy = list.toMutableList()
        val elapsedTime2 = measureNanoTime {
            val itemToFind = if (isFail) {
                100000000
            } else {
                initialListCopy.last()
            }
            initialListCopy.add(itemToFind)
            for ((i, k) in initialListCopy.withIndex()) {
                if (k == itemToFind) {
                    if (i == initialListCopy.lastIndex) {
                        break
                    } else {
                        break
                    }
                }
            }
        }
        timingMap[1] = elapsedTime2
        //timingMap[1] = TimeUnit.MILLISECONDS.convert(elapsedTime2, TimeUnit.NANOSECONDS)
        println(timingMap[1])



        val binaryList = list.toMutableList()
        binaryList.sort()
        val itemToFind: Int = if (isFail) {
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
                        index = mid
                        break
                    }
                }
            } catch (exc: java.lang.Exception) {
            }
        }
        timingMap[2] = elapsedTime3
        //timingMap[2] = TimeUnit.MILLISECONDS.convert(elapsedTime3, TimeUnit.NANOSECONDS)
        saveRecord(timingMap, choice, isFail)
    }
}

fun saveRecord(timingMap: MutableList<Long>, type: Int, isFail: Boolean) {
    FileOutputStream(File("timing${type}k.txt"), true).bufferedWriter().use { writer ->
        if (!isFail) writer.append("---Поиск удачный---\n")
        else writer.append("---Поиск неудачный---\n")
        writer.append("Последовательный поиск: ${timingMap[0]}\nБыстрый последовательный: ${timingMap[1]}\nБинарный: ${timingMap[2]}\n")
        writer.append("----END----\n")
        writer.flush()
    }
}


