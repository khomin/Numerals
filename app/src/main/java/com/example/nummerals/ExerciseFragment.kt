package com.example.nummerals

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nummerals.databinding.FragmentExerciseBinding
import com.example.nummerals.viewModels.ExerciseViewModel
import kotlinx.coroutines.*
import kotlin.random.Random

class ExerciseFragment : Fragment() {
    private lateinit var mBinding: FragmentExerciseBinding
    private var mTimerJob: Job?= null
    private val mAudioPlayer = AudioNumeralPlayer()
    private var mFinalDialog: FinalDialog ?= null

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

        mFinalDialog = FinalDialog(inflater)

        mBinding.controlRepeat?.setOnClickListener {
            mBinding.model?.correctAnswer?.value?.let {
                playAudio(it.toInt())
            }
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
        mBinding.model?.exerciseStatus?.postValue(ExerciseViewModel.StatusExercise.PREPARE)
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
                    GlobalScope.launch {
                        mBinding.model?.exerciseStatus?.postValue(ExerciseViewModel.StatusExercise.OK)
                        delay(1000)
                        mBinding.model?.exerciseStatus?.postValue(ExerciseViewModel.StatusExercise.PREPARE)
                        createExercise()
                        updateStatsAndResetTimer()
                    }
                } else {
                    mBinding.model?.exerciseStatus?.postValue(ExerciseViewModel.StatusExercise.ERROR)
                    errorResult()
                }
            }
        }
    }

    private fun errorResult() {
        mBinding.model?.exerciseInProcess?.postValue(false)
        mBinding.model?.exerciseStatus?.postValue(ExerciseViewModel.StatusExercise.ERROR)
        mAudioPlayer.stopTickSound()
        mFinalDialog?.show(
            mBinding.model?.correctAnswer?.value?.toInt(),
            mBinding.model?.inputValue?.value?.toInt(),
            mBinding.model?.progressExercise?.value) {
            /* reset before navigateup */
            mBinding.model?.correctAnswer?.postValue("")
            mBinding.model?.inputValue?.postValue("")
            mBinding.model?.progressExercise?.postValue(0)
            view?.let {
                Navigation.findNavController(it).popBackStack(R.id.mainScreenFragment, false)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createExercise() {
        mBinding.model?.exerciseInProcess?.postValue(true)
        mBinding.model?.inputValue?.postValue("")
        val nextValue = Random.nextInt(1, 9999999)
        mBinding.model?.correctAnswer?.postValue(nextValue.toString())
        playAudio(nextValue)
        mBinding.model?.exerciseStatus?.postValue(ExerciseViewModel.StatusExercise.PREPARE)
    }

    private fun playAudio(value: Int) {
        Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
            val listNumerResourses = mutableListOf<Int>()
            if (value < 100) {
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_$value",
                        "raw",
                        activity?.getPackageName()
                    )
                )
            } else if ((value >= 100) and (value < 1000)) {
                listNumerResourses.add(getResources().getIdentifier("numeral_" + (value / 100).toString(), "raw",activity?.getPackageName()))
                listNumerResourses.add(getResources().getIdentifier("numeral_hundred","raw",activity?.getPackageName()))
                listNumerResourses.add(getResources().getIdentifier("numeral_" + (value % 100).toString(),"raw", activity?.getPackageName()))
            } else if ((value >= 1000) and (value < 10000)) {
                listNumerResourses.add(getResources().getIdentifier("numeral_" + (value / 1000).toString(),"raw",activity?.getPackageName()))
                listNumerResourses.add(getResources().getIdentifier("numeral_thousand","raw", activity?.getPackageName()))
                listNumerResourses.add(getResources().getIdentifier("numeral_" + ((value / 100) % 10).toString(),"raw",activity?.getPackageName()))
                listNumerResourses.add(getResources().getIdentifier("numeral_hundred","raw",activity?.getPackageName()))
                listNumerResourses.add(getResources().getIdentifier("numeral_" + (value % 100),"raw",activity?.getPackageName()))
            } else if ((value >= 10000) and (value < 100000)) {
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + ((value / 1000)).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_thousand",
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value / 100 % 10).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_hundred",
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value % 100).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
            } else if ((value >= 100000) and (value < 1000000)) {
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value / 100000).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_thousand",
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value / 1000 % 100).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value % 1000 / 100).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_hundred",
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value % 100).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
            } else if ((value >= 1000000) and (value < 10000000)) {
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value / 1000000).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_million",
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value / 100000 % 10).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_hundred",
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value / 1000 % 100).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value / 100 % 10).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_hundred",
                        "raw",
                        activity?.getPackageName()
                    )
                )
                listNumerResourses.add(
                    getResources().getIdentifier(
                        "numeral_" + (value % 100).toString(),
                        "raw",
                        activity?.getPackageName()
                    )
                )
            }
            mAudioPlayer.addValueToPlay(context, listNumerResourses) {}
        })
    }

    private fun updateStatsAndResetTimer() {
        mBinding.model?.progressTimerResidue?.postValue(10)
        mBinding.model?.exerciseInProcess?.postValue(true)
        mBinding.model?.progressExercise?.value?.let {
            mBinding.model?.progressExercise?.postValue(it + 1)
        }
        mBinding.model?.inputValue?.postValue("")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createTimerResidue() : Job {
        return GlobalScope.launch {
            while(true) {
                if(mBinding.model?.exerciseInProcess?.value == true) {
                    mBinding.model?.progressTimerResidue?.value?.let {
                        if (it != 0) {
                            mBinding.model?.exerciseInProcess?.postValue(true)
                            mBinding.model?.progressTimerResidue?.postValue(it - 1)
                        } else {
                            mBinding.model?.exerciseInProcess?.postValue(false)
                            errorResult()
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
        mAudioPlayer.stopTickSound()
    }

    override fun onResume() {
        super.onResume()
        if(mTimerJob == null) {
            mTimerJob = createTimerResidue()
            createExercise()
            updateStatsAndResetTimer()
            mAudioPlayer.createTickSound(context)
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

        @JvmStatic
        @BindingAdapter("app:statusImageAdapter")
        fun convertStatusToIcon(imageView: ImageView, status: ExerciseViewModel.StatusExercise) {
            when(status) {
                ExerciseViewModel.StatusExercise.PREPARE -> {
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
                        imageView.setImageDrawable(imageView.context.getDrawable(R.drawable.ic_hourglass_start))
                    })
                }
                ExerciseViewModel.StatusExercise.OK -> {
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
                        imageView.setImageDrawable(imageView.context.getDrawable(R.drawable.ic_check))
                    })
                }
                ExerciseViewModel.StatusExercise.ERROR -> {
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
                        imageView.setImageDrawable(imageView.context.getDrawable(R.drawable.ic_times))
                    })
                }
            }
        }

        @JvmStatic
        @BindingAdapter("app:textError")
        fun convertStatusToIcon(view: View, status: ExerciseViewModel.StatusExercise) {
            when(status) {
                ExerciseViewModel.StatusExercise.PREPARE -> {
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
                        view.visibility = View.GONE
                    })
                }
                ExerciseViewModel.StatusExercise.OK -> {
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
                        view.visibility = View.GONE
                    })
                }
                ExerciseViewModel.StatusExercise.ERROR -> {
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
                        view.visibility = View.VISIBLE
                    })
                }
            }
        }
    }
}
