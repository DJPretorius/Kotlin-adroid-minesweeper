package com.example.kotlintest

import android.util.Log

class Board {
    val numOfBombs : Int
    private val xDimention : Int
    private val yDimention : Int
    val board = ArrayList<ArrayList<Int>>()
    val status = ArrayList<ArrayList<Boolean>>()
    var gameOver = false

    constructor(xDim : Int, yDim: Int, numBombs : Int)  {
        numOfBombs = numBombs
        xDimention = xDim
        yDimention = yDim

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

        var xIterator :Int = 0
        var yIterator :Int

        while(xIterator < xDim){
            yIterator = 0;
            while(yIterator < yDim){
                var countBombs :Int = 0;
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
                    };
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
                    board[xIterator][yIterator] = countBombs;
                }
                yIterator++
            }
            xIterator++
        }
    }

    public fun printBoard(){
        for(ar in board)
            Log.d("PRINT", ar.toString())
    }

    fun play(x : Int, y : Int) : Int{
        status[x][y] = !status[x][y]
        if(board[x][y] == -1) gameOver = true
        return board[x][y]
    }

    fun getX() : Int{
        return xDimention
    }

    fun getY() : Int{
        return yDimention
    }

    fun getValue(x: Int, y: Int) : Int{
        return board[x][y]
    }

    fun getGameStatus() : Boolean{
        if(gameOver) return true
        for(ar in status){
            for(done in ar){
                if(!done) return false
            }
        }
        return true
    }
}
