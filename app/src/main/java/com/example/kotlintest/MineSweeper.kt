package com.example.kotlintest

import androidx.lifecycle.ViewModelProviders

class MineSweeper {
    var board : Board
    val xDimension : Int
    val yDimension: Int
    val numBombs : Int
    val statusViewModel : StatusViewModel
    val activity : MainActivity


    constructor(xDim: Int, yDim : Int, numBombs : Int, mainActivity: MainActivity){
        board = Board(xDim, yDim, numBombs, mainActivity)
        this.xDimension = xDim
        this.yDimension = yDim
        this.numBombs = numBombs
        activity = mainActivity
        statusViewModel = ViewModelProviders.of(mainActivity).get(StatusViewModel::class.java)

    }

    fun open(xDim: Int, yDim: Int) : Int{
        if(!board.status[xDim][yDim]) {
            val played = board.open(xDim, yDim)
            if (played == 0) {
                when (xDim) {
                    0 -> {        //Left column
                        this.open(xDim + 1, yDim)
                    }
                    xDimension - 1 -> {        //Right column
                        this.open(xDim - 1, yDim)
                    }
                    else -> {
                        this.open(xDim + 1, yDim)
                        this.open(xDim - 1, yDim)
                    }
                }
                when (yDim) {
                    0 -> {            //Top row
                        this.open(xDim, yDim + 1)

                    }
                    yDimension - 1 -> {        //Bottom row
                        this.open(xDim, yDim - 1)
                    }
                    else -> {
                        this.open(xDim, yDim - 1)
                        this.open(xDim, yDim + 1)
                    }
                }
                if (yDim > 0) {
                    if (xDim != 0) { //Top Left
                        this.open(xDim - 1, yDim - 1)
                    }
                    if (xDim != xDimension - 1) { //Top Right
                        this.open(xDim + 1, yDim - 1)
                    }
                }
                if (yDim < yDimension - 1) {
                    if (xDim != 0) {
                        this.open(xDim - 1, yDim + 1)
                    }
                    if (xDim != xDimension - 1) {
                        this.open(xDim + 1, yDim + 1)
                    }
                }
            } else if (board.isGameOver()) {
                statusViewModel.gameOver.value = true
            }
            return played
        }
        return -999
    }

    fun mark(xDim: Int, yDim: Int){
        board.mark(xDim, yDim)
    }

    fun resetGame(){
        board = Board(xDimension, yDimension, numBombs, activity)
        statusViewModel.gameOver.value = false
    }

    fun isGameOver() : Boolean{
        return board.gameOver
    }

}