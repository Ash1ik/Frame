package com.example.frame.data.local

object TokenStore {

    var accessToken: String? = null
        private set

    fun saveToken(token: String?) {
        accessToken = token
    }

    fun clear() {
        accessToken = null
    }

}
