package com.example.contestssubscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.contestssubscription.data.UserApplication
import com.example.contestssubscription.viewModels.LoggedInViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class SelectSites : Fragment() {
    private lateinit var codeforcesToggle: SwitchCompat
    private lateinit var codeChefToggle: SwitchCompat
    private lateinit var atCoderToggle: SwitchCompat
    private val viewModel: UserSitesViewModel by activityViewModels {
        UserSitesViewModelFactory((activity?.application as UserApplication).database.userDao())
    }
    private lateinit var loggedInViewModel: LoggedInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loggedInViewModel = ViewModelProvider(this)[LoggedInViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_select_sites, container, false)
        codeforcesToggle = view.findViewById(R.id.CodeforcesSwitch)
        codeChefToggle = view.findViewById(R.id.CodeChefSwitch)
        atCoderToggle = view.findViewById(R.id.atCoderSwitch)


        val uid = loggedInViewModel.getUserLiveData().value?.uid
        if (uid != null) {
            GlobalScope.async {
                val settings = viewModel.retrieveUser(uid)
                codeforcesToggle.isChecked = settings.codeforces
                codeChefToggle.isChecked = settings.codeChef
                atCoderToggle.isChecked = settings.atCoder
            }

        } else {
            Toast.makeText(activity, "Please login to save settings", Toast.LENGTH_SHORT).show()
        }
        codeforcesToggle.setOnCheckedChangeListener { _, isChecked ->

            val message = if (isChecked) "Codeforces On" else "Codeforces off"
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            if (uid != null) {
                GlobalScope.async {
                    val user = viewModel.retrieveUser(uid)
                    user.codeforces = codeforcesToggle.isChecked
                    viewModel.updateUserData(user)
                }
            }

        }
        codeChefToggle.setOnCheckedChangeListener { _, isChecked ->

            val message = if (isChecked) "CodeChef On" else "CodeChef off"
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            GlobalScope.async {
                if (uid != null) {
                    val user = viewModel.retrieveUser(uid)
                    user.codeChef = codeChefToggle.isChecked
                    viewModel.updateUserData(user)
                }
            }
        }
        atCoderToggle.setOnCheckedChangeListener { _, isChecked ->

            val message = if (isChecked) "AtCoder On" else "AtCoder off"
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            if (uid != null) {
                GlobalScope.async {
                    val user = viewModel.retrieveUser(uid)
                    user.atCoder = atCoderToggle.isChecked
                    viewModel.updateUserData(user)
                }
            }
        }
        return view
    }



    override fun onStop() {
        super.onStop()
        Toast.makeText(activity,"Restart app for changes to take place", Toast.LENGTH_SHORT).show()
    }
}