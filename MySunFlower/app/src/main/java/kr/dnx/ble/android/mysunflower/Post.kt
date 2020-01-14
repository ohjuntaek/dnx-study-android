package kr.dnx.ble.android.mysunflower

import com.google.gson.annotations.SerializedName

data class Post(
    var userId: Int = 0,
    var id: Int = 0,
    var title : String = "",
    var body : String = ""
)