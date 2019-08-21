package com.odella.bacchus.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.odella.bacchus.Model.UserFeed
import com.odella.bacchus.R
import com.odella.bacchus.Service.NetworkService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    fun doSomething(connected: Boolean){
        GlobalScope.launch{
            delay(500L)
            if(connected){
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = NetworkService.create().getUsers("")
        val connecting = Snackbar.make(loadingLayout, "Connecting", Snackbar.LENGTH_INDEFINITE)
        connecting.show()
        call.enqueue(object: Callback<List<UserFeed>>{
            override fun onFailure(call: Call<List<UserFeed>>, t: Throwable) {
                connecting.dismiss()
                connecting.setText("Failed to connect").show()
                doSomething(false)
            }

            override fun onResponse(call: Call<List<UserFeed>>, response: Response<List<UserFeed>>) {
                connecting.dismiss()
                connecting.setText("Connected").show()
                doSomething(true)
            }
        })

    }
}
