package connectfour

import kotlin.system.exitProcess

fun main() {
    println("Connect Four")
    println("First player's name:")
    val player1 = readln()
    println("Second player's name:")
    val player2 = readln()
    var board: Board
    do {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        board = Board(readln())
    } while (!board.isValid)

    multiple(board, player1, player2)

}

fun parameters(board: Board, player1: String, player2: String) {
    println("$player1 VS $player2")
    println("${board.rows} X ${board.columns} board")
}

fun gameProcess2(board: Board, player1: String, player2: String, option: Int) {
    board.buildBoard(board.columns, board.boardList)

    while (true) {

        do {
            board.turns(
                turnChar = 42,
                player = player2,
                boardList = board.boardList,
                columns = board.columns
            )
            val winConditions = board.winConditions(
                turnChar = '*',
                player = player2,
                boardList = board.boardList,
                rows = board.rows,
                columns = board.columns
            )

            if (winConditions.isNotEmpty()) {
                println(winConditions)
                when (option) {
                    1 -> stop()

                    2 -> {
                        if (winConditions != "It is a draw") {
                            board.secondPlayerScore += 2
                        } else {
                            board.secondPlayerScore += 1
                            board.firstPlayerScore += 1
                        }
                        return

                    }
                }
            }

        } while (board.message.isNotEmpty())

        do {
            board.turns(
                turnChar = 111,
                player = player1,
                boardList = board.boardList,
                columns = board.columns
            )

            val winConditions = board.winConditions(
                turnChar = 'o',
                player = player1,
                boardList = board.boardList,
                rows = board.rows,
                columns = board.columns
            )
            if (winConditions.isNotEmpty()) {
                println(winConditions)
                when (option) {
                    1 -> stop()

                    2 -> {
                        if (winConditions != "It is a draw") {
                            board.firstPlayerScore += 2
                        } else {
                            board.secondPlayerScore += 1
                            board.firstPlayerScore += 1
                        }
                        return

                    }
                }
            }

        } while (board.message.isNotEmpty())
    }
}

fun gameProcess1(board: Board, player1: String, player2: String, option: Int) {

    board.buildBoard(board.columns, board.boardList)

    while (true) {

        do {
            board.turns(
                turnChar = 111,
                player = player1,
                boardList = board.boardList,
                columns = board.columns
            )
            val winConditions = board.winConditions(
                turnChar = 'o',
                player = player1,
                boardList = board.boardList,
                rows = board.rows,
                columns = board.columns
            )
            if (winConditions.isNotEmpty()) {
                println(winConditions)
                when (option) {
                    1 -> stop()

                    2 -> {
                        if (winConditions != "It is a draw") {
                            board.firstPlayerScore += 2
                        } else {
                            board.secondPlayerScore += 1
                            board.firstPlayerScore += 1
                        }
                        return

                    }
                }
            }

        } while (board.message.isNotEmpty())

        do {
            board.turns(
                turnChar = 42,
                player = player2,
                boardList = board.boardList,
                columns = board.columns
            )
            val winConditions = board.winConditions(
                turnChar = '*',
                player = player2,
                boardList = board.boardList,
                rows = board.rows,
                columns = board.columns
            )

            if (winConditions.isNotEmpty()) {
                println(winConditions)
                when (option) {
                    1 -> stop()

                    2 -> {
                        if (winConditions != "It is a draw") {
                            board.secondPlayerScore += 2
                        } else {
                            board.secondPlayerScore += 1
                            board.firstPlayerScore += 1
                        }
                        return

                    }
                }
            }

        } while (board.message.isNotEmpty())
    }

}

fun multiple(board: Board, player1: String, player2: String) {

    do {

        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter\nInput a number of games:")

        var numberOfGames = readln()

        if (numberOfGames == "") {
            numberOfGames = "1"
        }

        var isValid = true

        try {

            when (numberOfGames.toInt()) {
                1 -> {
                    parameters(board, player1, player2)

                    println("Single game")

                    gameProcess1(board, player1, player2, 1)

                    stop()
                }

                !in 1..Int.MAX_VALUE -> {

                    println("Invalid input")
                    isValid = false
                }

                else -> {

                    parameters(board, player1, player2)
                    println("Total $numberOfGames games")

                    while (board.gameCount != numberOfGames.toInt() + 1) {
                        println("Game #${board.gameCount}")

                        when {
                            board.gameCount % 2 != 0 -> gameProcess1(board, player1, player2, 2)

                            else -> gameProcess2(board, player1, player2, 2)
                        }

                        board.boardList = MutableList(board.rows) { MutableList(board.columns) { ' ' } }

                        println("Score\n$player1: ${board.firstPlayerScore} $player2: ${board.secondPlayerScore}")

                        board.gameCount++
                    }
                    stop()

                }
            }

        } catch (e: NumberFormatException) {
            println("Invalid input")

            isValid = false
        }
    } while (!isValid)

}

fun stop() {
    println("Game over!")
    exitProcess(0)
}