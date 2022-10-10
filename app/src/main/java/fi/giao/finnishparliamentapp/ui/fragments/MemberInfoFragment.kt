package fi.giao.finnishparliamentapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.adapter.ReviewAdapter
import fi.giao.finnishparliamentapp.database.MemberFavorite
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.databinding.FragmentMemberInfoBinding
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModel
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModelFactory

/**
 * Date: 5/10/2022
 * Name:Giao Ngo
 * Student id: 2112622
 * This fragment uses MemberInfoViewModel. It shows information of current Parliament member passed
 * from previous fragment, allows user to add their reviews, mark or un-mark that member as
 * favorite member and view list of favorite members. This also lets user send email to parliament
 * member by triggering implicit intent to other email app.
 * The member star rating displayed in the middle is a result of average calculation from
 * list of user star reviews.
 */
const val IMG_BASE_URL = "https://avoindata.eduskunta.fi/"
class MemberInfoFragment : Fragment() {
    private lateinit var binding: FragmentMemberInfoBinding
    private val viewModel: MemberInfoViewModel by viewModels {
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
        binding.emailButton.setOnClickListener {
            val firstName = currentMember.firstname.lowercase()
            val lastName = currentMember.lastname.lowercase()
            composeEmail("$firstName.$lastName@eduskunta.fi", "Questions")
        }
    }

    // Displays name, party and member image to UI
    private fun displayMemberInfo() {
        binding.apply {
            memberNameTextView.text = getString(
                R.string.member_name_text, currentMember.firstname,
                currentMember.lastname
            )
            memberPartyTextView.text = getString(R.string.party_name_text, currentMember.party)
        }
        // Fetch image data from network and bind it into imageView
        Glide.with(this)
            .load(IMG_BASE_URL + currentMember.pictureUrl)
            .placeholder(R.drawable.loading_icon)
            .error(R.drawable.error_icon)
            .into(binding.memberImageView)
    }

    // Displays list of reviews that user added
    private fun displayReviews() {
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

    // Observe the change of averageRating and update to UI
    private fun updateRating() {
        viewModel.averageRating.observe(viewLifecycleOwner) {
            binding.memberRatingBar.rating = it
        }
    }

    // Define implicit intent to send email
    private fun composeEmail(address: String, subject:String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data  =  Uri.parse("mailto:$address") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT,subject)
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
//            requireContext().startActivity(Intent.createChooser(intent,"Send Email"))
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.member_info_fragment_menu,menu)
    }

    // Option menu (un_mark or mark) is visible based on whether the member is a favorite or not
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        viewModel.isMarkedFavorite.observe(viewLifecycleOwner) {
            menu.findItem(R.id.unMark_favorite).isVisible = it
            menu.findItem(R.id.mark_favorite).isVisible = !it
        }
    }

    // Define click listener for app bar item menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentFavoriteMember = MemberFavorite(0,currentMember.hetekaId,true)
        return when (item.itemId) {
            R.id.mark_favorite -> {
                viewModel.markFavorite(currentFavoriteMember)
                Toast.makeText(requireContext(),getString(R.string.mark_favorite_member_name,
                    currentMember.firstname),Toast.LENGTH_SHORT).show()
                true
            }
            R.id.unMark_favorite -> {
                viewModel.unMarkFavorite(currentMember.hetekaId)
                Toast.makeText(requireContext(),getString(R.string.unMark_favorite_member_name,
                    currentMember.firstname),Toast.LENGTH_SHORT).show()
                true
            }
            R.id.view_favorites_from_info_fragment -> {
                findNavController().navigate(R.id.action_memberInfoFragment_to_favoriteListFragment)
                true
            }
            R.id.back_to_home -> {
                findNavController().navigate(R.id.action_memberInfoFragment_to_introAppFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}