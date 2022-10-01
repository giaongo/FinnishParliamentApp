package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.toColor
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
    private val viewModel: MemberInfoViewModel by activityViewModels {
        MemberInfoViewModelFactory(requireActivity().application)
    }
    private val safeArgs: MemberInfoFragmentArgs by navArgs()
    private lateinit var currentMember: ParliamentMember

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentMember = safeArgs.requestedMember
        setHasOptionsMenu(true)
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

        displayMemberInfo()
        displayReviews()
        updateRating()

        binding.reviewAddButton.setOnClickListener {
            val action = MemberInfoFragmentDirections.actionMemberInfoFragmentToAddReviewFragment(
                currentMember.hetekaId
            )
            view.findNavController().navigate(action)
        }
    }

    private fun displayMemberInfo() {
        binding.apply {
            memberNameTextView.text = getString(
                R.string.member_name_text, currentMember.firstname,
                currentMember.lastname
            )
            memberPartyTextView.text = getString(R.string.party_name_text, currentMember.party)
        }
        // Fetch image data from network and bind it into imageView
        Glide.with(this).load(IMG_BASE_URL + currentMember.pictureUrl).into(binding.memberImageView)
    }

    private fun displayReviews() {
        // Set hetekaId of current member to viewModel to activate the switchMap in viewModel
        viewModel.setHetekaId(currentMember.hetekaId)
        val reviewAdapter = ReviewAdapter(requireContext()) {
            val action = MemberInfoFragmentDirections.actionMemberInfoFragmentToUpdateReviewFragment2(it)
            findNavController().navigate(action)
        }

        viewModel.allReviewsByHetekaId.observe(viewLifecycleOwner) {
            reviewAdapter.submitList(it)
        }
        binding.userReviewsRecyclerView.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun updateRating() {
        viewModel.averageRating.observe(viewLifecycleOwner) {
            binding.memberRatingBar.rating = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.member_info_fragment_menu,menu)
    }

    /**
     * Source for changing icon color when menu item is clicked
     * Source: https://stackoverflow.com/questions/56716093/setcolorfilter-is-deprecated-on-api29
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mark_favorite -> {
                Toast.makeText(requireContext(),"clicked",Toast.LENGTH_SHORT).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}