package com.odella.bacchus.Controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.odella.bacchus.Model.WineFeed
import com.odella.bacchus.Service.NetworkService
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var passwordVisible = false
}