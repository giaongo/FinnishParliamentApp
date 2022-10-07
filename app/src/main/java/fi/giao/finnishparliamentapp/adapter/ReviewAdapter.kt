package fi.giao.finnishparliamentapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.databinding.ReviewBinding
import java.sql.Timestamp
import java.text.SimpleDateFormat
/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This recycler view adapter is for recycler view in MemberInfoFragment
 */
class ReviewAdapter(val context: Context, private val listener: (MemberReview) -> Unit): ListAdapter<MemberReview, ReviewAdapter.ReviewViewHolder>(ReviewDiffCallBack) {
    inner class ReviewViewHolder(private val binding: ReviewBinding):RecyclerView.ViewHolder(binding.root) {
        private val simpleDateFormat = SimpleDateFormat("HH:mm" + "-dd.MM.yyyy")

        init {
            binding.root.setOnClickListener {
                listener(getItem(adapterPosition))
            }
        }
        fun bindData(review:MemberReview) {
            val timestamp = simpleDateFormat.format(Timestamp(review.timeStamp))
            binding.apply {
                reviewRatingBar.rating = review.rating
                reviewTextView.text = context.getString(R.string.review_text,review.comment.trim())
                reviewTimestamp.text = context.getString(R.string.review_timestamp,timestamp)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = ReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object ReviewDiffCallBack: DiffUtil.ItemCallback<MemberReview>() {
        override fun areItemsTheSame(oldItem: MemberReview, newItem: MemberReview): Boolean {
            return oldItem.reviewId == newItem.reviewId
        }

        override fun areContentsTheSame(oldItem: MemberReview, newItem: MemberReview): Boolean {
            return oldItem == newItem
        }
    }
}