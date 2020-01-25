package com.example.nummerals

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nummerals.databinding.FragmentExerciseBinding
import com.example.nummerals.viewModels.ExerciseViewModel
import kotlinx.coroutines.*
import kotlin.random.Random

class ExerciseFragment : Fragment() {
    private lateinit var mBinding: FragmentExerciseBinding
    private var mTimerJob: Job?= null

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

        mBinding.controlRepeat?.setOnClickListener {

        }
        mBinding.controlRemove?.setOnClickListener {
            mBinding.model?.inputValue?.value?.let {
                if(it.isNotEmpty()) {
                    mBinding.model?.inputValue?.postValue(it.substring(0, it.length - 1))
                }
            }
        }
        mBinding.control0?.setOnClickListener { addInputValue(0) }
        mBinding.control1?.setOnClickListener { addInputValue(1) }
        mBinding.control2?.setOnClickListener { addInputValue(2) }
        mBinding.control3?.setOnClickListener { addInputValue(3) }
        mBinding.control4?.setOnClickListener { addInputValue(4) }
        mBinding.control5?.setOnClickListener { addInputValue(5) }
        mBinding.control6?.setOnClickListener { addInputValue(6) }
        mBinding.control7?.setOnClickListener { addInputValue(7) }
        mBinding.control8?.setOnClickListener { addInputValue(8) }
        mBinding.control9?.setOnClickListener { addInputValue(9) }

        mBinding.model?.progressTimerResidueTotal?.postValue(10)
        mBinding.model?.progressExercise?.postValue(0)
        mBinding.model?.progressExerciseTotal?.postValue(100)

        // TODO: add ring 5 second

        return mBinding.root
    }

    private fun addInputValue(i: Int) {
        var value = ""
        mBinding.model?.inputValue?.value?.let { lastValue ->
            value += lastValue
        }
        value += i.toString()
        mBinding.model?.inputValue?.postValue(value)

        mBinding.model?.correctAnswer?.value?.let {
            if(value.length >= it.length) {
                if(value.equals(it)) {
                    createExercise()
                    resetTimerResidue()
                } else {
                    errorResult()
                }
            }
        }
    }

    private fun errorResult() {
        mBinding.model?.exerciseInProcess?.postValue(false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createExercise() {
        mBinding.model?.exerciseInProcess?.postValue(true)
        mBinding.model?.inputValue?.postValue("")
        val nextValue = Random.nextInt(1, 10000)
        // TOOO: for isolate test
//        val nextValue = Random.nextInt(1000, 10000)
//        val nextValue = Random.nextInt(10000, 100000)
//        val nextValue = Random.nextInt(100000, 1000000)
//        val nextValue = Random.nextInt(1000000, 10000000)
        mBinding.model?.correctAnswer?.postValue(nextValue.toString())

        val audioPlayer = AudioNumeralPlayer()
        var listNumerResourses = mutableListOf<Int>()
        if(nextValue < 100) {
            listNumerResourses.add(getResources().getIdentifier("numeral_$nextValue","raw", activity?.getPackageName()))
        } else if((nextValue >= 100) and (nextValue < 1000)) {
            listNumerResourses.add(getResources().getIdentifier("numeral_" + (nextValue/100).toString(),"raw", activity?.getPackageName()))
            listNumerResourses.add(getResources().getIdentifier("numeral_hundred","raw", activity?.getPackageName()))
            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue/10) % 10).toString(),"raw", activity?.getPackageName()))
            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue%10)).toString(),"raw", activity?.getPackageName()))
        } else if((nextValue >= 1000) and (nextValue < 10000)) {
            listNumerResourses.add(getResources().getIdentifier("numeral_" + (nextValue/1000).toString(),"raw", activity?.getPackageName()))
            listNumerResourses.add(getResources().getIdentifier("numeral_thousand","raw", activity?.getPackageName()))
            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue/100) % 10).toString(),"raw", activity?.getPackageName()))
            listNumerResourses.add(getResources().getIdentifier("numeral_hundred","raw", activity?.getPackageName()))
            listNumerResourses.add(getResources().getIdentifier("numeral_" + (nextValue % 100),"raw", activity?.getPackageName()))
        } else if((nextValue >= 10000) and (nextValue < 100000)) { // TODO: make it
            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue / 1000)).toString(),"raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_hundred","raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue/10) % 10).toString(),"raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue%10)).toString(),"raw", activity?.getPackageName()))
        } else if((nextValue >= 100000) and (nextValue < 1000000)) { // TODO: make it
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + (nextValue/100).toString(),"raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_hundred","raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue/10) % 10).toString(),"raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue%10)).toString(),"raw", activity?.getPackageName()))
        } else if((nextValue >= 100000) and (nextValue < 1000000)) { // TODO: make it
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + (nextValue/100).toString(),"raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_hundred","raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue/10) % 10).toString(),"raw", activity?.getPackageName()))
//            listNumerResourses.add(getResources().getIdentifier("numeral_" + ((nextValue%10)).toString(),"raw", activity?.getPackageName()))
        }

        audioPlayer.addValueToPlay(context, listNumerResourses) {}
    }

    private fun resetTimerResidue() {
        mBinding.model?.progressTimerResidue?.postValue(10)
        mBinding.model?.progressExercise?.value?.let {
            mBinding.model?.progressExercise?.postValue(it + 1)
        }
        mBinding.model?.inputValue?.postValue("")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createTimerResidue() : Job {
        createExercise()
        resetTimerResidue()

        return GlobalScope.launch {
            while(true) {
                mBinding.model?.progressTimerResidue?.value?.let {
                    if (it != 0) {
                        mBinding.model?.exerciseInProcess?.postValue(true)
                        mBinding.model?.progressTimerResidue?.postValue(it - 1)
                    } else {
                        mBinding.model?.exerciseInProcess?.value?.let {
                            if(!it) {
                                errorResult()
                            }
                        }
                    }
                }
                delay(1000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimerJob?.cancel()
        mTimerJob = null
    }

    override fun onResume() {
        super.onResume()
        if(mTimerJob == null) {
            mTimerJob = createTimerResidue()
        } else {
            mTimerJob?.start()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["app:ProgressFromInt","app:ProgressFromTotal"], requireAll = true)
        fun convertIntToProgressBard(progress: ProgressBar, valueCurrent: Int?, valueTotal: Int?) {
            var current = 0
            var total = 0
            var value = 0
            valueCurrent?.let { current = it }
            valueTotal?.let { total = it }
            if(total != 0) {
                value = ((current.toFloat() / total.toFloat()) * 100).toInt()
            }
            progress.setProgress(value)
        }
    }
}
