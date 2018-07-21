package br.com.rads.gistnator.gist

import br.com.rads.gistnator.gist.response.GistsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface GistServiceApi {
    @GET("/gists/public")
    fun listGists(): Observable<List<GistsResponse>>
}