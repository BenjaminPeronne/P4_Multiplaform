package com.example.p4_multiplaform.android

import android.widget.LinearLayout

@Suppress("NAME_SHADOWING")
abstract class GameLogic(context_: android.content.Context, mainLayout_: LinearLayout, screenWidth: Int) {
    private val rows = 6 // number of rows
    protected val cols = 7 // number of columns
    private var board: Array<Array<Int>> = Array(cols) { Array(rows) { 0 } } // board array
    private val pieceWidth = ((screenWidth / cols) * 0.85).toInt() // 85% of the screen width
    private val margin = (screenWidth * 0.005).toInt() // 5% margin
    protected val mainLayout = mainLayout_ // main layout of the game
    protected val context = context_ // context of the activity
    protected var ended = false // Game ended
    protected var player = 1 // 1 = player 1, 2 = player 2

    // This init method is called when the object is created to initialize the board
    init {
        for (i in 0 until cols) {
            // create a linearlayout
            val ll = LinearLayout(context)

            // set the layout params
            val layoutParams1 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            ll.orientation = LinearLayout.VERTICAL
            ll.layoutParams = layoutParams1
            ll.gravity = android.view.Gravity.BOTTOM

            for (j in 0 until rows) {
                // create a linearlayout
                val piece = LinearLayout(context)

                // set the layout params
                val linearParams2 = LinearLayout.LayoutParams(pieceWidth, pieceWidth)
                linearParams2.setMargins(margin, margin, margin, margin)

                piece.layoutParams = linearParams2
                piece.setBackgroundResource(R.drawable.circle)

                // add the piece to the column
                ll.addView(piece)
            }
            mainLayout_.addView(ll)
        }
    }

    // This method is called when a player clicks on a column to drop a piece except if is computer turn
    abstract fun game(col: Int)

    // This method is called when the game ends
    protected fun highlightWinning(wonList: ArrayList<Pair<Int, Int>>) {
        // Browse the board and highlight the winning pieces
        for (c in 0 until cols) {
            for (r in 0 until rows) {

                // If the piece is in the winning list, highlight it
                if (!wonList.any { ( x, y ) -> x == c && y == r }) {
                    var piece: LinearLayout = mainLayout.getChildAt(c) as LinearLayout
                    piece = piece.getChildAt(r) as LinearLayout // get the piece
                    piece.alpha = 0.5f // 50% transparent
                }

            }
        }
    }

    protected fun update(row: Int, col: Int) {
        // Update the board
        val col = mainLayout.getChildAt(col) as LinearLayout

        // Get the piece
        val piece = col.getChildAt(row) as LinearLayout

        // Set the background color to the player's color
        piece.setBackgroundResource(
            if (player == 1) R.drawable.circle_red
            else R.drawable.circle_yellow)
    }

    protected fun clear() {
        // Browse the board and clear the pieces
        for (i in 0 until cols)
            for (j in 0 until rows){
                // Get the piece
                var p: LinearLayout = mainLayout.getChildAt(i) as LinearLayout // Get the column

                p = p.getChildAt(j) as LinearLayout // Get the piece
                p.alpha = 1f // Set the alpha to 1

                board[i][j] = 0 // Clear the board
                val col = mainLayout.getChildAt(i) as LinearLayout // Get the column
                val piece = col.getChildAt(j) as LinearLayout // Get the piece
                piece.setBackgroundResource(R.drawable.circle)
            }
    }

    protected abstract fun reset() // Reset the game to its initial state (for a new game)

    protected fun play(col: Int, player: Int): Int {
        if (this.board[col][0] != 0) { // If the column is full
            return -1
        }

        var row = -1 // The row where the piece will be placed
        for(i in rows - 1 downTo 0) { // Browse the column from the bottom to the top
            if (this.board[col][i] == 0) { // If the piece is empty
                row = i // The row where the piece will be placed
                break // Stop the loop
            }
        }

        if (row == -1) { // If the column is full
            return -1
        }

        // Update the board
        this.board[col][row] = player
        update(row, col)
        return row
    }

    // Basic AI algorithm
    protected fun ai(player: Int): Int {
        var bestScore = -1 // The best score
        var bestCol = -1 // The best column

        // Browse the board
        for (col in 0 until cols) {
            if (this.board[col][0] != 0) { // If the column is full
                continue // Go to the next column
            }

            // Get the score for the current column
            val score = getScore(col, player)

            // If the score is better than the best score
            if (score > bestScore) {
                bestScore = score // Update the best score
                bestCol = col // Update the best column
            }
        }

        // If no column has a better score
        if (bestCol == -1) {
            return -1
        }

        // Play the best column
        return play(bestCol, player)
    }

    // Get the score for a given column
    private fun getScore(col: Int, player: Int): Int {
        var score = 0 // The score

        // Browse the column
        for (i in 0 until rows) {
            // If the piece is empty
            if (this.board[col][i] == 0) {
                score += 1 // Add 1 to the score
            } else {
                // If the piece is not empty and is not the player's piece (the opponent's piece)
                if (this.board[col][i] == player) {
                    score += 2 // Add 2 to the score
                } else {
                    score -= 1 // Subtract 1 from the score
                }
            }
        }
        return score
    }

    protected fun computerTurn(player: Int): Int {

        val col = (0 until cols ).random() // Choose a random column
        //val col = Random().nextInt(cols)

        // Play the computer's turn
        val row = play(col, player)

        // Check if the game ended in a draw
        if (isDraw()) {
            return -1
        }

        // Update the board
        this.board[col][row] = player
        update(row, col)
        return row


        // The computer's turn is over
    }

    protected fun isDraw(): Boolean {
        // Browse the board and check if there is a draw
        for (i in 0 until cols)
            for (j in 0 until rows)
                if (board[i][j] == 0) // If there is a piece on the board, the game is not finished
                    return false
        return true
    }

    private fun checkPlayer(x: Int, y: Int, player: Int): Boolean {
        if (x < 0 || x >= cols || y < 0 || y >= rows) // out of bounds check
            return false

        if (board[x][y] != player) // check if the piece is the player's
            return false

        return true
    }

    protected fun isWon(player: Int): ArrayList<Pair<Int, Int>> {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                // For all directions
                // Right, Down, Diagonal Right, Diagonal Left
                val directions = arrayOf(Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, -1))

                for ((di, dj) in directions) {
                    // Create coordinates following the direction
                    val coordinates = ArrayList<Pair<Int, Int>>() // List of coordinates

                    // Add the coordinates
                    for (k in 0 until 4) coordinates.add(Pair(i + k * di, j + k * dj))

                    // Check if all 4 coordinates belong to the player
                    if (coordinates.all { (x, y) -> checkPlayer(x, y, player) }) return coordinates
                }
            }
        }
        return ArrayList()
    }
}