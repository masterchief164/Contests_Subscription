package com.example.contestssubscription

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContestsAdapter(private val contests: ArrayList<Contest>) : RecyclerView.Adapter<ContestsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.title)
        val contTime: TextView = view.findViewById(R.id.subtitle)
        val icon: ImageView = view.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.title.text = contests[position].contestName
        viewHolder.contTime.text = contests[position].contestTime
        viewHolder.icon.setImageResource(R.drawable.ic_launcher_background)
    }

    override fun getItemCount(): Int {
        return contests.size
    }
}