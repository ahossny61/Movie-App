package com.ahmedhossny61.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedhossny61.movieapp.DB.dbHelper;
import com.ahmedhossny61.movieapp.R;
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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Movie_detail extends AppCompatActivity {
    String title_json;
    private String language_json;
    private dbHelper db;
    String release_date_json;
    private TextView title;
    private String image_url;
    private ImageView backdrop;
    private ImageView play_vido;
    private TextView add_favourity;
    private TextView overview;
    private TextView released_date;
    private TextView rating;
    private TextView vote_count;
    private TextView language;
    String vote_count_json;
    private String poster_path;
    private String videoBaseUrl="/videos?api_key=ed7491be49c4db82832af10ed434b1e4";
    private String BaseUrl="https://api.themoviedb.org/3/movie/";
    private String Detail_BaseUrl="?api_key=ed7491be49c4db82832af10ed434b1e4";
    private String id;
    String Rating_json;
    private String movie_url;
    private String detail_url;
    private TextView homepage;
    ArrayList<String>videos_url=new ArrayList<>();
    private String homepage_json;
    private String youtube_url="https://www.youtube.com/watch?v=";
    private String backdrop_path;
    private ImageView done;
    private TextView addedToFavourity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progresslayout);
    }
    @Override
    protected void onStart(){
        super.onStart();
        internet checkInternetTask=new internet();
        checkInternetTask.execute(1);
    }
    public void onResume() {
        super.onResume();

    }

    private void start(){
        setContentView(R.layout.activity_movie_detail);
        Bundle b=getIntent().getExtras();
        id=String.valueOf(b.getInt("id"));
        movie_url=BaseUrl+id+videoBaseUrl;
        detail_url=BaseUrl+id+Detail_BaseUrl;
        homepage=(TextView)findViewById(R.id.film_homePage);
        homepage.setVisibility(View.GONE);
        title=(TextView)findViewById(R.id.film_title);
        overview=(TextView)findViewById(R.id.film_overView);
        language=(TextView)findViewById(R.id.film_language);
        released_date=(TextView)findViewById(R.id.film_releaseDate);
        rating=(TextView)findViewById(R.id.film_rating);
        vote_count=(TextView)findViewById(R.id.film_voteCount);
        backdrop=(ImageView) findViewById(R.id.detail_image);
        play_vido=(ImageView) findViewById(R.id.video_playOn);
        add_favourity=(TextView)findViewById(R.id.addToFavourite);
        addedToFavourity=findViewById(R.id.addedToFavourity);
        done=findViewById(R.id.done);
        addedToFavourity.setVisibility(View.GONE);
        done.setVisibility(View.GONE);
        db=new dbHelper(Movie_detail.this);
        if(db.selectWith_id(Integer.parseInt(id))>0){
            add_favourity.setVisibility(View.GONE);
            addedToFavourity.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
        }
        else {
            add_favourity.setVisibility(View.VISIBLE);
        }
        getData();
        play_vido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri uri=Uri.parse(homepage);
                intent.setData(uri);
                startActivity(intent);*/
                Uri link=Uri.parse(youtube_url);
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(link);
                startActivity(intent);
            }
        });
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri uri=Uri.parse(homepage_json);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        add_favourity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=new dbHelper(Movie_detail.this);
                db.InsertFilm(Integer.parseInt(id),title_json,language_json
                        ,release_date_json,Rating_json
                        ,Integer.parseInt(vote_count_json)
                        ,poster_path);
                Toast.makeText(Movie_detail.this,"added to your favourity",Toast.LENGTH_SHORT).show();
                add_favourity.setVisibility(View.GONE);
                addedToFavourity.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                //Toast.makeText(Movie_detail.this,poster_path,Toast.LENGTH_SHORT).show();
                // Toast.makeText(Movie_detail.this,Rating_json/*+"\n"+Integer.parseInt(vote_count_json)+"\n"+backdrop_path*/,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,movie_url, listener, errorListener);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET,detail_url, listener_detail, errorListener);
        queue.add(stringRequest);
        queue.add(stringRequest2);
    }
    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject root=new JSONObject(response);
                JSONArray arr=root.getJSONArray("results");
                if(arr.length()<2){
                    play_vido.setVisibility(View.GONE);
                }
                else {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject object = arr.getJSONObject(i);
                        videos_url.add(object.optString("key"));
                    }
                    youtube_url += videos_url.get(0);
                }
            } catch (JSONException e) {
                Toast.makeText(Movie_detail.this,"fdfsf",Toast.LENGTH_LONG).show();
            }
        }

    };
    Response.Listener<String> listener_detail = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
               // Toast.makeText(Movie_detail.this,"fdfsf",Toast.LENGTH_LONG).show();
                JSONObject root=new JSONObject(response);
                poster_path=root.optString("poster_path");
                poster_path="https://image.tmdb.org/t/p/w500"+poster_path;
                String backdrop_path=root.optString("backdrop_path");
                homepage_json=root.optString("homepage");
                language_json=root.optString("original_language");
                title_json=root.optString("original_title");
                String overview_json=root.optString("overview");
                release_date_json=root.optString("release_date");
                Rating_json=String.valueOf(root.optString("vote_average"));
                vote_count_json=String.valueOf(root.optString("vote_count"));
                if(backdrop_path!="null") {
                   // Toast.makeText(Movie_detail.this,"has image",Toast.LENGTH_SHORT).show();
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + backdrop_path).into(backdrop);
                }
                else {
                    if(poster_path!=null)
                    Picasso.get().load(poster_path).into(backdrop);
                }
                title.setText(title_json);
                overview.setText(overview_json);
                released_date.setText(release_date_json);
                rating.setText(Rating_json);
                vote_count.setText(vote_count_json);
                language.setText(language_json);
               if(homepage_json=="null")
                    homepage.setVisibility(View.INVISIBLE);
                else
                    homepage.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Movie_detail.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            setContentView(R.layout.emptylayout);
        }
    };
    private class internet extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            try {
                int timeoutMs = 1500;
                Socket sock = new Socket();
                SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

                sock.connect(sockaddr, timeoutMs);
                sock.close();

                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true) {
                start();
            } else {
                setContentView(R.layout.emptylayout);
            }
        }
    }

}
