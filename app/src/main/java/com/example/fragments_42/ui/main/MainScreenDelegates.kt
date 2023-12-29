package com.example.fragments_42.ui.main

import com.bumptech.glide.Glide
import com.example.fragments_42.R
import com.example.fragments_42.databinding.ItemUserBinding
import com.example.fragments_42.model.User
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

interface UserActionListener {
    fun onUserDetail(user: User)
}

object MainScreenDelegates {
    private var userActionListener: UserActionListener? = null

    fun setUserActionListener(listener: UserActionListener) {
        userActionListener = listener
    }

    val itemUserDelegate = adapterDelegateViewBinding<User, User, ItemUserBinding>(
        { inflater, container ->
            ItemUserBinding.inflate(inflater, container, false)
        }
    ) {
        binding.root.setOnClickListener {
            userActionListener?.onUserDetail(item)
        }
        bind {
            with(binding) {
                idUser.text = item.id.toString()
                nameUser.text = item.name
                surnameUser.text = item.surname
                phoneUser.text = item.phone
                if (item.image.isNotBlank()) {
                    Glide.with(imageUser.context)
                        .load(item.image)
                        .circleCrop()
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_user)
                        .into(imageUser)
                } else {
                    imageUser.setImageResource(R.drawable.ic_user)
                }
            }
        }
    }
}
