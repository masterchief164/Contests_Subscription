package com.example.contestssubscription.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestssubscription.data.Contest
import com.example.contestssubscription.R

class ContestAdapter(private val contests: ArrayList<Contest>) :
    RecyclerView.Adapter<ContestAdapter.ContestViewHolder>() {
    class ContestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contestName: TextView = view.findViewById(R.id.textView2)
        val timeView: TextView = view.findViewById(R.id.textView3)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.contest_item_layout, parent, false)
        return ContestViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val item = contests[position]
        if(item.name.length>25)
        holder.contestName.text =  (item.name.substring(0,25)+"...")
        else
            holder.contestName.text = item.name

        holder.timeView.text = item.startTimeSeconds.toString()
        holder.imageView.setImageResource(R.drawable.ic_launcher_background)

    }

    override fun getItemCount() = contests.size
}