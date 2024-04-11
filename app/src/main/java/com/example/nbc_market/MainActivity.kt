package com.example.nbc_market


import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import com.example.nbc_market.Util.dummyData
import com.example.nbc_market.Util.dummyItems
import com.example.nbc_market.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showBackPressedDialog()
            }
        }

//    private val myNotificationID = 1
//
//    private val channelID = "default"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dummyData()
        binding.rvMain.adapter = PostAdapter(dummyItems)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun showBackPressedDialog() {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("종료")
            .setMessage("정말 종료하시겠습니까?")
            .setIcon(R.drawable.chat)
            .setPositiveButton("아니요") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("예") { _, _ ->
                finish()
            }
            .create()
            .show()
    }

//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelID, "default channel",
//                NotificationManager.IMPORTANCE_DEFAULT)
//        }
//    }
}