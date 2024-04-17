package com.example.nbc_market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_market.databinding.RvItemBinding
import java.text.NumberFormat
import java.util.Locale

class PostAdapter(private val items: MutableList<PostModel>) : RecyclerView.Adapter<PostAdapter.Holder>() {

    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.Holder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: PostAdapter.Holder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel){

            // 화폐 , 처리
            val unitPrice = String.format(
                Locale.getDefault(),
                binding.root.context.getString(R.string.tv_postMoney),
                NumberFormat.getNumberInstance(Locale.getDefault()).format(item.postPrice)
            )
            binding.apply {
                ivPostThumnail.setImageURI(item.postThumnail)
                tvPostTitle.text = item.postTitle
                tvPostLocation.text = item.postLocation
                tvPostMoney.text = unitPrice
                tvPostCommentCount.text = item.postComment.toString()
                tvPostFavoriteCount.text = item.postFavorite.toString()
            }
        }
    }
}