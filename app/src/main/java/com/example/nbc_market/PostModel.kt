package com.example.nbc_market

import android.net.Uri

data class PostModel (
    val postThumnail: Uri,
    val postTitle: String,
    val postLocation: String,
    val postPrice: Int,
    val postComment: Int,
    val postFavorite: Int
)
