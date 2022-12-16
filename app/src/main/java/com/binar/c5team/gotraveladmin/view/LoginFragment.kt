package com.binar.c5team.gotraveladmin.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentLoginBinding
import com.binar.c5team.gotraveladmin.model.LoginData
import com.binar.c5team.gotraveladmin.model.LoginResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginProgressBar.visibility = View.GONE
        sharedPref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)


        binding.btnLogin.setOnClickListener {
            validateLoginInput()
        }
        binding.tvRegister.setOnClickListener {
//            Navigation.findNavController(view)
//                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateLoginInput() {
        val usernameInput = binding.inputUsername.editText?.text.toString()
        val passwordinput = binding.inputPassword.editText?.text.toString()

        if (usernameInput.isNotEmpty() && passwordinput.isNotEmpty()) {
            binding.loginProgressBar.visibility = View.VISIBLE
            validateLoginData(usernameInput, passwordinput)
        } else {
            Toast.makeText(context, "Username or Password can't be empty !", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validateLoginData(username: String, password: String) {
        RetrofitClient.apiInstance.getLoginData(LoginData(username, password))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Login Success") {
                            Log.d("login response", response.body().toString())
                            val saveData = sharedPref.edit()
                            saveData.putString("session", "true")
                            saveData.putInt("userId", response.body()!!.id)
                            saveData.putString("username", response.body()?.username)
                            saveData.putString("token", response.body()?.token)
                            saveData.apply()
                            binding.loginProgressBar.visibility = View.GONE
//                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    } else {
                        Log.d("login response", response.body().toString())
                        binding.loginProgressBar.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Wrong Username or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("Login Data Error", call.toString())
                    binding.loginProgressBar.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Something Went Wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        binding.loginProgressBar.visibility = View.GONE
    }
}