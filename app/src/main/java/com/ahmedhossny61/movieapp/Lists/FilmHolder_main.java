package com.ahmedhossny61.movieapp.Lists;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedhossny61.movieapp.R;

import org.w3c.dom.Text;

public class FilmHolder_main extends RecyclerView.ViewHolder {
     ImageView background;
     ImageView poster;
     TextView title;
    TextView date;
    public FilmHolder_main(@NonNull View itemView) {
        super(itemView);
       background=(ImageView)itemView.findViewById(R.id.main_recycler_image);
        poster=(ImageView)itemView.findViewById(R.id.main_recycler_image2);
        title=(TextView) itemView.findViewById(R.id.main_recycler_name);
        date=(TextView)itemView.findViewById(R.id.main_recycler_release_date);
    }
}
