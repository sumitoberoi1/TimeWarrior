package com.example.sumitoberoi.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal lateinit var tapmeButton:Button
    internal lateinit var gameScoreTextView:TextView
    internal lateinit var timeLeftTextView:TextView
    internal var score = 0
    internal var gameStarted = false
    internal lateinit var countDownTimer:CountDownTimer
    internal val initialCountDown:Long = 60000
    internal val countDownInterval:Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configUI()
    }

    private fun configUI() {
        tapmeButton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        resetGame()
        tapmeButton.setOnClickListener { view ->
            startTimer()
            incrementScore()
        }
    }

    private fun incrementScore() {
        score += 1
        var newScoreString = getString(R.string.your_score,score.toString())
        gameScoreTextView.text = newScoreString
    }

    private fun startTimer() {
        if (!gameStarted) {
            countDownTimer.start()
            gameStarted = true
        }
    }

    private fun resetGame() {
        score = 0
        gameScoreTextView.text = getString(R.string.your_score,score.toString())
        val initialTimeLeft = initialCountDown/1000
        timeLeftTextView.text = getString(R.string.time_left,initialTimeLeft.toString())
        countDownTimer = object:CountDownTimer(initialCountDown,countDownInterval) {
            override fun onTick(timeLeft: Long) {
                timeLeftTextView.text = getString(R.string.time_left,(timeLeft/1000).toString())
            }
            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }

    private fun endGame() {
        Toast.makeText(this,getString(R.string.toast_score,score.toString()),Toast.LENGTH_SHORT).show()
        resetGame()
    }


}
