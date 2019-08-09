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
    val xVal : Int
    val yVal : Int
    var clicked = false
    var flagged = false
    var gameValue : Int
    val mineSweeper : MineSweeper
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
        statusVM.stateLD.observe(activity, Observer<Boolean>{ newStatus ->
            if(newStatus){
                this.text = if(gameValue ==-1) {
                    this.setBackgroundColor(Color.RED)
                    "*"
                } else  "$gameValue"
            }else{
                this.background = shapeDrawable
                this.text = " "
                this.gameValue = mineSweeper.board.getValue(xVal,yVal)
            }
        })

        this.setOnClickListener{
            if(!flagged){
                mineSweeper.play(xVal, yVal)
                this.text = if(gameValue == -1) {
                    this.setBackgroundColor(Color.RED)
                    "*"
                }else{
                    "$gameValue"
                }
                clicked = true
            }else{
                flagged = !flagged
            }
        }

        this.setOnLongClickListener{
            if(!clicked){
                if(!flagged) {
                    this.text = "A"
                }else{
                    this.text = " "
                }
                flagged = !flagged
            }
            return@setOnLongClickListener  true
        }
    }

    fun gameOver(){
        if(mineSweeper.isGameOver()){

        }
    }

}