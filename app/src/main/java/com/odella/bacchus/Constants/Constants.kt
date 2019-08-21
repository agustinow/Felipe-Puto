package com.odella.bacchus.Constants

const val BASE_URL = "https://localhost:5001/api/"
const val WINE_ID = "id"


open class TokenClass(){
    object TokenSingelton {
        var actualToken:String
        init {

            actualToken=""
        }
    }
}


