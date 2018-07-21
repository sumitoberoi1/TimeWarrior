package com.example.sumitoberoi.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    internal lateinit var tapmeButton:Button
    internal lateinit var gameScoreTextView:TextView
    internal lateinit var timeLeftTextView:TextView
    internal var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configUI()
    }

    private fun configUI() {
        tapmeButton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        tapmeButton.setOnClickListener { view ->
            incrementScore()
        }
    }

    private fun incrementScore() {
        score += 1
        var newScoreString = getString(R.string.your_score,score.toString())
        gameScoreTextView.text = newScoreString
    }
}
