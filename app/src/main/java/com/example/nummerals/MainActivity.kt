package com.example.nummerals

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.activity_main.*

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
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)

        if(item.itemId == R.id.nav_source) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/khomin/Numerals"))
            startActivity(browserIntent)
        }

        mNavController.navigate(when (item.itemId){
            R.id.nav_exercise -> R.id.mainScreenFragment
            R.id.nav_about -> R.id.aboutFragment
            else -> R.id.mainScreenFragment
        })

        return true
    }

}
