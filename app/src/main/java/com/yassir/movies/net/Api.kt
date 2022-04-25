package com.yassir.movies.net

import com.yassir.movies.data.models.Movie
import com.yassir.movies.data.models.MovieCollection
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

/**
 * Interface to define REST API calls.
 */
interface Api {
    companion object {
        /** The API Endpoints.  */
        private const val GET_MOVIES_DISCOVER = "3/discover/movie"
        private const val GET_MOVIE_DETAILS = "3/movie/{movie_id}"

        /** api_key (used for testing purposes) */
        private const val API_KEY = "c9856d0cb57c3f14bf75bdc6c063b8f3"

        fun create(): Api {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/")
                .build()

            return retrofit.create(Api::class.java)
        }
    }

    @GET(GET_MOVIES_DISCOVER)
    fun fetchMoviesDiscover(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("page") page: String = "1",
    ): Observable<MovieCollection.Result>

    @GET(GET_MOVIES_DISCOVER)
    fun fetchMoviesLatest(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort_by: String = "release_date.desc",
        @Query("page") page: String = "1",
        @Query("vote_count.gte") vote_count_gte: Int = 20,
        @Query("release_date.lte") release_date_lte: String = "9999-99-99",
        ): Observable<MovieCollection.Result>

    @GET(GET_MOVIES_DISCOVER)
    fun fetchMoviesUpcoming(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("page") page: String = "1",
        @Query("primary_release_date.gte") release_date_gte: String = "9999-99-99",
    ): Observable<MovieCollection.Result>

    @GET(GET_MOVIES_DISCOVER)
    fun fetchMoviesRelated(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("page") page: String = "1",
        @Query("with_genres") with_genres: String = "",
    ): Observable<MovieCollection.Result>

    @GET(GET_MOVIE_DETAILS)
    fun fetchMovieDetails(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<Movie>
}