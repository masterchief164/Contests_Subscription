package com.example.contestssubscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


class UpcomingContests : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming_contests, container, false)


        val first = Contest("name", "hello")
        val second = Contest("wpig", "gstrr")

        val contestList: ArrayList<Contest> = ArrayList()

        contestList.add(first)
        contestList.add(second)


        val recyclerView = view.findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.adapter = ContestsAdapter(contestList)
        recyclerView.setHasFixedSize(true)
        return view
    }
}