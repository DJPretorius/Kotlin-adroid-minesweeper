package com.example.kotlintest

import android.util.Log
import androidx.lifecycle.ViewModelProviders

class Board {
    private val numOfBombs : Int
    private val xDimension : Int
    private val yDimension : Int
    private val board = ArrayList<ArrayList<Int>>()
    val status = ArrayList<ArrayList<Boolean>>()
    var gameOver = false
    private val statusViewModel : StatusViewModel

    constructor(xDim : Int, yDim: Int, numBombs : Int, activity: MainActivity)  {
        numOfBombs = numBombs
        xDimension = xDim
        yDimension = yDim
        statusViewModel = ViewModelProviders.of(activity).get(StatusViewModel::class.java)

        for(i in 0 until xDim){
            board.add(ArrayList())
            status.add(ArrayList())
            for(y in 0 until yDim){
                board[i].add(0)
                status[i].add(false)
            }
        }

        var count = 0
        while(count < numBombs){
            var randomX = (0 until xDim).shuffled().first()
            var randomY = (0 until yDim).shuffled().first()
            while(board[randomX][randomY] == -1){
                randomX = (0 until xDim).shuffled().first()
                randomY = (0 until yDim).shuffled().first()
            }
            board[randomX][randomY] = -1
            count++
        }

        var xIterator  = 0
        var yIterator :Int

        while(xIterator < xDim){
            yIterator = 0
            while(yIterator < yDim){
                var countBombs = 0
                if(board[xIterator][yIterator] != -1){
                    when (xIterator){
                        0 -> {		//Left column
                            countBombs += if(board[xIterator + 1][yIterator] == -1) 1 else 0
                        }
                        xDim -1 -> {		//Right column
                            countBombs += if(board[xIterator -1][yIterator] == -1) 1 else 0
                        }
                        else -> {
                            countBombs += if(board[xIterator + 1][yIterator] == -1) 1 else 0
                            countBombs += if(board[xIterator -1][yIterator] == -1) 1 else 0
                        }
                    }
                    when (yIterator){
                        0 -> {			//Top row
                            countBombs += if(board[xIterator][yIterator+1] == -1) 1 else 0

                        }
                        yDim -1 -> {		//Bottom row
                            countBombs += if(board[xIterator][yIterator-1] == -1) 1 else 0
                        }
                        else -> {
                            countBombs += if(board[xIterator][yIterator-1] == -1) 1 else 0
                            countBombs += if(board[xIterator][yIterator+1] == -1) 1 else 0
                        }
                    }
                    if(yIterator > 0 ){
                        if(xIterator != 0 ){ //Top Left
                            countBombs += if(board[xIterator -1][yIterator-1] == -1) 1 else 0
                        }
                        if(xIterator != xDim-1){ //Top Right
                            countBombs += if(board[xIterator+1][yIterator-1] == -1) 1 else 0
                        }
                    }
                    if(yIterator < yDim-1 ){
                        if(xIterator != 0 ){
                            countBombs += if(board[xIterator -1][yIterator+1] == -1) 1 else 0
                        }
                        if(xIterator != xDim-1){
                            countBombs += if(board[xIterator+1][yIterator+1] == -1) 1 else 0
                        }
                    }
                    board[xIterator][yIterator] = countBombs
                }
                yIterator++
            }
            xIterator++
        }
    }

    fun printBoard(){
        for(ar in board)
            Log.d("PRINT", ar.toString())
    }

    fun open(x : Int, y : Int) : Int{
        status[x][y] = !status[x][y]
        statusViewModel.statusChanged.value = (statusViewModel.statusChanged.value) ?: false
        if(board[x][y] == -1) gameOver = true
        return board[x][y]
    }

    fun mark(x: Int, y: Int) {
        status[x][y] = !status[x][y]
    }

    fun getX() : Int{
        return xDimension
    }

    fun getY() : Int{
        return yDimension
    }

    fun getValue(x: Int, y: Int) : Int{
        return board[x][y]
    }

    fun isGameOver() : Boolean{
        if(gameOver) return true
        for(ar in status){
            for(done in ar){
                if(!done) return false
            }
        }
        return true
    }
}
