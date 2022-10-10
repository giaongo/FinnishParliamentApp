package fi.giao.finnishparliamentapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fi.giao.finnishparliamentapp.R
/**
 * Date: 10/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This recycler view adapter is for recycler view in StatisticFragment
 */
class StatisticAdapter(val context:Context): ListAdapter<Pair<String,Double>, StatisticAdapter.StatisticViewHolder>(StatisticDiffCallBack) {
    inner class StatisticViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.statistic_item,parent,false)
        return StatisticViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.statistic_item).text = context.getString(R.string.statistic_party_item,
        getItem(position).first,getItem(position).second.toString())
    }

    companion object StatisticDiffCallBack: DiffUtil.ItemCallback<Pair<String,Double>>() {
        override fun areItemsTheSame(
            oldItem: Pair<String, Double>,
            newItem: Pair<String, Double>
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<String, Double>,
            newItem: Pair<String, Double>
        ): Boolean {
            return oldItem == newItem
        }
    }
}