package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fi.giao.finnishparliamentapp.adapter.PartyAdapter
import fi.giao.finnishparliamentapp.databinding.FragmentPartyListBinding
import fi.giao.finnishparliamentapp.viewmodel.MemberViewModel
import fi.giao.finnishparliamentapp.viewmodel.MemberViewModelFactory

class PartyListFragment : Fragment() {
    private lateinit var binding: FragmentPartyListBinding
    private val viewModel: MemberViewModel by activityViewModels {
        MemberViewModelFactory(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPartyListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val memberAdapter = PartyAdapter{
            val action = PartyListFragmentDirections.actionPartyListFragmentToMemberListFragment(it)
            view.findNavController().navigate(action)
        }

        binding.partyListRecyclerView.apply {
            adapter = memberAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.memberList.observe(viewLifecycleOwner) { list ->
            run {
                Toast.makeText(requireContext(),"Total: ${list.size}",Toast.LENGTH_SHORT).show()
                val partyList = list.map { it.party }.toSet().toList().sorted()
                memberAdapter.submitList(partyList)
            }
        }

    }
}