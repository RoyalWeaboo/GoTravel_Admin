package com.binar.c5team.gotraveladmin.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentAdminBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentPlaneBinding
import com.binar.c5team.gotraveladmin.view.adapter.AdminAdapter
import com.binar.c5team.gotraveladmin.view.adapter.FlightAdapter
import com.binar.c5team.gotraveladmin.view.adapter.PlaneAdapter
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel
import com.binar.c5team.gotraveladmin.viewmodel.PlaneViewModel


class PlaneFragment : Fragment() {
    private lateinit var binding: FragmentPlaneBinding
    lateinit var sharedPref: SharedPreferences
    lateinit var sharedPrefPlane: SharedPreferences
    private lateinit var adapter: PlaneAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        sharedPrefPlane = requireActivity().getSharedPreferences("dataplane", Context.MODE_PRIVATE)
        showPlaneData()

        binding.btnAddPlane.setOnClickListener {
            findNavController().navigate(R.id.action_nav_plane_to_addPlaneFragment)
        }
    }

    fun showPlaneData() {
        val viewModel = ViewModelProvider(this)[PlaneViewModel::class.java]
        val token = sharedPref.getString("token", "").toString()
        viewModel.callPlaneData {
            adapter = PlaneAdapter(it)
            binding.rvListPlane.adapter = adapter
            adapter.onEditClick = {
                val dataPlane = sharedPrefPlane.edit()
                dataPlane.putInt("id",it.id)
                dataPlane.putInt("code",it.code)
                dataPlane.putString("name",it.name)
                dataPlane.putString("status",it.status)
                dataPlane.apply()

                findNavController().navigate(R.id.action_nav_plane_to_editPlaneFragment)
            }
            adapter.onDeleteClick = {
                viewModel.deletePlaneData().observe(viewLifecycleOwner) {
                    if (it != null) {
                        Toast.makeText(
                            requireActivity(),
                            "Deleted Plane Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.callPlaneData {
                            adapter = PlaneAdapter(it)
                            binding.rvListPlane.adapter = adapter
                            binding.rvListPlane.layoutManager = LinearLayoutManager(
                                context, LinearLayoutManager.VERTICAL, false
                            )
                            binding.rvListPlane.setHasFixedSize(true)
                            refreshCurrentFragment()
                        }
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Delete Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                viewModel.callDeletePlaneData(token,it)
            }
            adapter.notifyDataSetChanged()
        }
        binding.rvListPlane.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        binding.rvListPlane.setHasFixedSize(true)
    }

    private fun refreshCurrentFragment(){
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!,true)
        findNavController().navigate(id)
    }
}