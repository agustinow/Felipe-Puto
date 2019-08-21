package com.odella.bacchus.Controller.ui.content

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.odella.bacchus.Model.WineCartFeed
import com.odella.bacchus.Model.WineFeed

class TabsViewModel : ViewModel() {
    lateinit var token: String
    var linearView: Boolean = true
    val wines: MutableLiveData<List<WineFeed>> = MutableLiveData()
    val cart: MutableMap<Int, WineCartFeed> = mutableMapOf()
}