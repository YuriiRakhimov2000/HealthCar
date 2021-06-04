package com.yura.newaws.ui.realtimeData

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.yura.newaws.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}