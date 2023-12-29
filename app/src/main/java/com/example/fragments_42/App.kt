package com.example.fragments_42

import android.app.Application
import com.example.fragments_42.model.UsersService

class App: Application() {
    val usersService = UsersService()
}