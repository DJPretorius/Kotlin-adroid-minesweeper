package com.example.kotlintest

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class GameButton: Button {
    private val xVal : Int
    private val yVal : Int
    private var clicked = false
    private var flagged = false
    private var gameValue : Int
    private val mineSweeper : MineSweeper
    private var statusVM : StatusViewModel

    constructor(context: Context, xVal : Int, yVal : Int, value: Int, mineSweeper: MineSweeper, activity: MainActivity) : super(context){
        this.xVal = xVal
        this.yVal = yVal
        this.gameValue = value
        this.text = " "
        this.mineSweeper = mineSweeper

        val shapeDrawable = ShapeDrawable()
        shapeDrawable.shape = RectShape()
        shapeDrawable.paint.color = Color.BLACK
        shapeDrawable.paint.strokeWidth = 8f
        shapeDrawable.paint.style = Paint.Style.STROKE
        this.background = shapeDrawable
        this.setPadding(0,0,0,0)

        statusVM = ViewModelProviders.of(activity).get(StatusViewModel::class.java)
        statusVM.gameOver.observe(activity, Observer<Boolean>{ gameOver ->
            if(gameOver){
                this.text = when (gameValue) {
                    -1 -> {
                        this.setBackgroundColor(Color.RED)
                        "*"
                    }
                    0 -> " "
                    else -> "$gameValue"
                }
            }else{
                this.background = shapeDrawable
                this.text = " "
                this.gameValue = mineSweeper.board.getValue(xVal,yVal)
                this.clicked = false
                this.flagged = false
            }
        })

        statusVM.statusChanged.observe(activity, Observer {
            if(!clicked) {
                if (mineSweeper.board.status[xVal][yVal]) {
                    this.text = if (gameValue == -1) {
                        "A"
                    } else {
                        if(gameValue == 0){
                            this.setBackgroundColor(activity.getColor(R.color.zero_button))
                            " "
                        }else{
                            "$gameValue"
                        }
                    }
                    clicked = true
                }
            }
        })

        this.setOnClickListener{
            if(!flagged) {
                mineSweeper.open(xVal, yVal)
                this.text = if (gameValue == -1) {
                    this.setBackgroundColor(Color.RED)
                    "*"
                } else {
                    if(gameValue == 0){
                        this.setBackgroundColor(activity.getColor(R.color.zero_button))
                        " "
                    }else{
                        "$gameValue"
                    }
                }
                clicked = true
            }
        }

        this.setOnLongClickListener{
            if(!clicked) {
                if (!flagged) {
                    this.text = "A"
                } else {
                    this.text = " "
                }
                mineSweeper.mark(xVal, yVal)
                flagged = !flagged
            }
            return@setOnLongClickListener  true
        }
    }

}