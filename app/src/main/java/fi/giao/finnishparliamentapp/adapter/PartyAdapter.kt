package fi.giao.finnishparliamentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fi.giao.finnishparliamentapp.R

class PartyAdapter(val onItemClick : (String) -> Unit) : ListAdapter<String, PartyAdapter.PartyViewHolder>(DiffCallBack){
    inner class PartyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText:TextView = itemView.findViewById(R.id.item_text_view)
        init {
            itemView.setOnClickListener {
                onItemClick(getItem(adapterPosition))
            }
        }
    }

    // Create ViewHolder and set item click listener on ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return PartyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        holder.itemText.text = getItem(position)
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

}