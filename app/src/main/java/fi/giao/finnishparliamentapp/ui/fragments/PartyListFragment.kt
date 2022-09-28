package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fi.giao.finnishparliamentapp.adapter.PartyAdapter
import fi.giao.finnishparliamentapp.databinding.FragmentPartyListBinding
import fi.giao.finnishparliamentapp.viewmodel.AppViewModel
import fi.giao.finnishparliamentapp.viewmodel.ViewModelFactory

class PartyListFragment : Fragment() {
    private lateinit var binding: FragmentPartyListBinding
    private val viewModel: AppViewModel by activityViewModels {
        ViewModelFactory(requireActivity().application)
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
            val action = PartyListFragmentDirections.actionPartyListFragmentToMemberListFragment(party = it)
            view.findNavController().navigate(action)
        }

        binding.partyListRecyclerView.apply {
            adapter = memberAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.partyList.observe(viewLifecycleOwner) {
            memberAdapter.submitList(it)
        }

    }
}