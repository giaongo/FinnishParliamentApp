package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.databinding.FragmentAddReviewBinding
import fi.giao.finnishparliamentapp.viewmodel.ReviewViewModel
import fi.giao.finnishparliamentapp.viewmodel.ReviewViewModelFactory

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This fragment uses ReviewViewModel that allows users to add their reviews (rating and comment)
 * to selected parliament member.
 */
class AddReviewFragment : Fragment() {
    private lateinit var binding: FragmentAddReviewBinding
    private val viewModel: ReviewViewModel by viewModels {
        ReviewViewModelFactory(requireActivity().application)
    }

    private val safeArgs: AddReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
         Set listener to be called when the rating changes
         Reference
         https://developer.android.com/reference/android/widget/RatingBar#setOnRatingBarChangeListener(android.widget.RatingBar.OnRatingBarChangeListener)
         */
        binding.starRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(requireContext(), "You rate $rating stars", Toast.LENGTH_SHORT).show()
        }
        val receivedHetekaId = safeArgs.hetekaId
        val timeStamp = System.currentTimeMillis()
        binding.apply {
            addReviewButton.setOnClickListener {
                val rating = starRatingBar.rating
                val comment = addCommentEditText.text.toString()
                viewModel.insertReview(
                    MemberReview(
                        0,
                        receivedHetekaId,
                        rating,
                        comment,
                        timeStamp
                    )
                )
                findNavController().popBackStack(R.id.addReviewFragment, true)
            }
        }

    }
}
