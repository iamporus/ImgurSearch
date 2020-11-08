package com.purush.imgursearch.utils

object Constants {

    //Configurations
    const val DEBOUNCE_TIMEOUT = 250L

    //Debugging
    const val INTERCEPT_RESPONSE = false

    //Networking
    const val BASE_URL = "https://api.imgur.com/"
    const val CLIENT_ID = "137cda6b5008a7c"     //TODO: THREAT -- consume this from build configs
    const val CLIENT_ID_IDENTIFIER = "Client-ID"
    const val AUTH_IDENTIFIER = "Authorization"
}