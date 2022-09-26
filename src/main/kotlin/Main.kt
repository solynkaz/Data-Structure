import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime


fun main(args: Array<String>) {
    var initialList = mutableListOf<Int>()
    var n = 0
    val sc = Scanner(System.`in`)
    try {

        print("Введите кол-во элементов: ")
        n = sc.nextInt()
        for (i in 0 until n) {
            initialList.add(sc.nextInt())
        }
    } catch (exc: java.lang.NumberFormatException) {
        println("Exception, $exc")
    }
    initialList.sort()
    while (true) {
        println("Выберите из списка:\n1. Последовательный поиск\n2. Быстрый последовательный поиск\n3. Бинарный поиск\n4. Вывести массив\n5. Закончить")
        var choice = sc.nextInt()

        when (choice) {
            //Линейный поиск
            1 -> {
                println("Введите искомый элемент")
                val itemToFind = sc.nextInt()
                val elapsedTime = measureNanoTime {
                    for ((i, k) in initialList.withIndex()) {
                        if (k == itemToFind) {
                            println("---------------------------\nУдача. Элемент $itemToFind найден, номер элемента в массиве ${i}.\n---------------------------")
                            break
                        }
                        if (i == n-1) println("---------------------------\nНеудача. Искомый элемент не найден.\n---------------------------")
                    }
                }
            }
            2 -> {
                println("Введите искомый элемент")
                val itemToFind = sc.nextInt()
                val initialListCopy = initialList.toMutableList()
                val elapsedTime = measureNanoTime {
                    initialListCopy.add(itemToFind)
                    for ((i, k) in initialListCopy.withIndex()) {
                        if (k == itemToFind) {
                            if (i > n) {
                                println("---------------------------\nНеудача. Искомый элемент не найден.\n---------------------------")
                                break
                            } else {
                                println("---------------------------\nУдача. Элемент $k найден, номер элемента в массиве ${i}.\n---------------------------")
                                break
                            }
                        }
                    }
                }
                println("TotalTime: $elapsedTime")
            }
            3 -> {
                try {
                    println("Введите искомый элемент")
                    val itemToFind = sc.nextInt()
                    val elapsedTime = measureNanoTime {
                        var index = -1;
                        var low = 0
                        var high = initialList.size
                        while (low <= high) {
                            var mid = (low + high) / 2
                            if (initialList[mid] < itemToFind) {
                                low = mid + 1;
                            } else if (initialList[mid] > itemToFind) {
                                high = mid - 1;
                            } else if (initialList[mid] == itemToFind) {
                                index = mid;
                                break;
                            }
                        }
                        if (low > high) {
                        println("---------------------------\nНеудача.\n---------------------------")
                    } else println("---------------------------\nУспех: Элемент найден с индексом: $index\n---------------------------")
                    }
                    println("TotalTime: $elapsedTime\n")
                } catch (exc: java.lang.Exception) {
                    println("Неудача")
                }
            }
            4-> {
                for (i in initialList) print("$i ")
                println()
            }
            5-> {

                    return
            }
        }
    }
}