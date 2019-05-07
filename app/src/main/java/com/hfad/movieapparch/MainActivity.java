package com.hfad.movieapparch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.movieapparch.model.Constants;
import com.hfad.movieapparch.model.MovieResponse;
import com.hfad.movieapparch.model.Result;
import com.hfad.movieapparch.retrofit.ApiService;
import com.hfad.movieapparch.retrofit.RetrofitClient;
import com.hfad.movieapparch.viewmodel.MainViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapter;
    private PagedList<Result> movies;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        getPopularMovies();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getPopularMovies(){
        Observer<PagedList<Result>> movieObserverList = new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(@Nullable PagedList<Result> resultList) {
                if (resultList != null) {
                    movies = resultList;
                }
                if (adapter == null) {
                    adapter = new MovieAdapter(MainActivity.this);
                    adapter.submitList(movies);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }
                adapter.setOnClickListener(new MovieAdapter.OnClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        Result result = movies.get(position);
                        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                        intent.putExtra(MovieActivity.KEY_MOVIE, result);
                        startActivity(intent);
                    }
                });
            }
        };
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMoviesPageList().observe(this, movieObserverList);
    }
}
