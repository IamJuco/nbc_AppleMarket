package com.example.nbc_market


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_market.Util.returnDummyData
import com.example.nbc_market.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var postAdapter : PostAdapter

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showBackPressedDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setRecyclerview()
        itemClickListener()
        scrollUpClickListener()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.ivAlarm.setOnClickListener {
            notificationChannel()
        }
    }

    private fun itemClickListener() {
        // postAdapter의 아이템 클릭 이벤트
        postAdapter.setItemClickListener(object: PostAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item = postAdapter.getItem(position)
                val intent = Intent(baseContext, DetailActivity::class.java)
                intent.putExtra("UserData", item)
                startActivity(intent)
            }
        })
    }

    private fun scrollUpClickListener(){
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true

        binding.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvMain.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    binding.fbUp.startAnimation(fadeOut)
                    binding.fbUp.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop) {
                        binding.fbUp.visibility = View.VISIBLE
                        binding.fbUp.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })

        binding.fbUp.setOnClickListener {
            binding.rvMain.smoothScrollToPosition(0)
        }
    }

    private fun setRecyclerview(){
        postAdapter = PostAdapter(fetchDummyData())
        binding.rvMain.adapter = postAdapter
    }

    private fun fetchDummyData(): List<PostModel>{
        return returnDummyData()
    }

    private fun showBackPressedDialog() {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setIcon(R.drawable.chat)
            .setPositiveButton(getString(R.string.dialog_positive)) { _, _ ->
                finish()
            }
            .setNegativeButton(getString(R.string.dialog_negative)) { dialog, _ ->
                dialog.dismiss()
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
                description = "create Channel"
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
            setContentTitle(getString(R.string.notification_content_title))
            setContentText(getString(R.string.notification_content_text))
        }
        manager.notify(1,builder.build())
    }
}