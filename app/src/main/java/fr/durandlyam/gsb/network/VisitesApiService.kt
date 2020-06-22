package fr.durandlyam.gsb.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.durandlyam.gsb.Credentials
import fr.durandlyam.gsb.Practiciens
import fr.durandlyam.gsb.User
import fr.durandlyam.gsb.database.Visite
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://lyam-durand.fr/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .baseUrl(BASE_URL)
    .build()

interface VisitesApiService {
    @GET("visite")
    fun getVisites(@Query("api_token") token : String): Call<List<Visite>>

    @POST("visite")
    fun createVisite(@Body visite: Visite) : Call<Visite>

    @GET("listePracticiens")
    fun getPracticiens(@Query("api_token") token : String): Call<List<Practiciens>>

    @POST("login")
    fun getLoginToken(@Body credentials: Credentials) : Call<User>
}

object VisitesApi {
    val retrofitService : VisitesApiService by lazy {
        retrofit.create(VisitesApiService::class.java)
    }
}


