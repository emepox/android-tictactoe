package de.sdomma.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

import de.sdomma.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var myGame = Game()
    private var inputFields: MutableList<ImageView>? = null

    private lateinit var currentPlayerTV: TextView
    private lateinit var winningMessageTV: TextView
    private lateinit var btnReset: Button
    private lateinit var counterOneTV: TextView
    private lateinit var board: Array<Array<Player?>>

    // Binding

    lateinit var binding: ActivityMainBinding


    private var counterOne: Int = 0
        set(value) {
            field = value
            counterOneTV.text = value.toString()
        }

    private lateinit var counterTwoTV: TextView
    private var counterTwo: Int = 0
        set(value) {
            field = value
            counterTwoTV.text = value.toString()
        }

    private lateinit var counterDrawTV: TextView
    private var counterDraw: Int = 0
        set(value) {
            field = value
            counterDrawTV.text = value.toString()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        counterOneTV = binding.counterOne
        counterTwoTV = binding.counterTwo
        counterDrawTV = binding.counterDraw

        // counterOneTV = findViewById(R.id.counter_one)
        // counterTwoTV = findViewById(R.id.counter_two)
        // counterDrawTV = findViewById(R.id.counter_draw)

        currentPlayerTV = binding.currentPlayerTv
        winningMessageTV = binding.winningMessageTv
        btnReset = binding.btnReset

        // currentPlayerTV = findViewById(R.id.current_player_tv)
        // winningMessageTV = findViewById(R.id.winning_message_tv)
        // btnReset = findViewById(R.id.btn_reset)


        //val input0: ImageView = findViewById(R.id.input_0)
        //val input1: ImageView = findViewById(R.id.input_1)
        //val input2: ImageView = findViewById(R.id.input_2)
        //val input3: ImageView = findViewById(R.id.input_3)
        //val input4: ImageView = findViewById(R.id.input_4)
        //val input5: ImageView = findViewById(R.id.input_5)
        //val input6: ImageView = findViewById(R.id.input_6)
        //val input7: ImageView = findViewById(R.id.input_7)
        //val input8: ImageView = findViewById(R.id.input_8)

        inputFields = mutableListOf(
            binding.input0, binding.input1, binding.input2, binding.input3, binding.input4, binding.input5, binding.input6, binding.input7, binding.input8
        ).apply {
            this.forEachIndexed { index, iv ->
                iv.setOnClickListener {
                    //  (2 / 3 = 0) (2 % 3 = 2)
                    dothething(iv, index / 3, index % 3)
                }
            }
        }

        currentPlayerTV.text = "Your turn Player ${myGame.currentPlayer}"

        btnReset.setOnClickListener {
            resetGame()
        }

        /*input0.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input0, 0, 0)
        }

        input1.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input1, 0, 1)
        }

        input2.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input2, 0, 2)
        }

        input3.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input3, 1, 0)
        }

        input4.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input4, 1, 1)
        }

        input5.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input5, 1, 2)
        }

        input6.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input6, 2, 0)
        }

        input7.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input7, 2, 1)
        }

        input8.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input8, 2, 2)
        }*/

    }


    companion object {
        val PLAYER_ONE_COUNTER_KEY = "playerOneCounterKey"
        val PLAYER_TWO_COUNTER_KEY = "playerTwoCounterKey"
        val DRAW_COUNTER_KEY = "drawCounterKey"
        var BOARD_KEY = "boardKey"
        var CURRENT_PLAYER_KEY = "currentPlayer"


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(PLAYER_ONE_COUNTER_KEY, counterOne)
        outState.putInt(PLAYER_TWO_COUNTER_KEY, counterTwo)
        outState.putInt(DRAW_COUNTER_KEY, counterDraw)

        outState.putSerializable(BOARD_KEY, myGame.board)
        outState.putSerializable(CURRENT_PLAYER_KEY, myGame.currentPlayer)



    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        counterOne = savedInstanceState.getInt(PLAYER_ONE_COUNTER_KEY)
        counterTwo = savedInstanceState.getInt(PLAYER_TWO_COUNTER_KEY)
        counterDraw = savedInstanceState.getInt(DRAW_COUNTER_KEY)
        myGame.board = savedInstanceState.getSerializable(BOARD_KEY) as Array<Array<Player?>>
        myGame.currentPlayer = savedInstanceState.getSerializable(CURRENT_PLAYER_KEY) as Player
        restoreBoard()

    }

    private fun restoreBoard() {
        myGame.board.flatten().forEachIndexed { position, player ->

            if (player == Player.ONE) {
                // Set icon for player one
                inputFields?.get(position)?.let {
                    it.setImageResource(R.drawable.ic_player_one)
                }
            } else if (player == Player.TWO) {
                // Set icon for player two
                inputFields?.get(position)?.let {
                    it.setImageResource(R.drawable.ic_player_two)
                }
            }
        }
    }




    private fun dothething(
        inputField: ImageView,
        x: Int,
        y: Int
    ) {
        // Game is running AND valid input (Field is not already taken)
//            if (myGame.gameStatus != GameStatus.RUNNING || !myGame.isValidInput(0, 0)) return@setOnClickListener
        if (myGame.gameStatus == GameStatus.RUNNING && myGame.isValidInput(x, y)) {

            // Make move on the gameboard
            myGame.makeMove(x, y)

            // Show move on the UI
            inputField.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    if (myGame.currentPlayer == Player.ONE) {
                        // Set icon for player one
                        R.drawable.ic_player_one
                    } else {
                        // Set icon for player two
                        R.drawable.ic_player_two
                    }, null
                )
            )

            // Checkboard for win or Draw
            myGame.checkBoard(myGame.board)

            if (myGame.gameStatus == GameStatus.WON) {
                winningMessageTV.apply {
                    text = "You Won Player ${myGame.currentPlayer}"
                    visibility = View.VISIBLE
                }

                if (myGame.currentPlayer == Player.ONE) {
                    counterOne++
                } else {
                    counterTwo++
                }
                // Solution 1
//                btnReset.visibility = View.VISIBLE
            }

            if (myGame.gameStatus == GameStatus.DRAW) {
                winningMessageTV.apply {
                    text = "Draw"
                    visibility = View.VISIBLE
                }

                counterDraw++
//                btnReset.visibility = View.VISIBLE
            }

            // Solution 2
//            if (myGame.gameStatus == GameStatus.WON || myGame.gameStatus == GameStatus.DRAW) {
//                btnReset.visibility = View.VISIBLE
//            }

            // Solution 3
//            if (myGame.gameStatus != GameStatus.RUNNING) {
//                btnReset.visibility = View.VISIBLE
//            }

            // Solution 4 - 5
            btnReset.visibility = if (myGame.gameStatus != GameStatus.RUNNING) View.VISIBLE else View.INVISIBLE

            // Solution 5 - 3
//            btnReset.isVisible = myGame.gameStatus != GameStatus.RUNNING

            // Switch Player
            myGame.switchPlayer()
            currentPlayerTV.text = "Your turn Player ${myGame.currentPlayer}"
        }
    }

    private fun resetGame() {
        inputFields?.forEach {
            it.setImageDrawable(null)
        }

        myGame = Game()

        btnReset.visibility = View.INVISIBLE
        winningMessageTV.visibility = View.INVISIBLE

        currentPlayerTV.text = "Your turn Player ${myGame.currentPlayer}"
    }
}

/*

# TicTacToe - Fragments

DONE
=====

1. Create a new branch called `fragments`
    1. Check out the new create branch

2. Create a Layout called `activity_fragments`
    1. Add a `FragmentContainerView` to the layout
    2. Give it an ID i.e. `fragment_holder`
    3. Let the holder fill the whole space

3. Create a new Activity called `FragmentsActivity`
    1. Extend `AppCompatActivity`
    2. Create a `binding` variable and initialise it by using the `ActivityFragmentBinding`

4. Go to the `AndroidManifest`
    1. Inside the `<activity>` tag replace the name to `.FragmentsActivity`

5. Create a Layout called `fragment_title`
    1. Add a `TextView` with the AppName as text
    2. Add a `Button` with the text: „Start Game“

6. Create a new Class called `TitleFragment`
    1. Extend `Fragment`
    2. Create a class-variable called `onButtonClick`
        1. The type is a lambda (i.e. -> `() -> Unit`)
        2. It’s a mutable variable
        3. The variable is initialised as lambda without a functionality -> `{}`
    3. Use `viewBinding` to inflate the Layout `fragment_title`
    4. Add a onClickListener to the Button
        1. Inside the lambda call the class lambda `onButtonClick()`

7. Add the attribute `name` of the `TitleFragment` to the `FragmentContainerView` of the `activity_fragments` layout

8. Create a Layout called `fragment_game`
    1. Copy the content from the `activity_main` layout

9. Create a new class called `GameFragment`
    1. Use `viewBinding` to inflate the layout


TO DO
======

10. Go to the `FragmentsActivity`
    1. In the `onCreate` function create a variable `currentFragment`
    2. Assign the current Fragment to the variable
        1. Use the `supportFragmentManager`
        2. Call the `findFragmentById` function of the `supportFragmentManager`
        3. The parameter for the function is the id of the `FragmentContainerView`
    3. Check if the `currentFragment` is of type `TitleFragment`
        1. If the condition is true assign a value to the `onButtonClick` variable of the `currentFragment`
        2. The value should be a lambda
        3. Use the `supportFragmentManager` to replace the content of the `fragment_holder` with the `GameFragment`

*/
