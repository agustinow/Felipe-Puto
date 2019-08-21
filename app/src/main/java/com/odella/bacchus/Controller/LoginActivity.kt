package com.odella.bacchus.Controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.odella.bacchus.Constants.TokenClass
import com.odella.bacchus.R
import com.odella.bacchus.Service.NetworkService
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    lateinit var model: LoginViewModel

    fun login(){
        val intent = Intent(this@LoginActivity, ClientMainActivity::class.java)
        startActivity(intent)
    }

    fun tryToConnect(username: String, password: String){
        val request = JSONObject()
        request.put("username", username)
        request.put("password", password)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), request.toString())
        val call = NetworkService.create().login(requestBody)
        call.enqueue(object: Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                makeText(this@LoginActivity, "Failed to connect", LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.code() == 200){
                    TokenClass.TokenSingelton.actualToken = "Bearer " + response.body().toString()
                    login()
                } else {
                    makeText(this@LoginActivity, "Invalid user", LENGTH_SHORT).show()
                    txtUsername.setText("")
                    txtPassword.setText("")
                }
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        model = ViewModelProviders.of(this@LoginActivity)[LoginViewModel::class.java]
        switchPassword.isChecked = model.passwordVisible
        val textListener = object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                btnLogin.isEnabled = !p0.isNullOrEmpty()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }
        txtUsername.addTextChangedListener(textListener)
        txtPassword.addTextChangedListener(textListener)

        switchPassword.setOnClickListener {
            if(model.passwordVisible){
                model.passwordVisible = false
                var start = txtPassword.selectionStart
                var end = txtPassword.selectionEnd
                txtPassword.transformationMethod = PasswordTransformationMethod()
                txtPassword.setSelection(start, end)
            } else {
                model.passwordVisible = true
                var start = txtPassword.selectionStart
                var end = txtPassword.selectionEnd
                txtPassword.transformationMethod = null
                txtPassword.setSelection(start, end)
            }
        }
        btnLogin.setOnClickListener {
            tryToConnect(txtUsername.text.toString(), txtPassword.text.toString())
        }
    }
}
