package com.hfad.movieapparch;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hfad.movieapparch.model.Constants;
import com.hfad.movieapparch.model.Result;

import java.util.List;

public class MovieAdapter extends PagedListAdapter<Result, MovieAdapter.MyViewHolder> {

    private Context context;
 //   private List<Result>movieList;
    private OnClickListener onClickListener;

    public interface OnClickListener{
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MovieAdapter(Context context) {
        super(Result.callBack);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Result result = getItem(position);
        String img = Constants.IMAGE_URL_BASE_PATH + result.getPosterPath();
        Glide.with(context).load(img).into(holder.imagePoster);
    }

//    @Override
//    public int getItemCount() {
//        return movieList.size();
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePoster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.image_poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null){
                        onClickListener.OnItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
