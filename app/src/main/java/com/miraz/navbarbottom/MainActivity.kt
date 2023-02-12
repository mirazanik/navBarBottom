package com.miraz.navbarbottom

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.miraz.navbarbottom.databinding.ActivityMainBinding

import com.miraz.navbarbottom.model.NavObject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomBar.init(
            NavObject(
                name = "Flights",
                image = ContextCompat.getDrawable(this, R.drawable.ic_flight_black_24dp)!!
            ), arrayListOf(
                NavObject(
                    name = "Hotel",
                    image = this.getDrawable(R.drawable.ic_hotel_black_24dp)!!
                ),
                NavObject(
                    name = "Chat",
                    image = this.getDrawable(R.drawable.ic_forum_black_24dp)!!
                ),
                NavObject(
                    name = "Profile",
                    image = this.getDrawable(R.drawable.ic_account_circle_black_24dp)!!
                ),
                NavObject(
                    name = "Settings",
                    image = this.getDrawable(R.drawable.ic_settings_black_24dp)!!
                )
            )
        ) { position, primaryClicked ->

            Log.e("MainActivity", "onCreate: $position")
            when (position) {
                0 -> showFragment("Hotel")
                1 -> showFragment("Chat")
                2 -> showFragment("Profile")
                3 -> showFragment("Settings")
                else -> if (primaryClicked) showFragment("Flights")
            }
        }
    }

    private fun showFragment(displayString: String) {
        Log.e("MainActivity", "showFragment: $displayString")
        val fragment: Fragment = SomeFragment.newInstance(displayString)
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.container, fragment)
            .commit()
    }
}