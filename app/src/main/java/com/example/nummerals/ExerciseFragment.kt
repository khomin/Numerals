package com.example.nummerals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nummerals.databinding.FragmentExerciseBinding
import com.example.nummerals.viewModels.ExerciseViewModel

class ExerciseFragment : Fragment() {
    private lateinit var mBinding: FragmentExerciseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false)
        mBinding.lifecycleOwner = this

        activity?.let {
            mBinding.model = ViewModelProviders.of(it).get(ExerciseViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        mBinding.cancelButton.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).popBackStack(R.id.mainScreenFragment, false)
            }
        }

        return mBinding.root
    }
}
