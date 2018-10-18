package com.jzd.android.jon.app.retrofit

import com.jzd.android.jon.app.common.bean.UserBean
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiService
{
    @POST
    fun login(@Url url: String, @QueryMap params: Map<String, Any>): Observable<ResponseBean<UserBean>>
}