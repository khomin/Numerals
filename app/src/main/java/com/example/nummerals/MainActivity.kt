package com.example.nummerals

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mDrawerLayout : DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mNavController: NavController
    private var mAlertDialog: AlertDialog?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mToolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(mToolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavigationView = findViewById(R.id.nav_view)
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayout)
        NavigationUI.setupWithNavController(mNavigationView, mNavController)

        mNavigationView.setNavigationItemSelectedListener(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        mAlertDialog = AlertDialog.Builder(this).create()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, mDrawerLayout)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        mDrawerLayout.let { mDrawerLayout->
            item.isChecked = true
            mDrawerLayout.closeDrawer(GravityCompat.START)

//            if (item.itemId == R.id.nav_logout) {
//                val intent = Intent(this@MainActivity, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//            mNavController.navigate(when (item.itemId){
//                R.id.nav_about -> R.id.fragmentAbout
//                else -> R.id.fragmentAbout//R.id.underConstruction
//            })
        }
        return true
    }

//    override fun onEventDeviceClickAdapterItem(device: DeviceProperty) {
//        fuelConfigure = FuelConfigureSimulation(device)
//        /* if connection lost */
//        (fuelConfigure as FuelConfigure).setConnectionListenerFromActivity {
//            Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
//                mNavController.popBackStack(R.id.deviceItems, false)
//            })
//            DialogMessages().showDialogConnectionLost(mAlertDialog)
//        }
//        /* goto connection fragment */
//        mNavController.navigate(R.id.action_deviceItems_to_connectToLlsFragment)
//    }
//
//    override fun onEventMenuClickAdapterItem(menuRole: DeviceMenuRole) {
//        when(menuRole) {
//            DeviceMenuRole.INFORMATION-> {
//                mNavController.navigate(R.id.action_deviceMenuFragment_to_menuInformation)
//            }
//            DeviceMenuRole.SETTINGS -> {
//                mNavController.navigate(R.id.action_deviceMenuFragment_to_menuSettings)
//            }
//            DeviceMenuRole.TYPE_DATA_OUTPUT -> {
//                mNavController.navigate(R.id.action_deviceMenuFragment_to_menuTypeDataOutput)
//            }
//            DeviceMenuRole.FILTRATION -> {
//                mNavController.navigate(R.id.action_deviceMenuFragment_to_menuFiltration)
//            }
//            DeviceMenuRole.TERMO_COMPENSTATION -> {
//                mNavController.navigate(R.id.action_deviceMenuFragment_to_menuTermoCompensation)
//            }
//            DeviceMenuRole.CALIBRATION_TAR -> {
//                mNavController.navigate(R.id.action_deviceMenuFragment_to_menuCalibration)
//            }
//        }
//    }
}
