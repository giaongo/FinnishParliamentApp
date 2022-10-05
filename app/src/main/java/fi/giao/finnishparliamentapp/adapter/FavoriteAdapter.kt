package fi.giao.finnishparliamentapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.databinding.ItemFavoriteBinding
import fi.giao.finnishparliamentapp.ui.fragments.IMG_BASE_URL

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This recycler view adapter is for recycler view in FavoriteListFragment
 */
class FavoriteAdapter(val context: Context,
                      val viewMoreListener:(ParliamentMember) -> Unit,
                      val unMarkListener: (ParliamentMember) -> Unit)
    : ListAdapter<ParliamentMember, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallBack) {
    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                viewMoreButton.setOnClickListener{
                    viewMoreListener(getItem(adapterPosition))
                }
                unMarkButton.setOnClickListener {
                    unMarkListener(getItem(adapterPosition))
                }
            }
        }
        fun bindData(member: ParliamentMember) {
            binding.apply {
                Glide.with(context).load(IMG_BASE_URL + member.pictureUrl)
                    .placeholder(R.drawable.loading_icon)
                    .error(R.drawable.error_icon)
                    .into(favoriteImageView)
                favoriteNameTextView.text = context.getString(R.string.favorite_name_text,
                member.firstname,member.lastname)
                favoritePartyTextView.text = context.getString(R.string.favorite_party_text,
                member.party)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object FavoriteDiffCallBack: DiffUtil.ItemCallback<ParliamentMember>() {

        override fun areItemsTheSame(
            oldItem: ParliamentMember,
            newItem: ParliamentMember
        ): Boolean {
            return oldItem.hetekaId == newItem.hetekaId
        }

        override fun areContentsTheSame(
            oldItem: ParliamentMember,
            newItem: ParliamentMember
        ): Boolean {
            return oldItem == newItem
        }
    }
}