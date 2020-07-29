@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val arr = str.split(" ")
    val dateStrInDigits: String
    if (arr.size != 3) return ""
    val mapOfMonth = mapOf(
        "декабря" to Pair(12, 31), "января" to Pair(1, 31), "февраля" to Pair(2, 28),
        "марта" to Pair(3, 31), "апреля" to Pair(4, 30), "мая" to Pair(5, 31),
        "июня" to Pair(6, 30), "июля" to Pair(7, 31), "августа" to Pair(8, 31),
        "сентября" to Pair(9, 30), "октября" to Pair(10, 31), "ноября" to Pair(11, 30)
    )
    try {
        val day = arr[0].toInt()
        val month = mapOfMonth[arr[1].toLowerCase()]?.first ?: throw Exception()
        val daysInMonth = mapOfMonth[arr[1].toLowerCase()]?.second ?: throw Exception()
        val year = arr[2].toInt()
        dateStrInDigits = when {
            month == 2 && ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) && day > 29 -> throw Exception()
            day > daysInMonth -> throw Exception()
            else -> String.format("%02d.%02d.%02d", day, month, year)
        }
    } catch (e: Exception) {
        return ""
    }
    return dateStrInDigits
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val arr = digital.split(".")
    val dateStr: String
    if (arr.size != 3) return ""
    val mapOfMonth = mapOf(
        12 to Pair("декабря", 31), 1 to Pair("января", 31), 2 to Pair("февраля", 28),
        3 to Pair("марта", 31), 4 to Pair("апреля", 30), 5 to Pair("мая", 31),
        6 to Pair("июня", 30), 7 to Pair("июля", 31), 8 to Pair("августа", 31),
        9 to Pair("сентября", 30), 10 to Pair("октября", 31), 11 to Pair("ноября", 30)
    )
    try {
        val day = arr[0].toInt()
        val month = mapOfMonth[arr[1].toInt()]?.first ?: throw Exception()
        val daysInMonth = mapOfMonth[arr[1].toInt()]?.second ?: throw Exception()
        val year = arr[2].toInt()
        dateStr = when {
            arr[1].toInt() == 2 && ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) && day > 29 -> throw Exception()
            day > daysInMonth -> throw Exception()
            else -> "$day $month $year"
        }
    } catch (e: Exception) {
        return ""
    }
    return dateStr
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    var telephoneNumber = ""
    val match = phone.matches(Regex("""^\+?\d*(\s*|-*)*(\((\d+(\s*|-*)*)+\))?((\s*|-*)*\d)+$"""))
    if (match) {
        telephoneNumber = Regex("""\+?\d+""").findAll(phone).joinToString("") { it.value }
    }
    return telephoneNumber
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var maxJump = -1
    val match = jumps.matches(Regex("""^(-|%|\d+)(\s(-|%|\d+))*$"""))
    if (match) {
        maxJump = Regex("""\d+""").findAll(jumps).maxBy { it.value }?.value?.toInt() ?: -1
    }
    return maxJump
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int = TODO()

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var sign = 1
    var sumValue = 0
    val match = expression.matches(Regex("""^\d+(\s(-|\+|)\s\d+)*$"""))
    if (match) {
        expression.split(" ").forEach {
            when (it) {
                "+" -> sign = 1
                "-" -> sign = -1
                else -> sumValue += sign * it.toInt()
            }
        }
    } else {
        throw IllegalArgumentException()
    }
    return sumValue
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var indexOfRepeatedWord = 0
    var previousWord = ""
    str.split(" ").forEach {
        indexOfRepeatedWord += it.length + 1
        if (it.toLowerCase() == previousWord.toLowerCase()) return indexOfRepeatedWord - (it.length + 1) * 2
        else previousWord = it
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String = TODO()

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val mapRoman =
        listOf(
            1000 to "M", 900 to "CM", 500 to "D", 400 to "CD", 100 to "C", 90 to "XC", 50 to "L", 40 to "XL",
            10 to "X", 9 to "IX", 5 to "V", 4 to "IV", 1 to "I"
        )
    if (!roman.matches(Regex("""^[MDCLXVI]+$"""))) return -1
    var result = 0
    var romanString = roman
    var i = 0
    while (romanString.isNotEmpty() && i < mapRoman.size) {
        val symbol = mapRoman[i]
        if (romanString.startsWith(symbol.second)) {
            result += symbol.first
            romanString = romanString.substring(symbol.second.length)
        } else {
            i++
        }
    }


//    mapRoman.forEach { (arab, roman) ->
//        while (num >= arab) {
//            result += roman
//            num -= arab
//        }
//    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val listOfCells = List(cells) { 0 }.toMutableList()
    // проверить корректность символов в строке commands
    if (!commands.matches(Regex("""^[\[ ><+\-\]]*$"""))) throw IllegalArgumentException()
    // проверить корректность расставленных скобок в строке commands
    val listOfBrackets = mutableListOf<Char>()
    for (i in commands) {
        when (i) {
            '[' -> listOfBrackets.add(i)
            ']' -> {
                if (listOfBrackets.isEmpty()) throw IllegalArgumentException()
                listOfBrackets.removeAt(0)
            }
        }
    }
    if (listOfBrackets.isNotEmpty()) throw IllegalArgumentException()
    var i = 0                                       // позиция в строке
    var countOperations = 0                         // общий счетчик выполненных операций
    val listOfMissedLoops = mutableListOf<Char>()   // список для пропуска циклов
    val listOfStartLoop = mutableListOf<Int>()      // список стартов циклов для последующего возвращения на старт цикла
    var currentCell = cells / 2                     // стартовая ячейка
    while (i < commands.length && countOperations < limit) {
        when {
            commands[i] == '[' && listOfMissedLoops.isNotEmpty() -> {
                listOfMissedLoops.add('[')
                countOperations--
            }
            commands[i] == ']' && listOfMissedLoops.isNotEmpty() -> {
                listOfMissedLoops.removeAt(0)
                countOperations--
            }
            listOfMissedLoops.isNotEmpty() -> countOperations--
            commands[i] == '<' -> currentCell--
            commands[i] == '>' -> currentCell++
            commands[i] == '+' -> listOfCells[currentCell]++
            commands[i] == '-' -> listOfCells[currentCell]--
            commands[i] == ']' && listOfCells[currentCell] != 0 -> {
                i = listOfStartLoop.takeLast(1)[0]
            }
            commands[i] == '[' && listOfCells[currentCell] == 0 -> {
                listOfMissedLoops.add('[')
            }
            commands[i] == '[' -> listOfStartLoop.add(i)
            commands[i] == ']' -> listOfStartLoop.removeAt(listOfStartLoop.lastIndex)
        }
        i++
        countOperations++
        if (currentCell !in 0 until cells) throw IllegalStateException() // если вышли за рамки конвейера
    }
    return listOfCells
}
