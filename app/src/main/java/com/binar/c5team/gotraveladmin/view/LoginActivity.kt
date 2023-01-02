package com.binar.c5team.gotraveladmin.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.binar.c5team.gotraveladmin.MainActivity
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentLoginBinding
import com.binar.c5team.gotraveladmin.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding : FragmentLoginBinding
    lateinit var viewModel : UserViewModel

    private var progressView: ViewGroup? = null
    private var isProgressShowing = false

    //data
    var uId: Int = 0
    var usernameRes: String = ""
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

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
            login(usernameInput, passwordinput, view)
        } else {
            Toast.makeText(this, "Username or Password can't be empty !", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun login(username: String, password: String,view: View){
        showProgressingView(view)
        viewModel.postLoginApi(this, username, password)

        viewModel.loading.observe(this) {
            if (!it){
                val saveData = sharedPref.edit()
                saveData.putString("session", "true")
                saveData.putInt("userId", uId)
                saveData.putString("username", usernameRes)
                saveData.putString("token", token)
                saveData.apply()
                hideProgressingView(view)

                //check whether the token was added(which mean login is success)
                if (token != "") {
                    Toast.makeText(this, "Login Successful !", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                }else{
                    Toast.makeText(this, "Wrong username or password !", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("InflateParams")
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