package kr.dnx.ble.android.mysunflower

import io.reactivex.Single
import retrofit2.http.GET

interface MySunflowerApi {
    @GET("/posts")
    fun requestPosts() : Single<Array<Post>>
}