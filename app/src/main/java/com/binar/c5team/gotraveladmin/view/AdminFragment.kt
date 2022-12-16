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
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentAdminBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentLoginBinding
import com.binar.c5team.gotraveladmin.model.UserAdminResponse
import com.binar.c5team.gotraveladmin.view.adapter.AdminAdapter
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private lateinit var adapter: AdminAdapter
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        showDataAdmin()
    }

    private fun showDataAdmin() {
        val viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        val token = sharedPref.getString("token", "").toString()
        viewModel.callAdminData(token, {
            binding.rvListAdmin.adapter = AdminAdapter(it)
        })
        binding.rvListAdmin.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        binding.rvListAdmin.setHasFixedSize(true)

//        viewModel.getAdminData().observe(viewLifecycleOwner) {
//            if (it != null) {
//                binding.rvListAdmin.layoutManager = LinearLayoutManager(
//                    context, LinearLayoutManager.VERTICAL, false
//                )
//                binding.rvListAdmin.setHasFixedSize(true)
//                val filterAdmin: MutableList<UserAdminResponse> = ArrayList()
//                for (i in it.data.user) {
//                    filterAdmin.add(i)
//                }
//                adapter = AdminAdapter(filterAdmin)
//            } else {
//                Toast.makeText(
//                    requireActivity(),
//                    "No Data",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }

}