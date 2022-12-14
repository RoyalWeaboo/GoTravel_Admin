package com.binar.c5team.gotraveladmin.view.add

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.c5team.gotraveladmin.MainActivity
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentDetailAdminBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentDetailAirportBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentEditAdminBinding
import com.binar.c5team.gotraveladmin.model.LoginData
import com.binar.c5team.gotraveladmin.model.LoginResponse
import com.binar.c5team.gotraveladmin.model.createadmin.CreateAdminResponse
import com.binar.c5team.gotraveladmin.model.data.CreateAdminData
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import com.binar.c5team.gotraveladmin.viewmodel.AdminViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class DetailAdminFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences

    private lateinit var binding: FragmentEditAdminBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAdminBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        binding.edtDate.setOnClickListener {
            openDatePicker()
        }

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

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, y, m, d ->
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = Calendar.getInstance()
                date.set(y, m, d)
                val dateString = formatter.format(date.time)
                binding.edtDate.setText(dateString)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()
    }
}