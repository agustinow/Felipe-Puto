package com.odella.bacchus.Controller

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.odella.bacchus.Constants.TokenClass
import com.odella.bacchus.Constants.WINE_ID
import com.odella.bacchus.Model.WineFeed
import com.odella.bacchus.R
import com.odella.bacchus.Service.NetworkService
import kotlinx.android.synthetic.main.dialog_wine.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogWineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_wine)
        val id = intent.getIntExtra(WINE_ID, 1)
        val call = NetworkService.create().getWine(id, TokenClass.TokenSingelton.actualToken)
        call.enqueue(object : Callback<WineFeed> {

            override fun onFailure(call: Call<WineFeed>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<WineFeed>, response: Response<WineFeed>) {
                if (response.isSuccessful) {
                    val vino = response.body()
                    if(vino != null){
                        txtDialogWineName.text = vino?.name!!.toString()
                        txtDesciption.text=vino?.description!!.toString()
                        txtFlavors.text=vino?.flavors!!.toString()
                        txtCountry.text=vino?.country!!.toString()
                        Glide.with(this@DialogWineActivity).load(vino.image).into(imgWine)
                        val score = vino?.score!!.toFloat()
                        dialogRatingBar.rating = score/2f
                        val wineMonth=vino?.age!!.toString().first()
                        val wineYear=vino?.age!!.toString().last()
                        txtAge.text="Months: $wineMonth Years: $wineYear"
                        txtPrice.text = "${vino?.price!!}"
                    } else {
                        finish()
                    }
                }
            }

        })
    }
}


