package com.daniel.housetasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.daniel.housetasker.databinding.ActivityMainBinding
import com.daniel.housetasker.ui.view.fragments.HomeFragment
import com.daniel.housetasker.ui.view.fragments.HomeFragment.Companion.ARG_PARAM1
import com.daniel.housetasker.ui.view.fragments.HomeFragment.Companion.ARG_PARAM2

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.btnHome.setOnClickListener {
            val bundle = Bundle().apply {
                putString(ARG_PARAM1, "Daniel")
                putString(ARG_PARAM2, "daniel@gmail.com")
            }

            val homeFragment = HomeFragment()
            homeFragment.arguments = bundle

            binding.fragmentContainer.visibility = View.VISIBLE //Visible el HomeFragment

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment)
                .addToBackStack(null) // Opcional: agrega la transacción a la pila de retroceso
                .commit()
        }
    }
}