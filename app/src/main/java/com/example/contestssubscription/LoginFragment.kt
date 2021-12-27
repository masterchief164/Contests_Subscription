package com.example.contestssubscription

import android.os.Bundle
import android.text.TextUtils
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser




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
                Navigation.findNavController(view).navigate(R.id.upcomingContests)
        })

        loginButton.setOnClickListener {
            when {
                TextUtils.isEmpty(email.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        activity,
                        "Please enter Email Address",
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(password.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        activity,
                        "Please enter Password",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    val emailText = email.text.toString().trim() { it <= ' ' }
                    val passwordText = password.text.toString().trim() { it <= ' ' }

                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val fireBaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    activity,
                                    "Registration Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }
            }

        }

        return view
    }
}