package com.binar.c5team.gotraveladmin.view.edit

import android.annotation.SuppressLint
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
import com.binar.c5team.gotraveladmin.viewmodel.PlaneViewModel


class EditPlaneFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentAddPlaneBinding
    lateinit var sharedPrefPlane: SharedPreferences

    private val statusList = arrayOf("On", "Off")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPlaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        sharedPrefPlane = requireActivity().getSharedPreferences("dataplane", Context.MODE_PRIVATE)

        binding.edtPlaneName.setText(sharedPrefPlane.getString("name",""))
        binding.edtCodeAirplane.setText(sharedPrefPlane.getInt("code",0).toString())

        binding.chooseStatus.adapter = ArrayAdapter(
            this.requireActivity(),
            android.R.layout.simple_list_item_1,
            statusList
        )
        binding.chooseStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    binding.tvStatus.text = statusList[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.tvStatus.text = sharedPrefPlane.getString("status","")
                }
            }
        binding.btnAdd.text = "Edit"
        binding.btnAdd.setOnClickListener {

            val planeName = binding.edtPlaneName.text.toString()
            val planeCode = binding.edtCodeAirplane.text.toString()
            val status = binding.tvStatus.text.toString()
            val token = sharedPref.getString("token", "").toString()
            val id = sharedPrefPlane.getInt("id",0)
            editPlane(id,token,planeCode.toInt(),planeName,status)
            findNavController().navigate(R.id.action_editPlaneFragment_to_nav_plane)
        }
    }

    private fun editPlane(
        id: Int,
        token: String,
        code: Int,
        name: String,
        status: String
    ) {
        val viewModel = ViewModelProvider(this)[PlaneViewModel::class.java]
        viewModel.putPlaneData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Edit Plane Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Edit Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.callPutPlaneData(token, id, code, name, status)

    }
}