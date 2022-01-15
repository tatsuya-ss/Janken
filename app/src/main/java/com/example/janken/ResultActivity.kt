package com.example.janken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.janken.databinding.ActivityMainBinding
import com.example.janken.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupMyHandImage()
    }

    fun setupBinding() {
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setupMyHandImage() {
        val id = intent.getIntExtra("MY_HAND", 0)
        when(id) {
            R.id.gu -> binding.myHandImage.setImageResource(R.drawable.gu)
            R.id.choki -> binding.myHandImage.setImageResource(R.drawable.choki)
            R.id.pa -> binding.myHandImage.setImageResource(R.drawable.pa)
        }
    }

}