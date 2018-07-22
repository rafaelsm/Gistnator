package br.com.rads.gistnator.gist

import br.com.rads.gistnator.gist.response.GistsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface GistServiceApi {

    @GET("/gists/public")
    fun listGists(): Observable<List<GistsResponse>>

    @GET
    fun rawGist(@Url url: String): Observable<String>
}