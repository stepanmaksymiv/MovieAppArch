package com.hfad.movieapparch.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.hfad.movieapparch.retrofit.ApiService;

public class MovieFactory extends DataSource.Factory{

    private MovieDataSource dataSource;
    private ApiService service;
    private Application application;
    private MutableLiveData<MovieDataSource>liveData;

    public MovieFactory(ApiService service, Application application) {
        this.service = service;
        this.application = application;
        liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        dataSource = new MovieDataSource(service, application);
        liveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<MovieDataSource> getLiveData() {
        return liveData;
    }
}
