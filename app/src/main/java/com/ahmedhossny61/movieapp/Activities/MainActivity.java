package com.ahmedhossny61.movieapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedhossny61.movieapp.Film;
import com.ahmedhossny61.movieapp.Lists.FilmAdapter;
import com.ahmedhossny61.movieapp.Lists.filmAdapter_main;
import com.ahmedhossny61.movieapp.R;
import com.ahmedhossny61.movieapp.json.jsonRead;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmedhossny61.movieapp.DB.dbHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {
    private Button Main_retry;
    private TextView main_internet_text;
    private ProgressBar Main_prgreeBar;
    private TextView popularText;
    private TextView topRatedText;
    private TextView upComingText;
    private dbHelper db;
    private TextView popular_seeAll;
    private TextView topRated_seeAll;
    final int speedScroll = 3000;
    final Handler handler = new Handler();
    private RecyclerView recycler_mostRated, recycler_popular, recycler_upComing;
    private FilmAdapter mostRatedAdapter, popularAdapter, upComingAdapter;
    private filmAdapter_main mainAdapter;
    private ArrayList<Film> mostRated = new ArrayList<>();
    private ArrayList<Film> upComing_Array = new ArrayList<>();
    private ArrayList<Film> main_array = new ArrayList<>();
    private ArrayList<Film> popularArray = new ArrayList<>();
    private RecyclerView recycler_main;
    private static final String upComingBaseUrl3 = "https://api.themoviedb.org/3/movie/upcoming?api_key=ed7491be49c4db82832af10ed434b1e4&language=en-US&page=2&region=US";
    private static final String upComingBaseUrl2 = "https://api.themoviedb.org/3/movie/upcoming?api_key=ed7491be49c4db82832af10ed434b1e4&language=en-US&page=1&region=JP";
    private static final String upComingBaseUrl1 = "https://api.themoviedb.org/3/movie/upcoming?api_key=ed7491be49c4db82832af10ed434b1e4&language=en-US&page=1&region=US";
    private static final String topRatedBaseUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key=ed7491be49c4db82832af10ed434b1e4";
    private static final String popularBaseUrl = "https://api.themoviedb.org/3/movie/popular?api_key=ed7491be49c4db82832af10ed434b1e4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main_retry=findViewById(R.id.main_retry);
        Main_retry.setVisibility(View.GONE);
        main_internet_text=findViewById(R.id.main_internet_text);
        main_internet_text.setVisibility(View.GONE);
        popularText = findViewById(R.id.mostPopularMoviesText);
        topRatedText = findViewById(R.id.topRateMiviesText);
        upComingText = findViewById(R.id.upComingMoviesText);
        Main_prgreeBar = findViewById(R.id.Main_prgreeBar);
        Main_prgreeBar.setVisibility(View.VISIBLE);
        recycler_mostRated = (RecyclerView) findViewById(R.id.recycler_TopRated);
        recycler_popular = (RecyclerView) findViewById(R.id.recycler_popular);
        recycler_upComing = (RecyclerView) findViewById(R.id.recycler_upComing);
        recycler_main = (RecyclerView) findViewById(R.id.main_recyclerView);
        popular_seeAll = findViewById(R.id.seeAll_popular);
        topRated_seeAll = findViewById(R.id.seeAll_topRated);
        popular_seeAll.setVisibility(View.GONE);
        topRatedText.setVisibility(View.GONE);
        topRated_seeAll.setVisibility(View.GONE);
        popularText.setVisibility(View.GONE);
        upComingText.setVisibility(View.GONE);
        recycler_main.setVisibility(View.INVISIBLE);
        recycler_upComing.setVisibility(View.GONE);
        recycler_mostRated.setVisibility(View.GONE);
        recycler_popular.setVisibility(View.GONE);
        internet checkIfInternetAvailableTask=new internet();
        checkIfInternetAvailableTask.execute(1);
    }
    private void start(){
        Main_retry.setVisibility(View.GONE);
        popular_seeAll.setVisibility(View.VISIBLE);
        topRatedText.setVisibility(View.VISIBLE);
        topRated_seeAll.setVisibility(View.VISIBLE);
        popularText.setVisibility(View.VISIBLE);
        upComingText.setVisibility(View.VISIBLE);
        recycler_main.setVisibility(View.VISIBLE);
        recycler_upComing.setVisibility(View.VISIBLE);
        recycler_mostRated.setVisibility(View.VISIBLE);
        recycler_popular.setVisibility(View.VISIBLE);
        Main_prgreeBar.setVisibility(View.GONE);
        upComingAdapter = new FilmAdapter(upComing_Array, 2);
        mostRatedAdapter = new FilmAdapter(mostRated, 1);
        popularAdapter = new FilmAdapter(popularArray, 1);
        mainAdapter = new filmAdapter_main(main_array);
        recycler_mostRated.setAdapter(mostRatedAdapter);
        recycler_popular.setAdapter(popularAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            LinearLayoutManager linearLayoutManager_main = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recycler_mostRated.setLayoutManager(linearLayoutManager);
        recycler_popular.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recycler_main.setAdapter(mainAdapter);
        recycler_main.setLayoutManager(linearLayoutManager_main);
        recycler_upComing.setAdapter(upComingAdapter);
        recycler_upComing.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recycler_mostRated.hasFixedSize();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                if (count < mainAdapter.getItemCount()) {
                    if (count == mainAdapter.getItemCount() - 1) {
                        flag = false;
                    } else if (count == 0) {
                        flag = true;
                    }
                    if (flag) count++;
                    else count--;

                    recycler_main.smoothScrollToPosition(count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };

        handler.postDelayed(runnable, speedScroll);

        recycler_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Toast.makeText(MainActivity.this,"ffgg",Toast.LENGTH_SHORT).show();
            }
        });
        popular_seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, seeAll.class);
                i.putExtra("BaseUrl", popularBaseUrl);
                startActivity(i);
                CustomIntent.customType(MainActivity.this,"left-to-right");
            }
        });
        topRated_seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, seeAll.class);
                i.putExtra("BaseUrl", topRatedBaseUrl);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.mainMenu_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search for a film");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, seeAll.class);
                intent.putExtra("search", query);
                intent.putExtra("option", 2);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemWasClicked = item.getItemId();
        if (itemWasClicked == R.id.mainMenu_favourity) {
            // Toast.makeText(MainActivity.this,"clicked me ",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, seeAll.class);
            intent.putExtra("option", 1);
            startActivity(intent);
            //Toast.makeText(MainActivity.this,ids.get(0)+"",Toast.LENGTH_LONG).show();
            return true;
        } else if (itemWasClicked == R.id.mainMenu_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest_Upcoming1 = new StringRequest(Request.Method.GET, upComingBaseUrl1, listener_Upcoming1, errorListener);
        StringRequest stringRequest_popular = new StringRequest(Request.Method.GET, popularBaseUrl, listener_popular, errorListener);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, topRatedBaseUrl, listener, errorListener);
        StringRequest stringRequest_Upcoming2 = new StringRequest(Request.Method.GET, upComingBaseUrl2, listener_Upcoming2, errorListener);
        StringRequest stringRequest_Upcoming3 = new StringRequest(Request.Method.GET, upComingBaseUrl3, listener_Upcoming3, errorListener);
        queue.add(stringRequest_Upcoming1);
        queue.add(stringRequest);
        queue.add(stringRequest_popular);
        queue.add(stringRequest_Upcoming2);
        queue.add(stringRequest_Upcoming3);
    }

    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            ArrayList<Film> temp = jsonRead.Read(response);
            for (int i = 0; i < temp.size(); i++)
                mostRated.add(temp.get(i));
            mostRatedAdapter = new FilmAdapter(mostRated, 1);
            recycler_mostRated.setAdapter(mostRatedAdapter);
        }

    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };


    Response.Listener<String> listener_popular = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            popularArray = jsonRead.Read(response);
            popularAdapter = new FilmAdapter(popularArray, 1);
            recycler_popular.setAdapter(popularAdapter);
        }
    };

    Response.Listener<String> listener_Upcoming1 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            ArrayList<Film> test = new ArrayList<Film>();
            test = jsonRead.Read(response);
            for (int i = 0; i < test.size(); i++) {
                main_array.add(test.get(i));
                upComing_Array.add(test.get(i));
            }
            upComingAdapter = new FilmAdapter(upComing_Array, 2);
            recycler_upComing.setAdapter(upComingAdapter);
            mainAdapter = new filmAdapter_main(main_array);
            recycler_main.setAdapter(mainAdapter);
        }
    };
    Response.Listener<String> listener_Upcoming2 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            ArrayList<Film> test = new ArrayList<Film>();
            test = jsonRead.Read(response);
            for (int i = 0; i < test.size(); i++)
                main_array.add(test.get(i));
            mainAdapter = new filmAdapter_main(main_array);
            recycler_main.setAdapter(mainAdapter);
        }
    };
    Response.Listener<String> listener_Upcoming3 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            ArrayList<Film> test = new ArrayList<Film>();
            test = jsonRead.Read(response);
            for (int i = 0; i < test.size(); i++)
                main_array.add(test.get(i));
            Main_prgreeBar.setVisibility(View.GONE);
            mainAdapter = new filmAdapter_main(main_array);
            recycler_main.setAdapter(mainAdapter);
        }
    };

    public void Retry(View view) {
        Main_retry.setVisibility(View.INVISIBLE);
        Main_prgreeBar.setVisibility(View.VISIBLE);
        main_internet_text.setVisibility(View.INVISIBLE);
        internet checkIfInternetAvailableTask=new internet();
        checkIfInternetAvailableTask.execute(1);
    }

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
                getData();
            }
            else{
                Main_prgreeBar.setVisibility(View.GONE);
                main_internet_text.setVisibility(View.VISIBLE);
                Main_retry.setVisibility(View.VISIBLE);
            }
        }
    }
}