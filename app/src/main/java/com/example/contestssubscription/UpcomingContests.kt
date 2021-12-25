package com.example.contestssubscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class UpcomingContests : Fragment() {

    //    private lateinit var factory:ContestsViewModelFactory
//    private lateinit var viewModel: ContestsViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contestViewModel: ContestsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming_contests, container, false)
        recyclerView = view.findViewById(R.id.RecyclerView)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        contestViewModel = ViewModelProvider(this)[ContestsViewModel::class.java]
        contestViewModel.getContests().observe(viewLifecycleOwner, {
            recyclerView.adapter = ContestAdapter(it)
        })

        return view
    }

}