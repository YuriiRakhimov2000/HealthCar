 package com.yura.newaws

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.sohrab.obd.reader.application.Preferences
import com.sohrab.obd.reader.service.ObdReaderService


 class MainActivity : AppCompatActivity() {


     private lateinit var appBarConfiguration: AppBarConfiguration

     lateinit var some :String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (AppPreferences.firstRun) {
                AppPreferences.firstRun = true
                Log.d("SpinKotlin", "The value of our pref is: ${AppPreferences.firstRun}")
            }



            setContentView(R.layout.activity_main)

            val toolbar: Toolbar = findViewById(R.id.toolbar)

            setSupportActionBar(toolbar)


            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            val navView: NavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home, R.id.aboutUsFragment, R.id.profileFragmentList,R.id.settingsFragment
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            startService(Intent(this, ObdReaderService::class.java))


        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return item.onNavDestinationSelected(
             findNavController(R.id.nav_host_fragment)
         ) || super.onOptionsItemSelected(item)
     }




    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


     override fun onDestroy() {
         super.onDestroy()
         stopService(Intent(this, ObdReaderService::class.java))
         Preferences.get(this).setServiceRunningStatus(false);

     }
}