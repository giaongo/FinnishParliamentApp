package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.databinding.FragmentUpdateReviewBinding
import fi.giao.finnishparliamentapp.databinding.ReviewBinding
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModel
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModelFactory

class UpdateReviewFragment : Fragment() {
    private lateinit var binding: FragmentUpdateReviewBinding
    private val safeArgs: UpdateReviewFragmentArgs by navArgs()
    private val viewModel:MemberInfoViewModel by activityViewModels {
        MemberInfoViewModelFactory(requireActivity().application)
    }
    private lateinit var currentReview:MemberReview
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateReviewBinding.inflate(inflater,container,false)
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
        val updatedReview = MemberReview(currentReview.reviewId,currentReview.hetekaId,
        binding.updateStarRatingBar.rating,binding.updateCommentEditText.text.toString(),
        currentReview.timeStamp)
        viewModel.updateReview(updatedReview)
        findNavController().popBackStack(R.id.updateReviewFragment,true)
    }
}