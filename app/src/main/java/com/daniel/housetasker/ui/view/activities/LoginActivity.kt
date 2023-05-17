package com.daniel.housetasker.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daniel.housetasker.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}