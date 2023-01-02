package com.binar.c5team.gotraveladmin.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.binar.c5team.gotraveladmin.MainActivity
import com.binar.c5team.gotraveladmin.R
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

    var progressView: ViewGroup? = null
    private var isProgressShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginProgressBar.visibility = View.GONE
        sharedPref = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        if (sharedPref.getString("token","") != "") {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            validateLoginInput(it)
        }

    }

    private fun validateLoginInput(view: View) {
        val usernameInput = binding.inputUsername.editText?.text.toString()
        val passwordinput = binding.inputPassword.editText?.text.toString()

        if (usernameInput.isNotEmpty() && passwordinput.isNotEmpty()) {
            binding.loginProgressBar.visibility = View.VISIBLE
            validateLoginData(usernameInput, passwordinput,view)
        } else {
            Toast.makeText(this, "Username or Password can't be empty !", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validateLoginData(username: String, password: String,view: View) {
        showProgressingView(view)

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
                        hideProgressingView(view)
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
                    hideProgressingView(view)
                }

            })
        binding.loginProgressBar.visibility = View.GONE
    }

    private fun showProgressingView(view : View) {
        if (!isProgressShowing) {
            isProgressShowing = true
            progressView = layoutInflater.inflate(R.layout.progress_bar, null) as ViewGroup
            val v: View = view.rootView
            val viewGroup = v as ViewGroup
            viewGroup.addView(progressView)
        }
    }

    private fun hideProgressingView(view : View) {
        val v: View = view.rootView
        val viewGroup = v as ViewGroup
        viewGroup.removeView(progressView)
        isProgressShowing = false
    }
}