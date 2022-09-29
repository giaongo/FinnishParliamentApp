package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import fi.giao.finnishparliamentapp.databinding.FragmentAddReviewBinding

class AddReviewFragment : Fragment() {
    private lateinit var binding: FragmentAddReviewBinding
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

    }
}
