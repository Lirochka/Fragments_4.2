package com.example.fragments_42.ui.main

import com.example.fragments_42.model.User

internal interface MainFragmentButtonClickListener {
    fun launchCFragmentWithNewText(user: User)
}