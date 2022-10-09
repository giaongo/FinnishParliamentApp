package fi.giao.finnishparliamentapp.ui.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.adapter.StatisticAdapter
import fi.giao.finnishparliamentapp.databinding.FragmentStatisticBinding
import fi.giao.finnishparliamentapp.viewmodel.PartyListViewModel
import fi.giao.finnishparliamentapp.viewmodel.PartyListViewModelFactory

class StatisticFragment : Fragment() {
    private lateinit var binding:FragmentStatisticBinding
    private val viewModel: PartyListViewModel by viewModels {
        PartyListViewModelFactory(requireActivity().application)
    }
    private lateinit var pieChart: PieChart


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentStatisticBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val statisticAdapter = StatisticAdapter(requireContext())
        viewModel.partyPercentage.observe(viewLifecycleOwner) {
            statisticAdapter.submitList(it)
            setUpPieChart()
        }

        binding.partyStatisticRecyclerView.apply {
            adapter = statisticAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    /**
     * Source reference:
     * https://www.geeksforgeeks.org/android-create-a-pie-chart-with-kotlin/
     * https://stackoverflow.com/questions/50907258/pie-chart-alignment-issue-using-mpandroidchart
     */
    private fun setUpPieChart() {
        pieChart = binding.partyPieChart
        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(0.5f,5f,0f,5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawEntryLabels(false)
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled =  true
            animateY(1400,Easing.EaseInOutQuad)
            legend.isEnabled = false

        }
        val dataSet = PieDataSet(viewModel.entries,"Parliament Parties")
        dataSet.apply {
            setDrawIcons(false)
            sliceSpace = 3f
            iconsOffset = MPPointF(0f,40f)
            selectionShift = 5f
            xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueLinePart1OffsetPercentage = 100f
            valueLinePart1Length = 0.3f
            valueLinePart2Length = 0.3f
        }
        val colors: MutableList<Int> = mutableListOf()

        colors.add(requireContext().getColor(R.color.red))
        colors.add(requireContext().getColor(R.color.green))
        colors.add(requireContext().getColor(R.color.star_yellow))
        colors.add(requireContext().getColor(R.color.navy_blue))
        colors.add(requireContext().getColor(R.color.cream))
        colors.add(requireContext().getColor(R.color.teal_700))
        colors.add(requireContext().getColor(R.color.orange))
        colors.add(requireContext().getColor(R.color.pink))
        colors.add(requireContext().getColor(R.color.grey))
        colors.add(requireContext().getColor(R.color.purple))
        colors.add(requireContext().getColor(R.color.dark_green))

        dataSet.colors =  colors

        val pieData = PieData(dataSet)
        pieData.apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(10f)
            setValueTextColor(Color.BLACK)
            setValueTypeface(Typeface.DEFAULT_BOLD)
        }
        pieChart.apply {
            data = pieData
            highlightValue(null)
            invalidate()
        }


    }
}