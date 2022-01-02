package com.example.contestssubscription

import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestssubscription.adapters.ContestAdapter
import com.example.contestssubscription.viewModels.LoggedInViewModel


class UpcomingContests : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var loggedInViewModel: LoggedInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loggedInViewModel = ViewModelProvider(this)[LoggedInViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming_contests, container, false)
        recyclerView = view.findViewById(R.id.RecyclerView)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        e("Upcoming",loggedInViewModel.getLoggedOutLiveData().value.toString())
        e("Upcoming",loggedInViewModel.getUserLiveData().value.toString())
        loggedInViewModel.getContests().observe(viewLifecycleOwner, {
            recyclerView.adapter = ContestAdapter(it)
        })

        return view
    }

}