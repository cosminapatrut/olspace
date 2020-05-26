package com.orange.olsnasa.screens.scores

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.orange.olsnasa.R
import kotlinx.android.synthetic.main.activity_scores.*
import org.koin.android.viewmodel.ext.android.viewModel

class ScoresActivity : AppCompatActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, ScoresActivity::class.java)
    }

    private val scoresViewModel by viewModel<ScoresViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        scoresViewModel.apply {
            val layoutManager = LinearLayoutManager(this@ScoresActivity)
            rvScores.layoutManager = layoutManager
            rvScores.adapter = ScoresAdapter(getScores())
        }
    }
}

