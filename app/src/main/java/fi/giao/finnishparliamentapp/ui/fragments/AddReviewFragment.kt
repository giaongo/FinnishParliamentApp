package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.databinding.FragmentAddReviewBinding
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModel
import fi.giao.finnishparliamentapp.viewmodel.MemberInfoViewModelFactory

class AddReviewFragment : Fragment() {
    private lateinit var binding: FragmentAddReviewBinding
    private val viewModel: MemberInfoViewModel by activityViewModels {
        MemberInfoViewModelFactory(requireActivity().application)
    }

    private val safeArgs:AddReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Set listener to be called when the rating changes
         * Reference
         * https://developer.android.com/reference/android/widget/RatingBar#setOnRatingBarChangeListener(android.widget.RatingBar.OnRatingBarChangeListener)
         *
         */
        binding.starRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(requireContext(), "You rate $rating stars",Toast.LENGTH_SHORT).show()
        }
        val receivedHetekaId = safeArgs.hetekaId
        val timeStamp = System.currentTimeMillis()
        binding.apply {
            addReviewButton.setOnClickListener {
                val rating = starRatingBar.rating
                val comment = addCommentEditText.text.toString()
                viewModel.insertReview(MemberReview(0,receivedHetekaId,rating,comment,timeStamp))
                //Pop this fragment out of the backstack
                findNavController().popBackStack(R.id.addReviewFragment,true)
            }
        }

    }
}
