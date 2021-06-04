package com.yura.newaws.ui.aboutUs

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yura.newaws.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutUsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val versionElement = Element()
        versionElement.setTitle("Version 1.0")
        val copyElement =Element()
        copyElement.setTitle("Copyright 2021 by Yurii Rakhimov")
        copyElement.gravity=Gravity.CENTER
        return   AboutPage(getContext())
            .isRTL(false)
            .setDescription(getString(R.string.app_description))
            .addGroup(getString(R.string.contact_group))
            .addEmail("yyuuraganov@gmail.com", "Email")
            .addGitHub("YuriiRakhimov2000")
            .addItem(versionElement)
            .addItem(copyElement)
            .create()
    }


}