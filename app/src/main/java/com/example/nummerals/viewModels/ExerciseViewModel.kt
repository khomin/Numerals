package com.example.nummerals.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nummerals.LevelExerciseType

class ExerciseViewModel() : ViewModel() {
    val progressExercise = MutableLiveData<Int>().apply { value = 0 }
    val progressExerciseTotal = MutableLiveData<Int>().apply { value = 0 }

    val progressTimerResidue = MutableLiveData<Int>().apply { value = 0 }
    val progressTimerResidueTotal = MutableLiveData<Int>().apply { value = 0 }

    val typeLevelExercise = MutableLiveData<LevelExerciseType>().apply { value = LevelExerciseType.MEDIUM }

    val correctAnswer = MutableLiveData<String>().apply { value = "" }
    val inputValue = MutableLiveData<String>().apply { value = "" }

    val exerciseInProcess = MutableLiveData<Boolean>().apply { value = true }
}