package com.example.sumitoberoi.timefighter

import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
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
    internal val TAG = MainActivity::class.java.simpleName
    internal var timeLeftInTimer:Long = 60000
    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onc create called with score $score")
        configUI(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            outState.putInt(SCORE_KEY, score)
            outState.putLong(TIME_LEFT_KEY, timeLeftInTimer)
            countDownTimer.cancel()
            Log.d(TAG,"onsave instance method called $score and time left $timeLeftInTimer")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"on destroy called")
    }

    private fun configUI(savedInstanceState:Bundle?) {
        tapmeButton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftInTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }

        tapmeButton.setOnClickListener { view ->
            doButtonAnimation(view)
            startTimer()
            incrementScore()
        }
    }

    private fun restoreGame() {
        gameScoreTextView.text = getString(R.string.your_score,score.toString())
        val restoredTime = timeLeftInTimer/1000
        timeLeftTextView.text = getString(R.string.time_left,restoredTime.toString())
        countDownTimer = object:CountDownTimer(timeLeftInTimer,countDownInterval) {
            override fun onTick(timeLeft: Long) {
                timeLeftInTimer = timeLeft
                timeLeftTextView.text = getString(R.string.time_left,(timeLeft/1000).toString())
            }
            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun incrementScore() {
        score += 1
        var newScoreString = getString(R.string.your_score,score.toString())
        gameScoreTextView.text = newScoreString
    }

    private fun doButtonAnimation(view:View){
        val bounceAnimation = AnimationUtils.loadAnimation(this,R.anim.bounce)
        view.startAnimation(bounceAnimation)
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
                timeLeftInTimer = timeLeft
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
