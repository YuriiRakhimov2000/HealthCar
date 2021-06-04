package com.yura.newaws.ui.realtimeData

import android.content.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.github.anastr.speedviewlib.SpeedView
import com.sohrab.obd.reader.constants.DefineObdReader
import com.sohrab.obd.reader.obdCommand.ObdCommand
import com.sohrab.obd.reader.obdCommand.ObdConfiguration
import com.sohrab.obd.reader.obdCommand.SpeedCommand
import com.sohrab.obd.reader.obdCommand.engine.RPMCommand
import com.sohrab.obd.reader.obdCommand.temperature.EngineCoolantTemperatureCommand
import com.sohrab.obd.reader.service.ObdReaderService
import com.sohrab.obd.reader.trip.TripRecord
import com.yura.newaws.R

class RealTimeDataFragment : Fragment() {
    val intentFilter = IntentFilter()
    var obdCommands =  ArrayList<ObdCommand>()
    lateinit var speedometer: SpeedView
    lateinit var tachometer: SpeedView
    lateinit var temperature: SpeedView
    lateinit var vp: SharedPreferences
init {

    obdCommands.add(SpeedCommand())
    obdCommands.add(RPMCommand())
    obdCommands.add(EngineCoolantTemperatureCommand())
    ObdConfiguration.setmObdCommands(context,null)

    intentFilter.addAction(DefineObdReader.ACTION_READ_OBD_REAL_TIME_DATA)
    intentFilter.addAction(DefineObdReader.ACTION_CONNECTION_STATUS_MSG)

}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vp = PreferenceManager.getDefaultSharedPreferences(context)
        val view = inflater.inflate(R.layout.real_time_data_fragment, container, false)
        speedometer = view.findViewById<SpeedView>(R.id.speedometer)
        tachometer = view.findViewById<SpeedView>(R.id.tachometer)
        temperature = view.findViewById<SpeedView>(R.id.temperature)
        speedometer.maxSpeed=220f
        speedometer.minSpeed=0f
        speedometer.unit="Km/h"
        temperature.minSpeed=0f
        temperature.maxSpeed=150f
        temperature.unit="C"
        if (vp.getString("speed","")=="ml"){
            speedometer.maxSpeed = ((speedometer.maxSpeed*0.67) - (speedometer.maxSpeed*0.67)%10).toFloat()
            speedometer.unit = "Ml/h"
        }
        if (vp.getString("temperature","")=="K"){
            temperature.maxSpeed = temperature.maxSpeed+270
            temperature.unit ="K"
        }
        if (vp.getString("temperature","")=="F"){
            val far = temperature.maxSpeed*(9/5)+32
            temperature.maxSpeed = (far-(far%10)).toFloat()
            temperature.unit ="F"
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(UIObdReaderReceiver, intentFilter);

    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(UIObdReaderReceiver);
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    val UIObdReaderReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
           /* findViewById<View>(R.id.progress_bar).visibility = View.GONE
            mObdInfoTextView!!.visibility = View.VISIBLE*/
            val action = intent.action
            if (action == DefineObdReader.ACTION_CONNECTION_STATUS_MSG) {
                val connectionStatusMsg = intent.getStringExtra(ObdReaderService.INTENT_EXTRA_DATA)
                //mObdInfoTextView!!.text = connectionStatusMsg
                Toast.makeText(activity, connectionStatusMsg, Toast.LENGTH_SHORT).show()
                if (connectionStatusMsg == getString(R.string.obd_connected)) {
                    //OBD connected  do what want after OBD connection
                    //updateUI()
                } else if (connectionStatusMsg == getString(R.string.connect_lost)) {
                    //OBD disconnected  do what want after OBD disconnection
                  /*  speedometer.speedTo(0f)
                    tachometer.speedTo(0f)
                    temperature.speedTo(0f)*/
                } else {
                    // here you could check OBD connection and pairing status
                }
            } else if (action == DefineObdReader.ACTION_READ_OBD_REAL_TIME_DATA) {
                val tripRecord = TripRecord.getTripRecode(activity)

               // mObdInfoTextView!!.text = tripRecord.toString()
                // here you can fetch real time data from TripRecord using getter methods like
                //tripRecord.getSpeed();
                //tripRecord.getEngineRpm();

                    speedometer.speedTo(tripRecord.speed.toFloat())
                    tachometer.speedTo(tripRecord.engineRpm.toFloat())
                    temperature.speedTo(tripRecord.getmEngineCoolantTemp().toFloat())

            }
        }
    }
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