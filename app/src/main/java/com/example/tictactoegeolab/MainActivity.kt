package com.example.tictactoegeolab

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var movesCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Array(9) { i ->
            findViewById(resources.getIdentifier("button$i", "id", packageName))
        }

        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                onButtonClick(i)
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetBoard()
        }
    }

    private fun onButtonClick(index: Int) {
        val row = index / 3
        val col = index % 3

        if (board[row][col] == null) {
            board[row][col] = currentPlayer
            buttons[index].text = currentPlayer
            movesCount++

            if (checkWin()) {
                showToast("$currentPlayer wins!")
                disableBoard()
            } else if (movesCount == 9) {
                showToast("It's a tie!")
            } else {
                switchPlayer()
            }
        }
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
    }

    private fun disableBoard() {
        for (button in buttons) {
            button.isEnabled = false
        }
    }

    private fun resetBoard() {
        for (i in buttons.indices) {
            buttons[i].text = ""
            buttons[i].isEnabled = true
        }
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = null
            }
        }
        currentPlayer = "X"
        movesCount = 0
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}