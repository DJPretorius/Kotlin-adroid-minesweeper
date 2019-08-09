package com.example.kotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*




class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mineSweeper = MineSweeper(15,10,25, this)
        createBoard(mineSweeper)


    }

    private fun createBoard(mineSweeper: MineSweeper) {
        val buttons = ArrayList<ArrayList<GameButton>>()
        val board = mineSweeper.board
        var count = 0
        for (i in 0 until board.getX()){
            buttons.add(ArrayList<GameButton>())
            for(y in 0 until board.getY()){
                count++
                buttons[i].add(GameButton(this,i,y, board.getValue(i,y), mineSweeper, this))
                val params = TableRow.LayoutParams(100,100)
                params.setMargins(0,0,0,0)
                buttons[i][y].layoutParams  = params

            }
        }
        val tableLayout = TableLayout(this)
        tableLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        for(i in 0 until board.getX()) {
            val tableRow = TableRow(this)
            tableRow.layoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT)
            tableLayout.addView(tableRow)
            for(y in 0 until board.getY()){
                tableRow.addView(buttons[i][y])
            }
        }

        val linLayout = findViewById<LinearLayout>(R.id.playground)
        val resetButton = Button(this)
        resetButton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        resetButton.text = getString(R.string.reset_button)
        resetButton.setOnClickListener {
            mineSweeper.resetGame()
        }

        linLayout?.addView(tableLayout)
        linLayout?.addView(resetButton)

    }
}
