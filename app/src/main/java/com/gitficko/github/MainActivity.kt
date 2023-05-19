package com.gitficko.github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.gitficko.github.utils.Utils.Companion.navOptionsToLeft
import com.gitficko.github.utils.Utils.Companion.navOptionsToRight
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var previousMenuItemId: Int? = R.id.nav_homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Скрытие BottomNavigationView для фрагментов, кроме home и profile.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_homeFragment,
                R.id.nav_profileFragment -> bottomNavigationView.visibility = View.VISIBLE
                else -> bottomNavigationView.visibility = View.GONE
            }
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            if (navController.currentDestination?.id == item.itemId) {
                false
            } else {
                when (item.itemId) {
                    R.id.nav_homeFragment -> {
                        navController.navigate(R.id.nav_homeFragment, null, navOptionsToLeft)
                        previousMenuItemId = item.itemId
                        true
                    }
                    R.id.nav_profileFragment -> {
                        navController.navigate(R.id.nav_profileFragment, null, navOptionsToRight)
                        previousMenuItemId = item.itemId
                        true
                    }
                    else -> false
                }
            }
        }

    }
}
