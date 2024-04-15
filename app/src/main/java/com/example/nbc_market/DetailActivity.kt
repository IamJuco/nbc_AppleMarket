package com.example.nbc_market

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.nbc_market.databinding.ActivityDetailBinding
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val postModel by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra("UserData", PostModel::class.java)
        } else {
            intent?.getParcelableExtra("UserData")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showDetailActivity()

        binding.tvMannersInfo.setOnClickListener {
            showMannersInfoDialog()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun showDetailActivity() {
        postModel?.let {// 그냥 여기서 if문으로 null인지 아닌지 하는게 좋음
            // 화폐 , 처리
            val unitPrice = String.format(
                Locale.getDefault(),
                binding.root.context.getString(R.string.tv_postMoney),
                NumberFormat.getNumberInstance(Locale.getDefault()).format(it.postPrice)
            )

            binding.ivDetailImage.setImageURI(it.postThumnail)
            //binding.ivProfile.setImageURI() 프로필 이미지 더미데이터에 추가해야함
            binding.tvDetailUserName.text = it.userName
            binding.tvDetailLocation.text = it.postLocation
            //binding.tvMannersTemperature= 매너온도 더미데이터에 추가해야함
            binding.tvDetailPostTitle.text = it.postTitle
            binding.tvDetailPostContents.text = it.postContents
            binding.tvDetailPrice.text = unitPrice
        }
    }

    private fun showMannersInfoDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.manners_info_title))

        val v1 = layoutInflater.inflate(R.layout.manners_info_dialog, null)
        builder.setView(v1)

        builder.setPositiveButton(getString(R.string.manners_info_positive)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

}