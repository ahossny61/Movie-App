package com.ahmedhossny61.movieapp.Lists;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedhossny61.movieapp.R;

public class FilmHolder extends RecyclerView.ViewHolder {
    ImageView icon_rate;
    TextView name;
    ImageView image;
    TextView rate;
    TextView releaseDate;
    public FilmHolder(@NonNull View itemView) {
        super(itemView);
        icon_rate=itemView.findViewById(R.id.icon_rate);
        name=itemView.findViewById(R.id.list_name);
        image=itemView.findViewById(R.id.list_image);
        rate=itemView.findViewById(R.id.list_rated);
        releaseDate=itemView.findViewById(R.id.list_releaseDate);
    }
}
