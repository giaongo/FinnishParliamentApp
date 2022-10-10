package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.databinding.FragmentUpdateReviewBinding
import fi.giao.finnishparliamentapp.viewmodel.ReviewViewModel
import fi.giao.finnishparliamentapp.viewmodel.ReviewViewModelFactory

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This fragment uses the ReviewViewModel and activates the update and delete reviews based on user
 * input.
 */
class UpdateReviewFragment : Fragment() {
    private lateinit var binding: FragmentUpdateReviewBinding
    private val safeArgs: UpdateReviewFragmentArgs by navArgs()
    private val viewModel: ReviewViewModel by viewModels {
        ReviewViewModelFactory(requireActivity().application)
    }
    private lateinit var currentReview: MemberReview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentReview = safeArgs.requestedReview
        binding.apply {
            updateStarRatingBar.rating = currentReview.rating
            updateCommentEditText.setText(currentReview.comment)
        }
        binding.updateReviewButton.setOnClickListener {
            updateReview()
        }
    }

    private fun updateReview() {
        val updatedReview = MemberReview(
            currentReview.reviewId, currentReview.hetekaId,
            binding.updateStarRatingBar.rating, binding.updateCommentEditText.text.toString(),
            currentReview.timeStamp
        )
        viewModel.updateReview(updatedReview)
        findNavController().popBackStack(R.id.updateReviewFragment, true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_review -> {
                viewModel.deleteReview(currentReview)
                Toast.makeText(
                    requireContext(),
                    "Review: \"${currentReview.comment}\" is deleted",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack(R.id.updateReviewFragment, true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}