package com.ahmedhossny61.movieapp.json;

import com.ahmedhossny61.movieapp.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class jsonRead {

    public static ArrayList<Film> Read(String j) {
        ArrayList<Film> Films = new ArrayList<>();
        try {
            JSONObject root=new JSONObject(j);
            JSONArray arr=root.getJSONArray("results");
            for (int i = 0; i <arr.length() ; i++) {
                JSONObject object=arr.getJSONObject(i);
                String adult=object.optString("adult");
                if(adult!="false")
                    continue;
                String title=object.optString("title");
                int rate=object.optInt("vote_average");
                String image_url=object.optString("poster_path");
                int id=object.optInt("id");
                String background_path=object.optString("backdrop_path");
                String overview=object.optString("overview");
                int vote_count=object.optInt("vote_count");
                String release_date=object.optString("release_date");
                if(background_path=="null")
                    background_path=image_url;
                String language=object.optString("original_language");
                Films.add(new Film(title,String.valueOf(rate),image_url,background_path,vote_count,id,overview,release_date,language));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Films;
    }
}
