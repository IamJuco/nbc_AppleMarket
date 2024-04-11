package com.example.nbc_market

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nbc_market.Util.dummyData
import com.example.nbc_market.Util.dummyItems
import com.example.nbc_market.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dummyData()
        binding.rvMain.adapter = PostAdapter(dummyItems)


    }
}