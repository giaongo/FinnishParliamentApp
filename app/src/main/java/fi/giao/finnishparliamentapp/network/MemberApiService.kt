package fi.giao.finnishparliamentapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fi.giao.finnishparliamentapp.database.ParliamentMember
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This contains network fetch configuration for the app
 */
private const val BASE_URL = "https://users.metropolia.fi/~peterh/"

// Build  Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Build a Retrofit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface MemberApiService {
    @GET("seating.json")
    suspend fun getMembers(): List<ParliamentMember>
}


object MemberApi{
    val retrofitService: MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java)
    }
}