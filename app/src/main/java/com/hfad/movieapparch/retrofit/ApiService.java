package com.hfad.movieapparch.retrofit;

import com.hfad.movieapparch.model.Constants;
import com.hfad.movieapparch.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(Constants.URL_POPULAR)
    Call<MovieResponse> getPopularMovies
            (@Query("api_key") String apiKey);

    @GET(Constants.URL_POPULAR)
    Call<MovieResponse> getPopularMoviesPaging
            (@Query("api_key") String apiKey,
             @Query("page") int page);
}
