package com.ahmedhossny61.movieapp.Lists;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ahmedhossny61.movieapp.DB.dbHelper;
import com.ahmedhossny61.movieapp.Activities.Movie_detail;
import com.ahmedhossny61.movieapp.R;
import com.ahmedhossny61.movieapp.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class see_allAdapter extends RecyclerView.Adapter<see_allHolder> {
    private ArrayList<Film> seeAll_film =new ArrayList<>();
    public see_allAdapter(ArrayList<Film>f){
        seeAll_film=f;
    }

    @NonNull
    @Override
    public see_allHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.see_all_item, viewGroup, false);
        return new see_allHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final see_allHolder holder,final int i) {
        holder.seeAll_title.setText(seeAll_film.get(i).getName());
        holder.seeAll_rating.setText(seeAll_film.get(i).getRate());
        holder.seeAll_voting.setText(seeAll_film.get(i).getVote_count()+"");
        holder.seeAll_rating.setText(seeAll_film.get(i).getRate());
        holder.seeAll_releaseData.setText(seeAll_film.get(i).getRelease_date());
        holder.seeAll_language.setText(seeAll_film.get(i).getLanguage());
        Picasso.get().load(seeAll_film.get(i).getImage_url()).into(holder.seeAll_image);
        dbHelper db=new dbHelper(holder.itemView.getContext());
        if(db.selectWith_id(seeAll_film.get(i).getId())>0) {
            holder.seeAll_addToFavourity.setText("In my favourity");
           // Toast.makeText(holder.itemView.getContext(),"zero",Toast.LENGTH_SHORT).show();
        }
        else {
            holder.seeAll_addToFavourity.setText("Add to favourity");
           // Toast.makeText(holder.itemView.getContext(),"one",Toast.LENGTH_SHORT).show();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Movie_detail.class);
                intent.putExtra("id",seeAll_film.get(i).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.seeAll_addToFavourity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.seeAll_addToFavourity.getText().toString()=="Add to favourity") {
                    dbHelper db = new dbHelper(view.getContext());
                    db.InsertFilm(seeAll_film.get(i).getId(), seeAll_film.get(i).getName(), seeAll_film.get(i).getLanguage()
                            , seeAll_film.get(i).getRelease_date(), seeAll_film.get(i).getRate(), seeAll_film.get(i).getVote_count()
                            , seeAll_film.get(i).getImage_url());
                    holder.seeAll_addToFavourity.setText("In my favourity");
                }
                else{
                    Toast.makeText(holder.itemView.getContext(),"This film already in your favourity!",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(view.getContext(),"fgdfgfgfd",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return seeAll_film.size();
    }
}
