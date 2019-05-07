package com.hfad.movieapparch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.hfad.movieapparch.model.MovieDataSource;
import com.hfad.movieapparch.model.MovieFactory;
import com.hfad.movieapparch.model.Result;
import com.hfad.movieapparch.retrofit.ApiService;
import com.hfad.movieapparch.retrofit.RetrofitClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    private Executor executor;
    private LiveData<MovieDataSource> dateSource;
    private LiveData<PagedList<Result>>moviesPageList;

    public MainViewModel(@NonNull Application application) {
        super(application);

        ApiService service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        MovieFactory factory = new MovieFactory(service, application);
        dateSource = factory.getLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(7);
        moviesPageList = new LivePagedListBuilder<Integer, Result>(factory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Result>> getMoviesPageList() {
        return moviesPageList;
    }
}
