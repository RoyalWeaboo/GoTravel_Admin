package com.binar.c5team.gotraveladmin.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentAirportBinding
import com.binar.c5team.gotraveladmin.model.AirportList
import com.binar.c5team.gotraveladmin.view.adapter.AirportAdapter
import com.binar.c5team.gotraveladmin.viewmodel.AirportViewModel

class AirportFragment : Fragment() {

    private lateinit var binding: FragmentAirportBinding
    private lateinit var adapter: AirportAdapter
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefLogin: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAirportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("dataairport", Context.MODE_PRIVATE)
        sharedPrefLogin = requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        showairport()
    }

    private fun showairport() {
        val viewModel = ViewModelProvider(this)[AirportViewModel::class.java]
        val token = sharedPrefLogin.getString("token", "").toString()

        binding.btnAddAirport.setOnClickListener {
            findNavController().navigate(R.id.action_nav_airport_to_addAirportFragment)
        }

        viewModel.getAirportData().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvListAirport.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
                binding.rvListAirport.setHasFixedSize(true)
                val filterAirport: MutableList<AirportList> = ArrayList()
                for (i in it.data.airports) {
                    filterAirport.add(i)
                }
                Log.d("filterAirport", "showDataAdmin: $filterAirport")
                adapter = AirportAdapter(filterAirport)
                binding.rvListAirport.adapter = adapter

                adapter.onCardClick = {
                    val airportInfo = sharedPref.edit()
                    airportInfo.putString("status", it.status)
                    airportInfo.putString("city", it.city)
                    airportInfo.putString("province", it.province)
                    airportInfo.putString("country", it.country)
                    airportInfo.apply()

                    findNavController().navigate(R.id.action_nav_airport_to_detailAirportFragment)
                }

                adapter.onEditClick = {
                    val airportInfo = sharedPref.edit()
                    airportInfo.putString("status", it.status)
                    airportInfo.putString("city", it.city)
                    airportInfo.putString("province", it.province)
                    airportInfo.putString("code", it.code)
                    airportInfo.putString("name", it.name)
                    airportInfo.putString("country", it.country)
                    airportInfo.putInt("id", it.id)
                    airportInfo.apply()

                    findNavController().navigate(R.id.action_nav_airport_to_editAirportFragment)
                }

                adapter.onDeleteClick = {
                    viewModel.deleteAirportData().observe(viewLifecycleOwner) {
                        if (it != null) {
                            Toast.makeText(
                                requireActivity(),
                                "Deleted Airport Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            refreshCurrentFragment()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "Delete Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    viewModel.callDeleteAirportData(token, it)
                }
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.callAirportData()
    }


    private fun refreshCurrentFragment() {
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!, true)
        findNavController().navigate(id)
    }
}