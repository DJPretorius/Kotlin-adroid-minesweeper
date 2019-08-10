package com.example.kotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private var yDim = 0
    private var xDim = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showDialog()


    }

    private fun createBoard(mineSweeper: MineSweeper) {
        val buttons = ArrayList<ArrayList<GameButton>>()
        val board = mineSweeper.board
        var count = 0
        for (i in 0 until board.getX()){
            buttons.add(ArrayList())
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

    private fun showDialog(){
        val edtX = EditText(this)
        edtX.inputType = InputType.TYPE_CLASS_NUMBER
        val linLayX = LinearLayout(this)
        linLayX.orientation = LinearLayout.HORIZONTAL
        linLayX.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT )
        val xTitle = TextView(this)
        xTitle.text = getString(R.string.linlayX_title)
        linLayX.addView(xTitle)
        linLayX.addView(edtX)

        val edtY = EditText(this)
        edtY.inputType = InputType.TYPE_CLASS_NUMBER
        val linLayY = LinearLayout(this)
        linLayY.orientation = LinearLayout.HORIZONTAL
        linLayY.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT )
        val yTitle = TextView(this)
        yTitle.text = getString(R.string.yLinLay_title)
        linLayY.addView(yTitle)
        linLayY.addView(edtY)

        val dLogLL = LinearLayout(this)
        dLogLL.orientation = LinearLayout.VERTICAL
        dLogLL.addView(linLayX)
        dLogLL.addView(linLayY)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_text))
        builder.setView(dLogLL)
        builder.setPositiveButton("OK") { _, _ ->
            xDim = edtX.text.toString().toInt()
            yDim = edtY.text.toString().toInt()
            val mineSweeper = MineSweeper(xDim,yDim, (xDim*yDim*0.125).toInt(), this)
            createBoard(mineSweeper)
        }

        builder.setNegativeButton("Cancel"){dialog,_ ->
            dialog.cancel()
        }

        builder.show()

    }
}
