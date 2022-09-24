package fi.giao.finnishparliamentapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import fi.giao.finnishparliamentapp.adapter.PartyAdapter
import fi.giao.finnishparliamentapp.data.Parliament
import fi.giao.finnishparliamentapp.data.ParliamentMemberData
import fi.giao.finnishparliamentapp.databinding.FragmentPartyListBinding

class PartyListFragment : Fragment() {
    private lateinit var binding: FragmentPartyListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPartyListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val partyAdapter = PartyAdapter()
        partyAdapter.submitList(Parliament(ParliamentMemberData.members).parties())
        binding.partyListRecyclerView.apply {
            adapter = partyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}