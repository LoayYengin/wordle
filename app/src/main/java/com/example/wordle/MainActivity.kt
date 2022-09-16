package com.example.wordle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var answerText = findViewById<TextView>(R.id.answer_text)
        var guessBtn = findViewById<Button>(R.id.guess_button)
        var inputEntry = findViewById<EditText>(R.id.input_edit)

        var guess1 = findViewById<TextView>(R.id.guess1_text)
        var guess2 = findViewById<TextView>(R.id.guess2_text)
        var guess3 = findViewById<TextView>(R.id.guess3_text)
        var guessResult1 = findViewById<TextView>(R.id.check1_text)
        var guessResult2 = findViewById<TextView>(R.id.check2_text)
        var guessResult3 = findViewById<TextView>(R.id.check3_text)

        var guessCount = 1
        var resetAnswer = false
        var wordToGuess = FourLetterWordList.getRandomFourLetterWord().uppercase()
        answerText.text = wordToGuess

        guessBtn.setOnClickListener {
            // set current guess to text in inputText
            val guess = inputEntry.text.toString().uppercase()

            // check guess is correct length
            if (guess.length != 4 && guessCount <= 3) {
                Toast.makeText(it.context, "Enter 4 letters!", Toast.LENGTH_SHORT).show()
            }
            if (guess.length == 4 && guessCount <= 3) {
                // verify the guess
                val result = checkGuess(guess, wordToGuess)

                // show list of guesses
                if (guessCount == 1) {
                    guess1.text = guess
                    guessResult1.text = result
                } else if (guessCount == 2) {
                    guess2.text = guess
                    guessResult2.text = result
                } else if (guessCount == 3) {
                    guess3.text = guess
                    guessResult3.text = result

                    // show word to guess
                    answerText.visibility = View.VISIBLE

                    // Show a toast
                    // Change submit btn to reset
                    Toast.makeText(it.context, "No more guesses!", Toast.LENGTH_SHORT).show()
                    guessBtn.text = "RESET"
                }

                guessCount++
            }

            if (resetAnswer) {
                wordToGuess = FourLetterWordList.getRandomFourLetterWord().uppercase()
                answerText.visibility = View.INVISIBLE

                guess1.text = "____"
                guessResult1.text = ""
                guess2.text = "____"
                guessResult2.text = ""
                guess3.text = "____"
                guessResult3.text = ""

                answerText.text = wordToGuess
                guessBtn.text = "SUBMIT"
                guessCount = 1

                resetAnswer = false;
            }

            if (guessCount == 4) {
                resetAnswer = true
            }
            // Hide the keyboard
            if (guess.isNotEmpty()) {
                Log.i("yengin", "not empty - GuessCount: " + guessCount)

                inputEntry.text.clear()
            }
            hideKeyboard()

        }

    }

    private fun hideKeyboard() {
        val view = this.currentFocus

        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String, wordToGuess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }


}