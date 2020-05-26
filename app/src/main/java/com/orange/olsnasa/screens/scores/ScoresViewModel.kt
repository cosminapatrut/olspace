package com.orange.olsnasa.screens.scores

import android.arch.lifecycle.ViewModel
import com.orange.domain.model.Score

class ScoresViewModel : ViewModel() {

    fun getScores() : List<Score> {
        return listOf(
            Score("Bejan Alina", 1200, "Romania"),
            Score("Draghici Ruxandra", 176, "Romania"),
            Score("Patrut Cosmina", 156, "Romania"),
            Score("Dita Cristian", 134, "Romania"),
            Score("Ionescu Andreea", 107, "Romania"),
            Score("Poenaru Mihai", 97, "Romania"),
            Score("Dobre Andrei", 93, "Romania"),
            Score("Stanciu Eugen", 87, "Romania"),
            Score("Popescu Ioana", 54, "Romania"),
            Score("Roman Alexandru", 50, "Romania"))
    }
}
