package com.example.nummerals.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nummerals.ConstValues
import com.example.nummerals.LevelExerciseType

class ExerciseViewModel() : ViewModel() {
    val progressExercise = MutableLiveData<Int>().apply { value = 0 }
    val progressExerciseTotal = MutableLiveData<Int>().apply { value = 0 }

    val progressTimerResidue = MutableLiveData<Int>().apply { value = ConstValues.maxPercentExercise }

    val typeLevelExercise = MutableLiveData<LevelExerciseType>().apply { value = LevelExerciseType.MEDIUM }

    val correctAnswer = MutableLiveData<String>().apply { value = "" }
    val inputValue = MutableLiveData<String>().apply { value = "" }

    val exerciseInProcess = MutableLiveData<Boolean>().apply { value = true }

    enum class StatusExercise(value: Int) {
        PREPARE(0),
        OK(1),
        ERROR(2)
    }
    val exerciseStatus = MutableLiveData<StatusExercise>().apply { value = StatusExercise.PREPARE }
}