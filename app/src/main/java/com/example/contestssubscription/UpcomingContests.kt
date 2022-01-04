package com.example.contestssubscription

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestssubscription.adapters.ContestAdapter
import com.example.contestssubscription.data.UserApplication
import com.example.contestssubscription.viewModels.LoggedInViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class UpcomingContests : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var loggedInViewModel: LoggedInViewModel
    private val contestAdapter: ContestAdapter = ContestAdapter(ArrayList())
    private val viewModel: UserSitesViewModel by activityViewModels {
        UserSitesViewModelFactory((activity?.application as UserApplication).database.userDao())
    }
    var codeforces = true
    var codeChef = true
    var atCoder = true



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



        loggedInViewModel.getContests(codeforces, codeChef, atCoder)
            .observe(viewLifecycleOwner, {
                contestAdapter.updateData(it)
                recyclerView.adapter = contestAdapter
            })

        return view
    }

    override fun onStart() {
        loggedInViewModel.getUserLiveData().value?.uid?.let { Log.e("upcom", it) }
        val uid = loggedInViewModel.getUserLiveData().value?.uid
        if (uid != null) {
            GlobalScope.async {
                val settings = viewModel.retrieveUser(uid)
                Log.e("sdg0", "sadhrt")
                codeforces = settings.codeforces
                codeChef = settings.codeChef
                atCoder = settings.atCoder
                val data = loggedInViewModel.getContests(codeforces, codeChef, atCoder).value
                contestAdapter.updateData(data!!)
            }
        }
        super.onStart()
    }

}