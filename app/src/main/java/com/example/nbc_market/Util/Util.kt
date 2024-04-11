package com.example.nbc_market.Util

import android.net.Uri
import com.example.nbc_market.PostModel


val dummyItems = mutableListOf<PostModel>()

fun dummyData() {
    
    dummyItems.add(
        PostModel(
            Uri.parse("android.resource://com.example.nbc_market/drawable/sample1"),
            "test_test",
            "test",
            1,
            1,
            1
        )
    )

    dummyItems.add(
        PostModel(
            Uri.parse("android.resource://com.example.nbc_market/drawable/sample2"),
            "test_test",
            "test",
            1,
            1,
            1
        )
    )

    dummyItems.add(
        PostModel(
            Uri.parse("android.resource://com.example.nbc_market/drawable/sample3"),
            "test_test",
            "test",
            1,
            1,
            1
        )
    )

    dummyItems.add(
        PostModel(
            Uri.parse("android.resource://com.example.nbc_market/drawable/sample4"),
            "test_test",
            "test",
            1,
            1,
            1
        )
    )

    dummyItems.add(
        PostModel(
            Uri.parse("android.resource://com.example.nbc_market/drawable/sample5"),
            "test_test",
            "test",
            1,
            1,
            1
        )
    )
}