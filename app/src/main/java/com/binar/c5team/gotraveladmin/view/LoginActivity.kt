package com.binar.c5team.gotraveladmin.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.binar.c5team.gotraveladmin.MainActivity
import com.binar.c5team.gotraveladmin.databinding.FragmentLoginBinding
import com.binar.c5team.gotraveladmin.model.LoginData
import com.binar.c5team.gotraveladmin.model.LoginResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding : FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginProgressBar.visibility = View.GONE
        sharedPref = this.getSharedPreferences("data", Context.MODE_PRIVATE)


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
            Toast.makeText(this, "Username or Password can't be empty !", Toast.LENGTH_SHORT)
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
                            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        }
                    } else {
                        Log.d("login response", response.body().toString())
                        binding.loginProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            "Wrong Username or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("Login Data Error", call.toString())
                    binding.loginProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@LoginActivity,
                        "Something Went Wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        binding.loginProgressBar.visibility = View.GONE
    }
}