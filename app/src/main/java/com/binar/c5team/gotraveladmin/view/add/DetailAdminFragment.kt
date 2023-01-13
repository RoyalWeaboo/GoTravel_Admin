package com.binar.c5team.gotraveladmin.view.add

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
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentEditAdminBinding
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel


class DetailAdminFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences

    private lateinit var binding: FragmentEditAdminBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAdminBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        binding.textView12.text = "Add Admin"
        binding.btnEdit.text = "Add"
        binding.btnEdit.setOnClickListener {
            val name = binding.edtNamaAdmin.text.toString()
            val username = binding.edtUsernameName.text.toString()
            val gender = binding.edtGender.text.toString()
            val date_of_birth = binding.edtDate.text.toString()
            val no_ktp = binding.edtKtp.text.toString()
            val address = binding.edtAddress.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val role = "admin"
            val token = sharedPref.getString("token","").toString()
            createAdmin(name,username,gender,date_of_birth,no_ktp,address,email,password,role,token)

            findNavController().navigate(R.id.action_detailAdminFragment_to_nav_admin)
        }

    }

    private fun createAdmin(name: String,
                            username: String,
                            gender: String,
                            date_of_birth: String,
                            no_ktp: String,
                            address: String,
                            email: String,
                            password: String,
                            role: String,
                            token: String) {
        val viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        viewModel.createAdminData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Create Admin Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Create Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.callCreateAdmin(name, username, gender, date_of_birth, no_ktp, address, email, password, role, token)

    }
}