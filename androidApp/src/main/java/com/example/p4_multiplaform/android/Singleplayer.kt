package com.example.p4_multiplaform.android

import android.widget.LinearLayout
import android.widget.TextView

@Suppress("NAME_SHADOWING")
class Singleplayer(context_: android.content.Context,
                   mainLayout_: LinearLayout,
                   screenWidth: Int,
                   currentPlayerEditText_: TextView,
                   currentColorLl_: LinearLayout,
                   messageTextView_: TextView) :
    GameLogic(
        context_,
        mainLayout_,
        screenWidth
    ) {

    private val player1 = "Your Piece Color"
    private val player2 = "Computer"
    private val messageTextView = messageTextView_
    private val currentPlayerEditText = currentPlayerEditText_
    private var currentPieceColor = currentColorLl_

    init {
        currentPlayerEditText.text = player1

        // Initialize the board with 0 and create the empty board
        for (col in 0 until cols){
            val column = mainLayout_.getChildAt(col) as LinearLayout

            // Add click listener
            column.setOnClickListener {
                game(col)
            }
        }
    }

    override fun game(col: Int) {
        if (!ended){ // If the game is not ended
            // Add a piece to the column
            val row = play(col, player)

            if (row != -1) {
                // Update the board
                update(row, col)

                val wonList = isWon(player)
                when {
                    wonList.isNotEmpty() -> {
                        // End the game
                        ended = true

                        // Update the message
                        messageTextView.setText(R.string.won)

                        // Set the layout user locked
                        mainLayout.isEnabled = false

                        highlightWinning(wonList)
                    }
                    isDraw() -> {
                        // End the game
                        ended = true

                        // Set the layout user locked
                        mainLayout.isEnabled = false

                        highlightWinning(ArrayList())

                        // Update the message
                        currentPlayerEditText.text = player2
                        messageTextView.setText(R.string.draw)
                    }
                    else -> {
                        // Switch player between player and computer
                        player = if (player == 1) 2 else 1

                        player = if (player == 2) {
                            // Computer's turn
                            ai(player)

                            //check if the computer won
                            val wonList = isWon(player)
                            if (wonList.isNotEmpty()) {
                                // End the game
                                ended = true

                                // Update the message
                                currentPlayerEditText.text = player2
                                messageTextView.setText(R.string.won)
                                currentPieceColor.setBackgroundResource(R.drawable.circle_yellow)

                                // Set the layout user locked
                                mainLayout.isEnabled = false

                                highlightWinning(wonList)
                            }
                            // Switch to player 1
                            1
                        } else {
                            player
                        }
                    }
                }
            }
            else{
                // Create the toast
                val toast = android.widget.Toast.makeText(
                    context, "Can't play here", android.widget.Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    public override fun reset() {
        mainLayout.isEnabled = true
        ended = false
        clear()
        player = 1
        currentPlayerEditText.text = player1
        messageTextView.text = ""
        currentPieceColor.setBackgroundResource(R.drawable.circle_red)
    }
}