package com.pcodelight.tiket.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User: Serializable {
    @SerializedName("login")
    var name: String = ""

    @SerializedName("avatar_url")
    var photoUrl: String = ""
}