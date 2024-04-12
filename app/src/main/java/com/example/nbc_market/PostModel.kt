package com.example.nbc_market

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel (
    val userName: String,
    val postThumnail: Uri,
    val postTitle: String,
    val postContents: String,
    val postLocation: String,
    val postPrice: Int,
    val postFavorite: Int,
    val postComment: Int
) : Parcelable
