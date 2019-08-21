package com.odella.bacchus.Controller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.odella.bacchus.Constants.TokenClass
import com.odella.bacchus.Controller.ui.content.CartFragment
import com.odella.bacchus.Controller.ui.content.SectionsPagerAdapter
import com.odella.bacchus.Controller.ui.content.StoreFragment
import com.odella.bacchus.Controller.ui.content.TabsViewModel
import com.odella.bacchus.Model.WineFeed
import com.odella.bacchus.R
import com.odella.bacchus.Service.NetworkService
import kotlinx.android.synthetic.main.activity_client_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientMainActivity : AppCompatActivity() {

    lateinit var model: TabsViewModel
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    fun fetchWines(){
        val call = NetworkService.create().getWines(TokenClass.TokenSingelton.actualToken)
        call.enqueue(object: Callback<List<WineFeed>>{
            override fun onFailure(call: Call<List<WineFeed>>, t: Throwable) {
                Toast.makeText(this@ClientMainActivity, "Error fetching data", Toast.LENGTH_SHORT)
                finish()
            }

            override fun onResponse(call: Call<List<WineFeed>>, response: Response<List<WineFeed>>) {
                if(response.isSuccessful){
                    Toast.makeText(this@ClientMainActivity, "Fetch successful", Toast.LENGTH_SHORT)
                    model.wines!!.value = response.body()
                    sectionsPagerAdapter = SectionsPagerAdapter(this@ClientMainActivity, supportFragmentManager)
                    view_pager.adapter = sectionsPagerAdapter
                    tabs.setupWithViewPager(view_pager)
                    tabs.getTabAt(0)!!.icon = resources.getDrawable(R.drawable.icon_cart_nobg, resources.newTheme())
                } else {
                    finish()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)
        model = ViewModelProviders.of(this@ClientMainActivity)[TabsViewModel::class.java]
        val prefs = getSharedPreferences("BacchusPrefs", Context.MODE_PRIVATE)
        model.token = prefs.getString("Token", null).toString()
        fetchWines()

    }
}