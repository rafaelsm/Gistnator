package br.com.rads.gistnator.gist

import br.com.rads.gistnator.gist.response.GistsResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface GistServiceApi {

    @GET("/gists/public")
    fun listGists(): Observable<List<GistsResponse>>

    @GET
    fun rawGist(@Url url: String): Observable<ResponseBody>

    companion object {
        fun getService(): GistServiceApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            return retrofit.create(GistServiceApi::class.java)
        }
    }
}