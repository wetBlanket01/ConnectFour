package connectfour

class Board(boardParameters: String) {

    var firstPlayerScore = 0
    var secondPlayerScore = 0
    var gameCount = 1

    var isValid = true
    var message = "Invalid input"
    var rows: Int = 6
    var columns: Int = 7
    private val splitBoard = boardParameters
        .replace("\\s".toRegex(), "")
        .split("x", ignoreCase = true)

    init {

        when (splitBoard.size) {
            1 -> isValid = splitBoard[0].isEmpty()

            2 -> {
                try {
                    if (splitBoard.isNotEmpty()) {
                        rows = splitBoard.first().toInt()
                        columns = splitBoard.last().toInt()
                    }
                    if (rows !in 5..9) {
                        message = "Board rows should be from 5 to 9"
                        isValid = false
                    }
                    if (columns !in 5..9) {
                        message = "Board columns should be from 5 to 9"
                        isValid = false
                    }

                } catch (e: NumberFormatException) {
                    isValid = false
                }

            }
            else -> isValid = false
        }
        if (!isValid) {
            println(message)
        }
    }

    var boardList = MutableList(rows) { MutableList(columns) { ' ' } }
    fun buildBoard(columns: Int, boardList: MutableList<MutableList<Char>>) {
        val endStr: String = "═╩".repeat(columns - 1)
        for (i in 1..columns) {
            print(" $i")
        }
        println()
        for (i in boardList.indices) {
            println("║${boardList[i].joinToString("║", "", "")}║")

        }

        print("╚${endStr}═╝\n")

    }

    fun turns(
        turnChar: Int,
        player: String,
        boardList: MutableList<MutableList<Char>>,
        columns: Int,
    ) {
        message = ""
        println("$player's turn:")
        val turn = readln()
        try {
            var lastIndex = boardList.lastIndex
            val count = 1

            if (turn.toInt() !in 1..columns) {
                message = "The column number is out of range (1 - $columns)"

            } else {

                if (boardList[lastIndex][turn.toInt() - count] != ' ') {
                    do {
                        lastIndex--

                    } while (boardList[lastIndex][turn.toInt() - count] != ' ')
                }

                boardList[lastIndex][turn.toInt() - count] = turnChar.toChar()

                buildBoard(columns, boardList)

            }

        } catch (e: NumberFormatException) {
            if (turn == "end") {
                stop()
            } else {
                message = "Incorrect column number"
            }
        } catch (e: java.lang.IndexOutOfBoundsException) {
            message = "Column $turn is full"
        }
        if (message.isNotEmpty()) {
            println(message)

        }

    }

    fun winConditions(
        boardList: MutableList<MutableList<Char>>,
        rows: Int,
        columns: Int,
        turnChar: Char,
        player: String,
    ): String {

        val winString = "$turnChar, $turnChar, $turnChar, $turnChar"

        var verticalString = ""
        var verticalCount = 0

        while (verticalString.replace(".", "").length != rows * columns) {

            for (i in 1..rows) {
                verticalString += boardList[i - 1][verticalCount]

            }

            verticalString += "."
            verticalCount++
        }

        var diagonals = ""

        var diagonalCount = 0
        var count = 0
        while (count != rows) {

            for (i in 1..rows) {
                diagonals += boardList[diagonalCount][i - 1]

                diagonalCount++

                if (diagonalCount == rows) break

            }
            diagonals += "."
            count++

            diagonalCount = count

        }
        diagonalCount = 0
        count = 0
        while (diagonalCount != rows) {

            for (i in 1..rows) {
                diagonals += boardList[diagonalCount][i]

                if (diagonalCount == rows - 1) break
                if (i == rows - 1) break

                diagonalCount++

            }
            diagonals += "."
            count++

            diagonalCount = count

        }

        var lastIndex = boardList.lastIndex
        count = 0
        var secondDiagonalString = ""

        while (count != rows) {
            for (i in 1..rows) {
                secondDiagonalString += boardList[lastIndex][i - 1 + count]

                if (lastIndex == 0) break
                if (i - 1 + count == rows - 1) break

                lastIndex--
            }
            secondDiagonalString += "."
            count++
            lastIndex = boardList.lastIndex

        }

        count = 0
        lastIndex = boardList.lastIndex - 1

        while (count != rows - 1) {

            for (i in 1..rows) {
                secondDiagonalString += boardList[lastIndex][i - 1]

                if (lastIndex == 0) break
                if (i - 1 == rows - 1) break

                lastIndex--

            }
            secondDiagonalString += "."
            count++
            lastIndex = boardList.lastIndex - 1 - count

        }

        diagonals += secondDiagonalString

        return if (boardList.joinToString().contains(winString)
            || verticalString.contains(winString.replace(", ", ""))
            || diagonals.contains(winString.replace(", ", ""))
        ) {
            "Player $player won"
        } else if (boardList.count { it.contains(' ') } == 0) {
            "It is a draw"
        } else {
            ""
        }

    }

}
