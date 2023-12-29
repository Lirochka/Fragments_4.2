package com.example.fragments_42.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
  var id: Long,
    val image: String,
    var name: String,
    var surname: String,
    var phone: String
):Parcelable
