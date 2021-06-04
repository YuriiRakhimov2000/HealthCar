package com.yura.newaws.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amplifyframework.datastore.generated.model.Profile
import com.yura.newaws.AppPreferences
import com.yura.newaws.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {

    lateinit var profileViewModel: ProfileViewModel
    lateinit var profile_id :String
    lateinit var profile: Profile
    lateinit var make:EditText
    lateinit var model:EditText
    lateinit var year:EditText
    lateinit var save:Button
    lateinit var delete:Button
    lateinit var switch:Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile_id = requireArguments().getString("profile_id")!!
        profileViewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(ProfileViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {

       val task = async(Dispatchers.Default){
           profileViewModel.query(profile_id)
       }
        task.await()

        profile = profileViewModel.profile!!



        make.setText(profile.make)
        year.setText(profile.year)
        model.setText(profile.model)
        if (AppPreferences.DefaultProfile==profile_id){
            switch.isChecked = true
            switch.isEnabled = false
            delete.isEnabled=false
            Log.d("MyAmplifyApp", "edit default")
        }

        save.setOnClickListener {
            val edited = profile.copyOfBuilder()
                .make(make.text.toString())
                .model(model.text.toString())
                .year(year.text.toString())
                .build()

            viewLifecycleOwner.lifecycleScope.launch {
                profileViewModel.update(edited)
                findNavController().navigateUp()
            }
            if (AppPreferences.DefaultProfile!=profile_id && switch.isChecked){
                    AppPreferences.DefaultProfile = profile_id
            }
        }

        delete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                profileViewModel.delete(profile_id)

                findNavController().navigateUp()

            }
        }

    }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details, container, false)
        make = view.findViewById<EditText>(R.id.editTextSaveMake)
        model = view.findViewById<EditText>(R.id.editTextSaveModel)
        year = view.findViewById<EditText>(R.id.editTextYearSave)
        save = view.findViewById<Button>(R.id.btn_save)
        delete = view.findViewById<Button>(R.id.btn_delete)
        switch = view.findViewById<Switch>(R.id.switch_edit)

        return view
    }


}