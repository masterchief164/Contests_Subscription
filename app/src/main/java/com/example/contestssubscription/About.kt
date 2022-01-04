package com.example.contestssubscription

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class About : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_about, container, false)
        val link: TextView = view.findViewById(R.id.link)
        val text = "GitHub Repo: <a href=\"https://github.com/masterchief164/Contests_Subscription\">Contest Subscription</a>"
        link.text= Html.fromHtml(text)
        return view
    }
}