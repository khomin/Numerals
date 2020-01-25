package com.example.nummerals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nummerals.databinding.FragmentSelectLevelExerciseBinding
import com.example.nummerals.viewModels.ExerciseViewModel

class SelectLevelFragment : Fragment() {
    private lateinit var mBinding: FragmentSelectLevelExerciseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_level_exercise, container, false)
        mBinding.lifecycleOwner = this

        activity?.let {
            mBinding.model = ViewModelProviders.of(it).get(ExerciseViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        mBinding.mediumMode?.setOnClickListener {
            view?.let {
                mBinding.model?.typeLevelExercise?.postValue(LevelExerciseType.MEDIUM)
                Navigation.findNavController(it).navigate(R.id.action_selectLevelFragment_to_exerciseFragment)
            }
        }

        return mBinding.root
    }
}
