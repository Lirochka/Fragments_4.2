package com.example.fragments_42.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.fragments_42.R
import com.example.fragments_42.model.User
import com.example.fragments_42.ui.edit.EditFragment
import com.example.fragments_42.ui.edit.EditFragmentButtonClickListener
import com.example.fragments_42.ui.main.MainFragmentButtonClickListener

class MainActivity : AppCompatActivity(),
    MainFragmentButtonClickListener,
    EditFragmentButtonClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun launchCFragmentWithNewText(user: User) {
        val fragment = EditFragment()
        val bundle = Bundle()
        bundle.putParcelable("key", user)
        fragment.arguments = bundle

        supportFragmentManager.commit {
            replace(R.id.mainContainer, fragment)
            addToBackStack(EditFragment.NAME_EDIT)
        }
    }

    override fun returnMainFragment() {
        supportFragmentManager.popBackStack()
    }
}