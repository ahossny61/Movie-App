package com.ahmedhossny61.movieapp.Lists;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedhossny61.movieapp.Activities.Movie_detail;
import com.ahmedhossny61.movieapp.R;
import com.ahmedhossny61.movieapp.Film;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmHolder> {
    private ArrayList<Film> films=new ArrayList<>();
    private int option;
    public FilmAdapter(ArrayList<Film>f,int moption){
        films=f;
        option=moption;
    }
    @NonNull
    @Override
    public FilmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list, viewGroup, false);
        return new FilmHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilmHolder holder, final int i) {
        if(option==1) {
            holder.name.setText(films.get(i).getName());
            holder.rate.setText(films.get(i).getRate());
            Picasso.get().load(films.get(i).getImage_url()).into(holder.image);
        }
        else if(option==2){
            holder.name.setText(films.get(i).getName());
            holder.releaseDate.setText(films.get(i).getRelease_date());
            Picasso.get().load(films.get(i).getImage_url()).into(holder.image);
            holder.icon_rate.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Movie_detail.class);
                intent.putExtra("id",films.get(i).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }
}
