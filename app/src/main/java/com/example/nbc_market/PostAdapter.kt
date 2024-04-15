package com.example.nbc_market

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_market.databinding.RvItemBinding
import java.text.NumberFormat
import java.util.Locale

class PostAdapter(private val items: MutableList<PostModel>) : RecyclerView.Adapter<PostAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.Holder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: PostAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel){

            // 화폐 , 처리
            val unitPrice = String.format(
                Locale.getDefault(),
                binding.root.context.getString(R.string.tv_postMoney),
                NumberFormat.getNumberInstance(Locale.getDefault()).format(item.postPrice)
            )

            binding.ivPostThumnail.setImageURI(item.postThumnail)
            binding.tvPostTitle.text = item.postTitle
            binding.tvPostLocation.text = item.postLocation
            binding.tvPostMoney.text = unitPrice
            binding.tvPostCommentCount.text = item.postComment.toString()
            binding.tvPostFavoriteCount.text = item.postFavorite.toString()

            binding.rvItemArea.setOnClickListener {
                val context = binding.root.context

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("UserData", item)
                context.startActivity(intent)
            }
        }
    }
}