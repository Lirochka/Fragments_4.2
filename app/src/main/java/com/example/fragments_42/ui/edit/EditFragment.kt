package com.example.fragments_42.ui.edit

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.bumptech.glide.Glide
import com.example.fragments_42.R
import com.example.fragments_42.databinding.FragmentEditBinding
import com.example.fragments_42.model.User


class EditFragment : Fragment() {

    private var user: User? = null

    private var _binding: FragmentEditBinding? = null
    private val binding: FragmentEditBinding
        get() = _binding ?: throw RuntimeException("FragmentEditBinding = null")

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        parseArgs()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        binding.btnSave.setOnClickListener {
            val newUserId = binding.idEdit.text.toString()
            val newName = binding.nameEdit.text.toString()
            val newSurname = binding.surnameEdit.text.toString()
            val newPhone = binding.phoneEdit.text.toString()
            val editedUser = User(
                id = newUserId.toLong(),
                image = user?.image ?: "",
                name = newName,
                surname = newSurname,
                phone = newPhone,
            )
            setFragmentResult(
                "request_key",
                bundleOf("extra_key" to editedUser)
            )

            (requireActivity() as EditFragmentButtonClickListener)
                .returnMainFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun parseArgs() {
        user = arguments?.getParcelable("key") ?: User(0, "", "", "", "")
    }

    private fun bindViews() {
        if (user?.image!!.isNotBlank()) {
            Glide.with(binding.imageEdit.context)
                .load(user?.image)
                .circleCrop()
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(binding.imageEdit)
        } else {
            binding.imageEdit.setImageResource(R.drawable.ic_user)
        }
        binding.idEdit.setText(user?.id.toString())
        binding.nameEdit.setText(user?.name)
        binding.surnameEdit.setText(user?.surname)
        binding.phoneEdit.setText(user?.phone)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NAME_EDIT = "Edit_Fragment"
    }
}