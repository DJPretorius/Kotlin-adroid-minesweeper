package com.example.kotlintest

import androidx.lifecycle.ViewModelProviders

class MineSweeper {
    var board : Board
    val xDim : Int
    val yDim: Int
    val numBombs : Int
    val statusViewModel : StatusViewModel


    constructor(xDim: Int, yDim : Int, numBombs : Int, mainActivity: MainActivity){
        board = Board(xDim, yDim, numBombs)
        this.xDim = xDim
        this.yDim = yDim
        this.numBombs = numBombs
        statusViewModel = ViewModelProviders.of(mainActivity).get(StatusViewModel::class.java)

    }

    fun play(xDim: Int, yDim: Int) : Int{
        val played = board.play(xDim, yDim)
        if(board.getGameStatus()){
            statusViewModel.stateLD.value =true
        }
        return played
    }

    fun resetGame(){
        board = Board(xDim, yDim, numBombs)
        statusViewModel.stateLD.value = false
    }

    fun isGameOver() : Boolean{
        return board.gameOver
    }

}