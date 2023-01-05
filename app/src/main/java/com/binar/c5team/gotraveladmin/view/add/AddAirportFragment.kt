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
import com.binar.c5team.gotraveladmin.databinding.FragmentAddAirportBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentAddPlaneBinding
import com.binar.c5team.gotraveladmin.viewmodel.AirportViewModel
import com.binar.c5team.gotraveladmin.viewmodel.PlaneViewModel


class AddAirportFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentAddAirportBinding

    private val status_list = arrayOf("On", "Off")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAirportBinding.inflate(inflater, container, false)
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
            val viewModel = ViewModelProvider(this)[AirportViewModel::class.java]
            val token = sharedPref.getString("token","").toString()
            val city = binding.edtCity.text.toString()
            val code = binding.edtCode.text.toString()
            val country = binding.edtCountry.text.toString()
            val name = binding.edtAirportName.text.toString()
            val province = binding.edtProvince.text.toString()
            val status = binding.tvStatus.text.toString()

            createAirport(token,city, code ,country,name, province, status)
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it == false) {
                    findNavController().navigate(R.id.action_addAirportFragment_to_nav_airport)
                }
            }
        }
    }

    private fun createAirport(token: String,
                              city: String,
                              code: String,
                              country: String,
                              name: String,
                              province: String,
                              status: String) {
        val viewModel = ViewModelProvider(this)[AirportViewModel::class.java]
        viewModel.createAirportData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Create Airport Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Create Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.postAirportData(token, city, code, country, name, province, status)

    }

}