package com.example.contestssubscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation


class LoginFragment : Fragment() {

    private lateinit var loginButton: Button
    private lateinit var password: EditText
    private lateinit var email: EditText

    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginRegisterViewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        loginButton = view.findViewById(R.id.loginButton)
        email = view.findViewById(R.id.editTextTextEmailAddress)
        password = view.findViewById(R.id.editTextTextPassword)

        loginRegisterViewModel.getUserLiveData().observe(viewLifecycleOwner, Observer {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_upcomingContests)
        })

        loginButton.setOnClickListener {
            val emailText: String = email.text.toString()
            val passwordText: String = password.text.toString()
            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                loginRegisterViewModel.login(emailText, passwordText)
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
}