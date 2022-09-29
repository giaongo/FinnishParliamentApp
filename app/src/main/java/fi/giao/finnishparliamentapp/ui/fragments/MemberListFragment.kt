package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import fi.giao.finnishparliamentapp.adapter.MemberAdapter
import fi.giao.finnishparliamentapp.databinding.FragmentMemberListBinding
import fi.giao.finnishparliamentapp.viewmodel.MemberListViewModel
import fi.giao.finnishparliamentapp.viewmodel.MemberListViewModelFactory


class MemberListFragment : Fragment() {
    private lateinit var binding: FragmentMemberListBinding

    private val viewModel: MemberListViewModel by viewModels {
        MemberListViewModelFactory(requireActivity().application)
    }
    private val safeArgs: MemberListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.setParty(safeArgs.party)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val memberAdapter = MemberAdapter({
            Toast.makeText(requireContext(), "${it.firstname} is clicked", Toast.LENGTH_SHORT)
                .show()
            val action = MemberListFragmentDirections.actionMemberListFragmentToMemberInfoFragment(requestedMember = it)
            view.findNavController().navigate(action)
        }, requireContext())
        binding.memberListRecyclerView.apply {
            adapter = memberAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.memberListFromParty.observe(viewLifecycleOwner) {
            memberAdapter.submitList(it)
        }
    }
}