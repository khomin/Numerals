package com.example.nummerals.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExerciseViewModel() : ViewModel() {
    val progressExercise = MutableLiveData<Int>().apply { value = 0 }
}