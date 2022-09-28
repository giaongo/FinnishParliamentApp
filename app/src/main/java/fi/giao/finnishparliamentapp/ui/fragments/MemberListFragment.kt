package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fi.giao.finnishparliamentapp.adapter.MemberAdapter
import fi.giao.finnishparliamentapp.databinding.FragmentMemberListBinding
import fi.giao.finnishparliamentapp.viewmodel.MemberViewModel
import fi.giao.finnishparliamentapp.viewmodel.MemberViewModelFactory
import fi.giao.finnishparliamentapp.viewmodel.ParliamentFunctions

class MemberListFragment : Fragment() {
    private lateinit var binding:FragmentMemberListBinding
    private lateinit var requestedParty: String

    companion object {
        private const val PARTY = "party"
    }
    private val viewModel:MemberViewModel by activityViewModels {
        MemberViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            requestedParty = it.getString(PARTY).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setRequestedParty(requestedParty)

        val memberAdapter = MemberAdapter({
            Toast.makeText(requireContext(),"${it.firstname} is clicked", Toast.LENGTH_SHORT).show()
        }, requireContext())
        binding.memberListRecyclerView.apply {
            adapter = memberAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

//        viewModel.memberList.observe(viewLifecycleOwner) {
//            val memberListFromParty = ParliamentFunctions.listMemberFromParty(it,requestedParty)
//            memberAdapter.submitList(memberListFromParty)
//        }

//        viewModel.getMemberListFromParty(requestedParty).observe(viewLifecycleOwner) {
//            memberAdapter.submitList(it)
//        }

        viewModel.memberListFromParty.observe(viewLifecycleOwner) {
            memberAdapter.submitList(it)
        }
    }
}