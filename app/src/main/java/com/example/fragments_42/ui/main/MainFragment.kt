package com.example.fragments_42.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.fragments_42.App
import com.example.fragments_42.databinding.FragmentMainBinding
import com.example.fragments_42.model.User
import com.example.fragments_42.model.UsersListener
import com.example.fragments_42.model.UsersService
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding = null")

    private val adapter = ListDelegationAdapter(
        MainScreenDelegates.itemUserDelegate
    )

    private val usersService: UsersService
        get() = (requireContext().applicationContext as App).usersService

    private val userActionListener = object : UserActionListener {
        override fun onUserDetail(user: User) {
            (requireActivity() as MainFragmentButtonClickListener)
                .launchCFragmentWithNewText(user)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        usersService.addListener(usersListener)
        initFragmentResultListener()
        MainScreenDelegates.setUserActionListener(userActionListener)
    }

    private fun initAdapter() {
        with(binding) {
            recyclerView.adapter = adapter
        }
    }

    private val usersListener: UsersListener = {
        adapter.items = it
    }

    private fun initFragmentResultListener() {
        setFragmentResultListener("request_key") { key, bundle ->
            val selectedSort = bundle.getParcelable<User>("extra_key")

            selectedSort?.let { editedUser ->
                adapter.items = adapter.items?.map { existingUser ->
                    if (existingUser.id == editedUser.id) {
                        editedUser
                    } else {
                        existingUser
                    }
                }?.toMutableList()
                adapter.notifyDataSetChanged()
            }
            if (selectedSort != null) {
                (requireContext().applicationContext as App)
                    .usersService.updateUser(selectedSort)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        usersService.removeListener(usersListener)
    }
}

