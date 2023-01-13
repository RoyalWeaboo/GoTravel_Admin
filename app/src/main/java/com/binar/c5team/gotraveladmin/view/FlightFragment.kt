package com.binar.c5team.gotraveladmin.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentFlightBinding
import com.binar.c5team.gotraveladmin.view.adapter.FlightAdapter
import com.binar.c5team.gotraveladmin.viewmodel.FlightViewModel

class FlightFragment : Fragment() {

    private lateinit var binding: FragmentFlightBinding
    private lateinit var adapter: FlightAdapter
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefLogin: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlightBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("dataflight", Context.MODE_PRIVATE)
        sharedPrefLogin = requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        showDataFlight()

        binding.btnAddFlight.setOnClickListener {
            findNavController().navigate(R.id.action_nav_flight_to_addFlightFragment)
        }
    }

    private fun showDataFlight() {
        val viewModel = ViewModelProvider(this)[FlightViewModel::class.java]
        viewModel.callFlightData {
            adapter = FlightAdapter(it)
            binding.rvListPlane.adapter = adapter
            adapter.onCardClick = {
                val flightInfo = sharedPref.edit()
                flightInfo.putString("planename",it.plane.name)
                flightInfo.putInt("idplane",it.plane.id)
                flightInfo.putString("class",it.kelas)
                flightInfo.putInt("seat",it.availableSeats)
                flightInfo.putInt("price",it.price)
                flightInfo.putString("arrival",it.arrivalTime)
                flightInfo.putString("departure",it.departureTime)
                flightInfo.putString("date",it.flightDate)
                flightInfo.apply()

                findNavController().navigate(R.id.action_nav_flight_to_detailFlightFragment)
            }

            adapter.onEditClick = {
                val flightInfo = sharedPref.edit()
                flightInfo.putInt("id",it.id)
                flightInfo.putString("arrival_time",it.arrivalTime)
                flightInfo.putInt("available_seats",it.availableSeats)
                flightInfo.putString("departure_time",it.departureTime)
                flightInfo.putString("flight_date",it.flightDate)
                flightInfo.putInt("from_airport_id",it.fromAirportId)
                flightInfo.putInt("id_plane",it.idPlane)
                flightInfo.putString("kelas",it.kelas)
                flightInfo.putInt("price",it.price)
                flightInfo.putInt("to_airport_id",it.toAirportId)
                flightInfo.apply()

                findNavController().navigate(R.id.action_nav_flight_to_editFlightFragment)

            }

            adapter.onDeleteClick = {
                val token = sharedPrefLogin.getString("token","").toString()
                viewModel.deleteFlightData().observe(viewLifecycleOwner) {
                    if (it != null) {
                        Toast.makeText(
                            requireActivity(),
                            "Deleted Plane Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.callFlightData {
                            adapter = FlightAdapter(it)
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
                viewModel.callDeleteFlightData(token,it)
            }
        }
        binding.rvListPlane.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListPlane.setHasFixedSize(true)
    }

    private fun refreshCurrentFragment(){
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!,true)
        findNavController().navigate(id)
    }
}