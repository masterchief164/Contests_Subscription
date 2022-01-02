package com.example.contestssubscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.contestssubscription.data.UserApplication
import com.example.contestssubscription.viewModels.LoginRegisterViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModel
import com.example.contestssubscription.viewModels.UserSitesViewModelFactory

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
        registerButton = view.findViewById(R.id.loginButton)
        email = view.findViewById(R.id.editTextTextEmailAddress)
        password = view.findViewById(R.id.editTextTextPassword)

        loginRegisterViewModel.getUserLiveData().observe(viewLifecycleOwner, {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_upcomingContests)
        })

        registerButton.setOnClickListener {
            val emailText: String = email.text.toString()
            val passwordText: String = password.text.toString()
            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                loginRegisterViewModel.register(emailText, passwordText)
                addUser()
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

    private fun addUser() {
        val user = loginRegisterViewModel.getUserLiveData()
        viewModel.addNewUser(
            name.text.toString(),
            user.value!!.uid,email.text.toString(),true, atCoder = true, codeChef = true
        )
    }

}