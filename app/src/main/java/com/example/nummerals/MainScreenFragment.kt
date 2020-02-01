package com.example.nummerals

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nozzletagconnect.db.entity.ExeResultEntity
import com.example.nummerals.database.StatsRepository
import com.example.nummerals.databinding.FragmentMainScreenBinding
import com.example.nummerals.viewModels.ExerciseViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import java.util.*
import kotlin.collections.ArrayList

class MainScreenFragment : Fragment() {
    private lateinit var mBinding: FragmentMainScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false)
        mBinding.lifecycleOwner = this

        activity?.let {
            mBinding.model = ViewModelProviders.of(it).get(ExerciseViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        mBinding.startButton?.setOnClickListener {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainScreenFragment_to_selectLevelFragment)
            }
        }

        StatsRepository.init(activity?.applicationContext)

        return mBinding.root
    }

    private fun initPie() {
        mBinding.pieChart.setUsePercentValues(true)
        mBinding.pieChart.getDescription().setEnabled(false)
        mBinding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        mBinding.pieChart.setDragDecelerationFrictionCoef(0.95f)
        mBinding.pieChart.setCenterTextTypeface(Typeface.DEFAULT)
        mBinding.pieChart.setDrawHoleEnabled(true)
        mBinding.pieChart.setHoleColor(Color.WHITE)
        mBinding.pieChart.setTransparentCircleColor(Color.WHITE)
        mBinding.pieChart.setTransparentCircleAlpha(110)
        mBinding.pieChart.setHoleRadius(58f)
        mBinding.pieChart.setTransparentCircleRadius(61f)
        mBinding.pieChart.setDrawCenterText(true)
        mBinding.pieChart.setRotationAngle(0f)
        // enable rotation of the chart by touch
        mBinding.pieChart.setRotationEnabled(true)
        mBinding.pieChart.setHighlightPerTapEnabled(true)
        mBinding.pieChart.animateY(1400, Easing.EaseInOutQuad)
        val l = mBinding.pieChart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        mBinding.pieChart.setEntryLabelColor(Color.BLACK)
        mBinding.pieChart.setEntryLabelTypeface(Typeface.DEFAULT)
        mBinding.pieChart.setEntryLabelTextSize(12f)
    }

    private fun generateCenterSpannableText(text: String): SpannableString {
        val s = SpannableString(text)
        s.setSpan(RelativeSizeSpan(1.7f), 0, text.length, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), text.length, s.length - 0, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), text.length, s.length - 0, 0)
        s.setSpan(RelativeSizeSpan(.8f), text.length, s.length - 0, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 0, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 0, s.length, 0)
        return s
    }

    private fun pieSetData(textCenter: String, entries: List<PieEntry>) {
        val dataSet = PieDataSet(entries, textCenter)
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        // add a lot of colors

        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.setColors(colors)
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(mBinding.pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        data.setValueTypeface(Typeface.DEFAULT)
        mBinding.pieChart.setData(data)

        // undo all highlights
        mBinding.pieChart.highlightValues(null)

        mBinding.pieChart.invalidate()
    }

    override fun onResume() {
        super.onResume()
        initPie()
        StatsRepository.getAll {
            var totalExercise = 0
            var correctAnswer = 0
            var incorrectAnswer = 0
            it?.let {
                for (i in it) {
                    correctAnswer += i.correctAnswersCount
                    incorrectAnswer += i.incorrectAnswersCount
                    totalExercise += i.correctAnswersCount + i.incorrectAnswersCount
                }
            }
            mBinding.model?.stats?.totalAnswer?.postValue(totalExercise)
            mBinding.model?.stats?.correct?.postValue(correctAnswer)
            mBinding.model?.stats?.incorrect?.postValue(incorrectAnswer)

            var correctPercent = 0f
            var incorrectPercent = 0f
            if(totalExercise != 0) {
                correctPercent = (correctAnswer.toFloat() * 100 / totalExercise.toFloat())
                incorrectPercent = (incorrectAnswer.toFloat() * 100 / totalExercise.toFloat())

            }
            val list = mutableListOf<PieEntry>()
            list.add(0, PieEntry( correctPercent, correctAnswer.toString() ))
            list.add(1, PieEntry( incorrectPercent, incorrectAnswer.toString() ))
            val textCenter = context?.getString(R.string.total_answers) + " " + totalExercise.toString()
            pieSetData(textCenter, list)
            mBinding.pieChart.setCenterText(generateCenterSpannableText(textCenter))
        }
    }
}
