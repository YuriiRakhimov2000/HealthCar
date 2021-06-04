package com.yura.newaws.ui.allData


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sohrab.obd.reader.constants.DefineObdReader
import com.sohrab.obd.reader.obdCommand.ObdCommand
import com.sohrab.obd.reader.obdCommand.ObdConfiguration
import com.sohrab.obd.reader.obdCommand.SpeedCommand
import com.sohrab.obd.reader.obdCommand.engine.RPMCommand
import com.sohrab.obd.reader.obdCommand.temperature.EngineCoolantTemperatureCommand
import com.sohrab.obd.reader.service.ObdReaderService
import com.sohrab.obd.reader.trip.TripRecord
import com.yura.newaws.R







class AllDataFragment : Fragment() {
    val intentFilter = IntentFilter()
    var obdCommands =  ArrayList<ObdCommand>()
    lateinit var mObdInfoTextView :TextView


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
        val view = inflater.inflate(R.layout.fragment_all_data, container, false)
        mObdInfoTextView = view.findViewById<TextView>(R.id.tv_obd_info)


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
                mObdInfoTextView.text =(tripRecord.toString())

            }
        }
    }

}


