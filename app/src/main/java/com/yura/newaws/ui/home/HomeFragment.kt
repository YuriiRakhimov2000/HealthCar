package com.yura.newaws.ui.home

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yura.newaws.R

class HomeFragment : Fragment() {
    lateinit var realTimeDataButton:Button
    lateinit var allDataButton:Button
    lateinit var troubleCodesButton:Button
    lateinit var profilesButton:Button
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        realTimeDataButton = view.findViewById<Button>(R.id.btn_realTime)
        allDataButton = view.findViewById<Button>(R.id.btn_AllData)
        troubleCodesButton = view.findViewById<Button>(R.id.btn_troubleCodes)
        profilesButton = view.findViewById<Button>(R.id.btn_profiles)
        allDataButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_allDataFragment)
        }
        realTimeDataButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_realTimeDataFragment)
        }
        troubleCodesButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_troubleCodeFragment)
        }
        profilesButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_profileFragmentList)
        }
        return view

    }

    override fun onStart() {
        super.onStart()
        Log.d("MyAmplifyApp", "onStart: ")
    }
}



