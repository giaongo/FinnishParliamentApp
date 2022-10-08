package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fi.giao.finnishparliamentapp.adapter.StatisticAdapter
import fi.giao.finnishparliamentapp.databinding.FragmentStatisticBinding
import fi.giao.finnishparliamentapp.viewmodel.PartyListViewModel
import fi.giao.finnishparliamentapp.viewmodel.PartyListViewModelFactory

class StatisticFragment : Fragment() {
    private lateinit var binding:FragmentStatisticBinding
    private val viewModel: PartyListViewModel by viewModels {
        PartyListViewModelFactory(requireActivity().application)
    }

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
            Toast.makeText(requireContext(),"${it.size}",Toast.LENGTH_SHORT).show()
        }
//        viewModel.duplicatedParty.observe(viewLifecycleOwner){
//            val result = viewModel.calculatePartyPercentage(it)
//            Toast.makeText(requireContext(),"${result.size}",Toast.LENGTH_SHORT).show()
//        }
        binding.partyStatisticRecyclerView.apply {
            adapter = statisticAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }
}