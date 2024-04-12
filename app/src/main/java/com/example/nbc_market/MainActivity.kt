package com.example.nbc_market


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dummyData()
        binding.rvMain.adapter = PostAdapter(dummyItems)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.ivAlarm.setOnClickListener {
            notificationChannel()
        }

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

    private fun notificationChannel() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()){
                // 알람 권한 요청
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
            // 26 버전 이상 일때
            val channelId = "default"
            val channelName = "Default Channel"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // 채널 설정
                description = "채널 생성"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                // 알람 오디오 설정
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            // 채널을 NotificationChannel에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용한 builder 설정
            builder = NotificationCompat.Builder(this,channelId)
        }else {
            // 26 버전 이하 일때
            builder = NotificationCompat.Builder(this)
        }
        // 알람 정보 설정
        builder.run {
            setSmallIcon(R.drawable.btn_radius)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
        }
        manager.notify(1,builder.build())

    }

}