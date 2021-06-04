package com.yura.newaws.ui.troubleCodes.trouble_code

import java.util.ArrayList


object TroubleCodesContent {


    val ITEMS: MutableList<TroubleCode> = ArrayList()



    init {


    }

    private fun addItem(string: String) {
        if ( ITEMS.find { it.content == string } == null){
            ITEMS.add(TroubleCode(string))
        }
    }

    data class TroubleCode(val content: String) {
        override fun toString(): String = content
    }
}