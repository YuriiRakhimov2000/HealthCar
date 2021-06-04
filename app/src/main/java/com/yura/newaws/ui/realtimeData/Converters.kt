package com.yura.newaws.ui.realtimeData


import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class Converters(context: Context,sp:SharedPreferences){


    val vp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun convert_speed(speed: Float):Float{
            if(vp.getString("speed", "") == "ml"){
                return (0.621371 * speed).toFloat()
            }
            return speed

    }

    fun convert_temperature(temperature:Float): Float {
        if (vp.getString("temperature","")=="F"){
            return ((temperature*(9/5))+32).toFloat()
        }
        if (vp.getString("temperature","")=="K"){
            return (temperature+271.3).toFloat()
        }
        return temperature
    }

}