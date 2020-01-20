package com.example.nummerals

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nummerals.databinding.FragmentMainScreenBinding
import com.example.nummerals.viewModels.ExerciseViewModel

class MainScreenFragment : Fragment() {
    private lateinit var mBinding: FragmentMainScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false)
        mBinding.lifecycleOwner = this

        activity?.let {
            mBinding.model = ViewModelProviders.of(it).get(ExerciseViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        mBinding.startButton?.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_mainScreenFragment_to_selectLevelFragment)
            }
        }

        return mBinding.root
    }
}
