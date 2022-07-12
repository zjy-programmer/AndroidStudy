package com.example.androidstudy.any.coroutines.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidstudy.R
import com.example.androidstudy.any.coroutines.flowpractice.adapter.UserAdapter
import com.example.androidstudy.any.coroutines.flowpractice.viewmodel.UserViewModel
import com.example.androidstudy.databinding.FragmentUserBinding
import kotlinx.coroutines.flow.collect

class UserFragment : Fragment() {
    private val viewModel by viewModels<UserViewModel>()

    private val binding: FragmentUserBinding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnAddUser.setOnClickListener {
                viewModel.insert(
                    etId.text.toString(),
                    etFirstName.text.toString(),
                    etLastName.text.toString()
                )
            }
        }

        context?.let {
            val adapter = UserAdapter(it)
            binding.rv.adapter = adapter
            binding.rv.layoutManager = LinearLayoutManager(it)
            lifecycleScope.launchWhenCreated {
                viewModel.getAll().collect { value ->
                    adapter.setData(value)
                }
            }
        }
    }

}