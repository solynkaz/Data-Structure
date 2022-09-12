import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    var initialList = mutableListOf<Int>()
    println("Введите кол-во элементов")
    var n = readLine()!!.toInt()
    for (i in 0 until n!!) {
        initialList.add(readLine()!!.toInt())
    }
    initialList.sort()
    while (true) {
        println("Выберите из списка:\n1. Последовательный поиск\n2. Быстрый последовательный поиск\n3. Бинарный поиск\n4. Закончить")
        var choice = readLine()!!.toInt()
        println("Введите искомый элемент")
        var itemToFind = readLine()!!.toInt()

        when (choice) {
            //Линейный поиск
            1 -> {
                val elapsedTime = measureNanoTime {
                    for ((i, k) in initialList.withIndex()) {
                        if (k == itemToFind) {
                            println("Удача. Элемент $i найден, номер элемента в массиве ${k + 1}.")
                            break
                        }
                        if (k == n - 1) println("Неудача. Искомый элемент не найден.")
                    }
                }
                println("TotalTime: $elapsedTime")
            }

            2 -> {
                val elapsedTime = measureNanoTime {
                    initialList.add(itemToFind)
                    for ((i, k) in initialList.withIndex()) {
                        if (i == itemToFind) {
                            if (i > n) {
                                println("Неудача. Искомый элемент не найден.")
                                break
                            } else {
                                println("Удача. Элемент $i найден, номер элемента в массиве ${k + 1}.")
                                break
                            }
                        }
                    }
                }
                println("TotalTime: $elapsedTime")

            }

            3 -> {
                //Сортировка
                var right: Int
                val elapsedTime = measureNanoTime {
                    var middleItem = 0
                    var left = 0
                    right = n
                    while (left <= right) {
                        middleItem = (left + right) / 2
                        if (itemToFind < initialList[middleItem]) right = middleItem - 1
                        else if (itemToFind > initialList[middleItem])
                            left = middleItem + 1
                        else {
                            println("Удача. Позиция: ${middleItem + 1}")
                            break
                        }
                        if (left > right) {
                            println("Неудача")
                            break
                        }
                    }
                }
                println("TotalTime: $elapsedTime")
            }
            4 -> {
                return
            }
        }
    }
}