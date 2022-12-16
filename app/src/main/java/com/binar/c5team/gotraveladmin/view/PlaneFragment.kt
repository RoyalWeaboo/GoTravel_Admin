package com.binar.c5team.gotraveladmin.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentAdminBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentPlaneBinding
import com.binar.c5team.gotraveladmin.view.adapter.AdminAdapter
import com.binar.c5team.gotraveladmin.view.adapter.PlaneAdapter
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel
import com.binar.c5team.gotraveladmin.viewmodel.PlaneViewModel


class PlaneFragment : Fragment() {
    private lateinit var binding: FragmentPlaneBinding
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPlaneData()
    }

    fun showPlaneData() {
        val viewModel = ViewModelProvider(this)[PlaneViewModel::class.java]
        viewModel.callPlaneData {
            binding.rvListPlane.adapter = PlaneAdapter(it)
        }
        binding.rvListPlane.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        binding.rvListPlane.setHasFixedSize(true)
    }
}