package com.example.nbc_market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_market.databinding.RvItemBinding

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
            binding.ivPostThumnail.setImageURI(item.postThumnail)
            binding.tvPostTitle.text = item.postTitle
            binding.tvPostLocation.text = item.postLocation
            binding.tvPostMoney.text = item.postPrice.toString()
            binding.tvPostCommentCount.text = item.postComment.toString()
            binding.tvPostFavoriteCount.text = item.postFavorite.toString()

        }
    }
}