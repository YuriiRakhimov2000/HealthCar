package com.yura.newaws.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.amplifyframework.datastore.generated.model.Profile
import com.google.android.material.switchmaterial.SwitchMaterial
import com.yura.newaws.AppPreferences
import com.yura.newaws.R
import java.util.*


class AddProfile : Fragment() {
 lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_profile, container, false)
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(ProfileViewModel::class.java)
        val addButton = view.findViewById<Button>(R.id.btn_add)
        val makeeditText = view.findViewById<EditText>(R.id.editTextMake)
        val modeleditText = view.findViewById<EditText>(R.id.editTextModel)
        val yeareditText = view.findViewById<EditText>(R.id.editTextYear)
        val defaultSwitch = view.findViewById<Switch>(R.id.switch_add)
        addButton.setOnClickListener {
            var profile = Profile.builder()
                .make(makeeditText.text.toString())
                .sub(AppPreferences.Sub)
                .model(modeleditText.text.toString())
                .year(yeareditText.text.toString())
                .id(UUID.randomUUID().toString())
                .build()

            viewModel.create(profile)
            Log.d("MyAmplifyApp", "DefaultValue: ${AppPreferences.DefaultProfile}")
            if (defaultSwitch.isChecked){
                AppPreferences.DefaultProfile =  profile.id
                Log.d("MyAmplifyApp", "DefaultValue: ${AppPreferences.DefaultProfile}")
            }

            findNavController().navigateUp()
        }
        return view
    }


}