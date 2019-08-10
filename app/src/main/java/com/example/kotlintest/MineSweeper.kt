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
        val played = board.open(xDim, yDim)
        if(played == 0) {
            when (xDim) {
                0 -> {        //Left column
                    board.open(xDim+1, yDim)
                }
                xDimension - 1 -> {        //Right column
                    board.open(xDim-1, yDim)
                }
                else -> {
                    board.open(xDim + 1,yDim)
                    board.open(xDim - 1,yDim)
                }
            }
            when (yDim) {
                0 -> {            //Top row
                    board.open(xDim, yDim+1)

                }
                yDimension - 1 -> {        //Bottom row
                    board.open(xDim, yDim-1)
                }
                else -> {
                    board.open(xDim, yDim-1)
                    board.open(xDim, yDim+1)
                }
            }
            if (yDim > 0) {
                if (xDim != 0) { //Top Left
                    board.open(xDim-1, yDim-1)
                }
                if (xDim != xDimension - 1) { //Top Right
                    board.open(xDim+1, yDim-1)
                }
            }
            if (yDim < yDimension - 1) {
                if (xDim != 0) {
                    board.open(xDim-1, yDim+1)
                }
                if (xDim != xDimension - 1) {
                    board.open(xDim+1, yDim+1)
                }
            }
        }

        if(board.isGameOver()){
            statusViewModel.gameOver.value =true
        }
        return played
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