package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.adapter.ReviewAdapter
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.databinding.FragmentMemberInfoBinding
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModel
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModelFactory

private const val IMG_BASE_URL = "https://avoindata.eduskunta.fi/"
class MemberInfoFragment : Fragment() {
    private lateinit var binding: FragmentMemberInfoBinding
    private val viewModel:MemberInfoViewModel by activityViewModels {
        MemberInfoViewModelFactory(requireActivity().application)
    }
    private val safeArgs: MemberInfoFragmentArgs by navArgs()
    private lateinit var currentMember: ParliamentMember

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentMember = safeArgs.requestedMember
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            memberNameTextView.text = getString(
                R.string.member_name_text, currentMember.firstname,
                currentMember.lastname
            )
            memberPartyTextView.text = getString(R.string.party_name_text, currentMember.party)
        }
        // Fetch image data from network and bind it into imageView
        Glide.with(this).load(IMG_BASE_URL + currentMember.pictureUrl).into(binding.memberImageView)

        binding.reviewAddButton.setOnClickListener {
            val action = MemberInfoFragmentDirections.actionMemberInfoFragmentToAddReviewFragment(currentMember.hetekaId)
            view.findNavController().navigate(action)
        }

        // Set hetekaId of current member to viewModel to activate the switchMap in viewModel
        viewModel.setHetekaId(currentMember.hetekaId)
        val reviewAdapter = ReviewAdapter(requireContext())
        viewModel.allReviewsByHetekaId.observe(viewLifecycleOwner) {
            reviewAdapter.submitList(it)
        }
        binding.userReviewsRecyclerView.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        updateRating()
    }

    private fun updateRating() {
        viewModel.averageRating.observe(viewLifecycleOwner) {
            binding.memberRatingBar.rating = it

        }


    }
}