package com.yura.newaws.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import com.yura.newaws.AppPreferences
import com.yura.newaws.R
import com.yura.newaws.ui.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeProfileFragment : Fragment() {
    lateinit var profileViewModel: ProfileViewModel

   init {
       Log.d("MyAmplifyApp", "vdv: ")
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_profile, container, false)
        val make = view.findViewById<TextView>(R.id.main_profile_make)
        val model = view.findViewById<TextView>(R.id.main_profile_model)
        Log.d("MyAmplifyApp", "onCreateView: ${AppPreferences.DefaultProfile}")
        if (AppPreferences.DefaultProfile != ""){
            Log.d("MyAmplifyApp", "onCreateView: ")
            viewLifecycleOwner.lifecycleScope.launch(){
                viewLifecycleOwner.lifecycleScope.launch(){
                    withContext(Dispatchers.IO) {
                        profileViewModel.query(AppPreferences.DefaultProfile!!)
                    }
                }.join()
                val profile = profileViewModel.profile
                make.text=profile!!.make
                model.text= profile.model
            }

        }
        else{
            make.text=getString(R.string.default_make)
            model.text=getString(R.string.default_model)
        }

        return view
    }


}