package com.bulbstudios.justeat.dataclasses

import com.google.gson.annotations.SerializedName

/**
 * Created by Terence Baker on 12/05/2016.
 */
class Restaurant {

    @SerializedName("Name")
    var name = ""

    @SerializedName("Address")
    var address = ""
}