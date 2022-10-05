package fi.giao.finnishparliamentapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.ParliamentMember

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This recycler view adapter is for recycler view in MemberListFragment
 */
class MemberAdapter(val onItemClick: (ParliamentMember) -> Unit, val context: Context):
    ListAdapter<ParliamentMember,MemberAdapter.MemberViewHolder>(DiffCallBack) {

    // Defines MemberViewHolder and its constructor changes layout and activates click listener
    inner class MemberViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemText:TextView = itemView.findViewById(R.id.item_text_view)
        init {
            itemText.apply {
                isAllCaps = false
                setBackgroundColor(ContextCompat.getColor(context,R.color.teal_700))
            }

            itemView.setOnClickListener {
                onItemClick(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val firstName = getItem(position).firstname
        val lastName = getItem(position).lastname
        holder.itemText.text = context.getString(R.string.member_name_text,firstName,lastName)
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<ParliamentMember>() {
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