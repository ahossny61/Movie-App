package com.ahmedhossny61.movieapp.Lists;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedhossny61.movieapp.R;
import com.ahmedhossny61.movieapp.Film;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class filmAdapter_main extends RecyclerView.Adapter<FilmHolder_main> {
    ArrayList<Film> films=new ArrayList<>();
    private String BaseUrl="https://api.themoviedb.org/3/movie/";
    private String BaseUrl2="/videos?api_key=ed7491be49c4db82832af10ed434b1e4";
    private String id;
    private String movie_url;
    ArrayList<String>videos_url=new ArrayList<>();
    private String youtube_url="https://www.youtube.com/watch?v=";
    private FilmHolder_main context;
    public filmAdapter_main(ArrayList<Film>f){
        films=f;
    }
    @NonNull
    @Override
    public FilmHolder_main onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_recycler, viewGroup, false);
        return new FilmHolder_main(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilmHolder_main holder,final int i) {
        context=holder;
        holder.title.setText(films.get(i).getName());
        //holder.rate.setText(films.get(i).getRate());
        Picasso.get().load(films.get(i).getImage_url()).into(holder.poster);
        Picasso.get().load(films.get(i).getImage_backdrop_path()).into(holder.background);
        holder.date.setText(films.get(i).getRelease_date());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie_url=BaseUrl+String.valueOf(films.get(i).getId())+BaseUrl2;
                getData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }
    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(context.itemView.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,movie_url, listener, errorListener);
        queue.add(stringRequest);
    }
    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject root=new JSONObject(response);
                JSONArray arr=root.getJSONArray("results");
                for (int i=0;i<arr.length();i++){
                    JSONObject object=arr.getJSONObject(i);
                    videos_url.add(object.optString("key"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            youtube_url+=videos_url.get(0);
            Uri link=Uri.parse(youtube_url);
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(link);
            context.itemView.getContext().startActivity(intent);
        }

    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(context.itemView.getContext(),"error" + error.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

}
