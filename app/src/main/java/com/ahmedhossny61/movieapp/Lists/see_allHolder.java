package com.ahmedhossny61.movieapp.Lists;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedhossny61.movieapp.R;

public class see_allHolder extends RecyclerView.ViewHolder {
    ImageView seeAll_image;
    TextView seeAll_title;
    TextView seeAll_language;
    TextView seeAll_releaseData;
    TextView seeAll_rating;
    TextView seeAll_voting;
    TextView seeAll_addToFavourity;
    public see_allHolder(@NonNull View itemView) {
        super(itemView);
        seeAll_addToFavourity=itemView.findViewById(R.id.seeAll_AddFavourity);
        seeAll_image=itemView.findViewById(R.id.seeAll_image);
        seeAll_title=itemView.findViewById(R.id.seeAll_title);
        seeAll_language=itemView.findViewById(R.id.seeAll_language);
        seeAll_rating=itemView.findViewById(R.id.seeAll_rating);
        seeAll_releaseData=itemView.findViewById(R.id.seeAll_release_data);
        seeAll_voting=itemView.findViewById(R.id.seeAll_vote_count);
    }
}
