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
import com.binar.c5team.gotraveladmin.databinding.FragmentAdminBinding
import com.binar.c5team.gotraveladmin.model.admin.User
import com.binar.c5team.gotraveladmin.view.adapter.AdminAdapter
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private lateinit var adapter: AdminAdapter
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        showDataAdmin()
    }

    private fun showDataAdmin() {
        val viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        val token = sharedPref.getString("token", "").toString()
        binding.btnAddAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_nav_admin_to_detailAdminFragment)
        }

        viewModel.getAdminData().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvListAdmin.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
                binding.rvListAdmin.setHasFixedSize(true)
                val filterAdmin: MutableList<User> = ArrayList()
                for (i in it.data.users) {
                    filterAdmin.add(i)
                }
                Log.d("filterAdmin", "showDataAdmin: $filterAdmin")
                adapter = AdminAdapter(filterAdmin)
                binding.rvListAdmin.adapter = adapter

                adapter.onDeleteClick = {
                    viewModel.deleteAdminData().observe(viewLifecycleOwner) {
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
                    viewModel.callDeleteAdminData(token,it)
                }
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "No Data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.callAdminData(token)
    }

    private fun refreshCurrentFragment(){
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!,true)
        findNavController().navigate(id)
    }

}