package com.hfad.movieapparch.model;

import android.app.Application;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.hfad.movieapparch.retrofit.ApiService;
import com.hfad.movieapparch.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, Result> {

    private ApiService service;
    private Application application;
    public List<Result> movies = new ArrayList<>();


    public MovieDataSource(ApiService service, Application application) {
        this.service = service;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Result> callback) {

        service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        Call<MovieResponse>call = service.getPopularMoviesPaging(Constants.API_KEY, 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null && movieResponse.getResults() != null){
                    movies = movieResponse.getResults();
                    callback.onResult(movies, null, 2);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Result> callback) {

        service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        Call<MovieResponse>call = service.getPopularMoviesPaging(Constants.API_KEY, params.key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null && movieResponse.getResults() != null){
                    movies = movieResponse.getResults();
                    callback.onResult(movies, params.key + 1);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
