package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.startText.setOnClickListener {

            val height = binding.constrainlayout.height
            val width = binding.constrainlayout.width

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("width", width)

            startActivity(intent)

            finish()
        }
    }
}