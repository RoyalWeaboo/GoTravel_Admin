package com.binar.c5team.gotraveladmin.view.edit

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentAddAirportBinding
import com.binar.c5team.gotraveladmin.viewmodel.AirportViewModel


class EditAirportFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentAddAirportBinding
    lateinit var sharedPrefAirport: SharedPreferences


    private val statusList = arrayOf("On", "Off")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAirportBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        sharedPrefAirport =
            requireActivity().getSharedPreferences("dataairport", Context.MODE_PRIVATE)

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
                    binding.tvStatus.text = sharedPrefAirport.getString("status", "")
                }
            }

        binding.edtCity.setText(sharedPrefAirport.getString("city","").toString())
        binding.edtCode.setText(sharedPrefAirport.getString("code","").toString())
        binding.edtCountry.setText(sharedPrefAirport.getString("country","").toString())
        binding.edtAirportName.setText(sharedPrefAirport.getString("name","").toString())
        binding.edtProvince.setText(sharedPrefAirport.getString("province","").toString())
        binding.btnAdd.text = "Edit"
        binding.btnAdd.setOnClickListener {
            val token = sharedPref.getString("token","").toString()
            val id = sharedPrefAirport.getInt("id",0)
            val city = binding.edtCity.text.toString()
            val code = binding.edtCode.text.toString()
            val country = binding.edtCountry.text.toString()
            val name = binding.edtAirportName.text.toString()
            val province = binding.edtProvince.text.toString()
            val status = binding.tvStatus.text.toString()
            editAirport(token, id, city, code, country, name, province, status)
            findNavController().navigate(R.id.action_editAirportFragment_to_nav_airport)
        }
    }

    private fun editAirport(
        token: String,
        id: Int,
        city: String,
        code: String,
        country: String,
        name: String,
        province: String,
        status: String
    ) {
        val viewModel = ViewModelProvider(this)[AirportViewModel::class.java]
        viewModel.putAirportData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Edit Airport Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Edit Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.callPutAirportData(token, id, city, code, country, name, province, status)

    }
}