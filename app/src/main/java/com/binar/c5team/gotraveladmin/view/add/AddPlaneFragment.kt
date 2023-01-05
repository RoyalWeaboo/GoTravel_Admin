package com.binar.c5team.gotraveladmin.view.add

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentAddPlaneBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentDetailAdminBinding
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel
import com.binar.c5team.gotraveladmin.viewmodel.PlaneViewModel


class AddPlaneFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentAddPlaneBinding

    private val status_list = arrayOf("On", "Off")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        binding.chooseStatus.adapter = ArrayAdapter<String>(
            this.requireActivity(),
            android.R.layout.simple_list_item_1,
            status_list
        )
        binding.chooseStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    binding.tvStatus.text = status_list.get(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.tvStatus.text = ""
                }
            }
        binding.btnAdd.setOnClickListener {
            val viewModel = ViewModelProvider(this)[PlaneViewModel::class.java]

            val planeName = binding.edtPlaneName.text.toString()
            val planeCode = binding.edtCodeAirplane.text.toString()
            var status = binding.tvStatus.text.toString()
            val token = sharedPref.getString("token","").toString()

            createPlane(token,planeCode.toInt(),planeName,status)
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it == false) {
                    findNavController().navigate(R.id.action_addPlaneFragment_to_nav_plane)
                }
            }
        }
    }

    private fun createPlane(token: String ,
                            code: Int,
                            name: String,
                            status: String) {
        val viewModel = ViewModelProvider(this)[PlaneViewModel::class.java]
        viewModel.createPlaneData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Create Plane Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Create Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.postPlaneData(token,code,name,status)

    }
}