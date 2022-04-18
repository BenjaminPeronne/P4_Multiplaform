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
        for (i in 0 until cols) { // for each column
            // create a linearlayout
            val linearLayout = LinearLayout(context)

            // set the layout params
            val layoutParams1 = LinearLayout.LayoutParams( // set the layout params for the linearlayout object
                LinearLayout.LayoutParams.WRAP_CONTENT, // width
                LinearLayout.LayoutParams.MATCH_PARENT // height
            )

            // set the layout params for the buttons in the column
            linearLayout.orientation = LinearLayout.VERTICAL // vertical orientation of the linear layout
            linearLayout.layoutParams = layoutParams1 // set the layout params of the linearlayout  to the layout params of the column
            linearLayout.gravity = android.view.Gravity.BOTTOM // bottom gravity of the layout (column) (the buttons will be at the bottom)

            for (j in 0 until rows) { // for each row
                // create a linearlayout
                val piece = LinearLayout(context) // create a linearlayout for the piece

                // set the layout params
                val linearParams2 = LinearLayout.LayoutParams(pieceWidth, pieceWidth) // set the layout params for the piece
                linearParams2.setMargins(margin, margin, margin, margin) // set the margins of the piece

                piece.layoutParams = linearParams2 // set the layout params of the piece to the layout params of the piece
                piece.setBackgroundResource(R.drawable.circle) // set the background of the piece to the circle image

                // add the piece to the column
                linearLayout.addView(piece) // add the piece to the column
            }
            mainLayout_.addView(linearLayout) // add the column to the main layout
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
                if (!wonList.any { ( x, y ) -> x == c && y == r }) { // if the piece is not in the winning list
                    var piece: LinearLayout = mainLayout.getChildAt(c) as LinearLayout // get the column
                    piece = piece.getChildAt(r) as LinearLayout
                    piece.alpha = 0.5f // 50% transparent
                }
            }
        }
    }

    protected fun update(row: Int, col: Int) {
        // Update the board
        val col = mainLayout.getChildAt(col) as LinearLayout // get the column

        // Get the piece
        val piece = col.getChildAt(row) as LinearLayout // get the piece in the column at the row position

        // set the background of the piece to the player's color
        piece.setBackgroundResource(
            if (player == 1) R.drawable.circle_red
            else R.drawable.circle_yellow)
    }

    protected fun clear() { // clear the board of all pieces and set the background to the default color
        // Browse the board and clear the pieces
        for (i in 0 until cols)
            for (j in 0 until rows){
                // Get the piece
                var p: LinearLayout = mainLayout.getChildAt(i) as LinearLayout // Get the column

                p = p.getChildAt(j) as LinearLayout // Get the piece in the column at the row position
                p.alpha = 1f // Set the alpha to 1 (100% transparent)

                board[i][j] = 0 // Clear the board
                val col = mainLayout.getChildAt(i) as LinearLayout // Get the column in the board
                val piece = col.getChildAt(j) as LinearLayout // Get the piece in the column at the row position
                piece.setBackgroundResource(R.drawable.circle)
            }
    }

    protected abstract fun reset() // Reset the game to its initial state (for a new game)

    protected fun move(col: Int, player: Int): Int {

        //  If the column is full, return -1 to indicate that the move was invalid and the game should continue to the next player (if any)
        if (this.board[col][0] != 0) { // If the column is full return -1
            return -1 // Column is full
        }

        // Find the first empty row in the column and drop the piece there (if any)
        var row = -1 // The row where the piece will be dropped
        for(i in rows - 1 downTo 0) { // Browse the column from the bottom to the top
            if (this.board[col][i] == 0) { // If the row is empty
                row = i // The row where the piece will be placed
                break // Stop the loop
            }
        }

        // If the row is -1, the column is full and the move is invalid
        if (row == -1) { // If the column is full
            return -1
        }

        // Set the piece in the board and in the GUI (if the move is valid)
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
        return move(bestCol, player)
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

    protected fun isDraw(): Boolean {
        // Browse the board and check if there is a draw
        for (i in 0 until cols)
            for (j in 0 until rows)
                if (board[i][j] == 0) // If there is a piece on the board, the game is not finished
                    return false // The game is not finished
        return true // The game is finished in a draw (no more pieces can be placed)
    }


    private fun checkPlayer(x: Int, y: Int, player: Int): Boolean { // Check if the player has won the game or not
        if (x < 0 || x >= cols || y < 0 || y >= rows) // If the piece is out of the board (the player can't win)
            return false // The player can't win

        if (board[x][y] != player) // If the piece is not the player's piece (the opponent's piece)
            return false // The player can't win

        return true // The player has won the game
    }

    protected fun isWon(player: Int): ArrayList<Pair<Int, Int>> { //
        for (i in 0 until rows) {
            for (j in 0 until cols) { // Browse the board
                // For all directions
                // Right, Down, Diagonal Right, Diagonal Left
                val directions = arrayOf(Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, -1))

                for ((direction_i, direction_j) in directions) { // For each direction to check for a win

                    // Create coordinates following the direction
                    val coordinates = ArrayList<Pair<Int, Int>>() // List of coordinates

                    // Add the coordinates to the list following the direction
                    for (k in 0 until 4) coordinates.add(Pair(i + k * direction_i, j + k * direction_j))

                    // Check if all 4 coordinates belong to the player (the player has won)
                    if (coordinates.all { (x, y) -> checkPlayer(x, y, player) }) return coordinates
                }
            }
        }
        return ArrayList() // return an empty list if the player has not won
    }
}