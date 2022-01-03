package com.example.contestssubscription

import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.contestssubscription.data.UserApplication
import com.example.contestssubscription.viewModels.LoginRegisterViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class Register : Fragment() {

    private lateinit var registerButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var name: EditText


    private lateinit var loginRegisterViewModel: LoginRegisterViewModel
    private val viewModel: UserSitesViewModel by activityViewModels {
        UserSitesViewModelFactory((activity?.application as UserApplication).database.userDao())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginRegisterViewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        registerButton = view.findViewById(R.id.registerButton)
        email = view.findViewById(R.id.editTextTextEmailAddress)
        password = view.findViewById(R.id.editTextTextPassword)
        confirmPassword = view.findViewById(R.id.confirmPassword)
        name = view.findViewById(R.id.name)

        val login: TextView = view.findViewById(R.id.login)

        login.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_loginFragment)
        }

        loginRegisterViewModel.getUserLiveData().observe(viewLifecycleOwner, {
            Navigation.findNavController(view)
                .navigate(R.id.action_register_to_upcomingContests)
        })

        registerButton.setOnClickListener {
            val emailText: String = email.text.toString()
            val passwordText: String = password.text.toString()
            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                if (passwordText != confirmPassword.text.toString())
                    Toast.makeText(
                        context,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    GlobalScope.async {
                        loginRegisterViewModel.register(emailText, passwordText)
                        e("wgr","awgtsrj")
                        e("aTR","fhyjrny")
                        delay(1000)
                        viewModel.retrieveUser("dgr")

                        addUser()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Email Address and Password Must Be Entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return view
    }

    private suspend fun addUser() {
        val user = loginRegisterViewModel.getUserLiveData()
        e("Register","hello")
        delay(1000)
        e("Register", user.value!!.uid)
        viewModel.addNewUser(
            name.text.toString(),
            user.value!!.uid,
            email.text.toString(),
            true,
            atCoder = true,
            codeChef = true
        )

    }

}