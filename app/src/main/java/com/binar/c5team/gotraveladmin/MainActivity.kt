package com.binar.c5team.gotraveladmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.binar.c5team.gotraveladmin.databinding.ActivityMainBinding
import com.binar.c5team.gotraveladmin.model.user.User
import com.binar.c5team.gotraveladmin.view.LoginActivity
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    private lateinit var tvName: TextView
    private lateinit var imgProfile : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        val token = sharedPref.getString("token","").toString()
        val id = sharedPref.getInt("userId",0)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_selectBookingFragment, R.id.nav_plane, R.id.nav_airport, R.id.nav_flight,R.id.nav_admin ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.btnLogout.setOnClickListener {
            val datalogin = sharedPref.edit()
            datalogin.clear()
            datalogin.apply()
            startActivity(Intent(this,LoginActivity::class.java))
        }

        val header = navView.getHeaderView(0)
        tvName = header.findViewById(R.id.tv_name_profile)
        imgProfile = header.findViewById(R.id.img_profile)
        getDataUser(token,id)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun getDataUser(token: String,id: Int) {
        val viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        viewModel.getUserData().observe(this) {
            if (it != null) {
                val filterUser: MutableList<User> = ArrayList()
                for (i in it.data.users) {
                    if (i.id == id) {
                        filterUser.add(i)
                    }
                }
                tvName.text = filterUser[0].name

                if (filterUser[0].role == "superAdmin") {
                    imgProfile.setImageResource(R.drawable.super_admin)
                } else {
                    imgProfile.setImageResource(R.drawable.basic_admin)
                    hideItem()
                }
            }
        }
        viewModel.callUserData(token)
    }

    private fun hideItem() {
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val nav_Menu: Menu = navigationView.getMenu()
        nav_Menu.findItem(R.id.nav_admin).setVisible(false)
    }


}