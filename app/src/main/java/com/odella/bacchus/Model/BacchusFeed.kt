package com.odella.bacchus.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WineFeed {

    @SerializedName("Id")
    @Expose
    var id: String? = null
    @SerializedName("Wine_id")
    @Expose
    var wineId: Int? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("Description")
    @Expose
    var description: String? = null
    @SerializedName("Flavors")
    @Expose
    var flavors: String? = null
    @SerializedName("Color")
    @Expose
    var color: String? = null
    @SerializedName("Country")
    @Expose
    var country: String? = null
    @SerializedName("Age")
    @Expose
    var age: String? = null
    @SerializedName("Score")
    @Expose
    var score: Int? = null
    @SerializedName("Price")
    @Expose
    var price: Int? = null
    @SerializedName("Image")
    @Expose
    var image: String? = null

}

data class WineCartFeed(var wine: WineFeed, var amount: Int)

class UserFeed {

    @SerializedName("Id")
    @Expose
    var id: String? = null
    @SerializedName("Username")
    @Expose
    var username: String? = null
    @SerializedName("Password")
    @Expose
    var password: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
    @SerializedName("Photo")
    @Expose
    var photo: String? = null

}