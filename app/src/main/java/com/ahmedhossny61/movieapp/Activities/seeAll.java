package com.ahmedhossny61.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ahmedhossny61.movieapp.DB.dbHelper;
import com.ahmedhossny61.movieapp.Film;
import com.ahmedhossny61.movieapp.R;
import com.ahmedhossny61.movieapp.json.jsonRead;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ahmedhossny61.movieapp.Lists.see_allAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;

import maes.tech.intentanim.CustomIntent;

public class seeAll extends AppCompatActivity {
    private TextView seeAll_internet_text2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView seeAll_internet_text;
    private String mSearchBaseUrl = "https://api.themoviedb.org/3/search/movie?api_key=ed7491be49c4db82832af10ed434b1e4&include_adult=false&query=";
    private TextView noItems;
    private String BaseUrl;
    private String FinalUrl;
    RecyclerView recycler_see_all;
    private ArrayList<Film> Array_seeAll = new ArrayList<>();
    private see_allAdapter mAdapter;
    private ProgressBar progressBar;
    private int counter;
    private TextView addToFavourity;
    private ArrayList<Film> Films;
    private dbHelper db;
    private int option;
    private Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);
        seeAll_internet_text2=findViewById(R.id.seeAll_internet_text2);
        seeAll_internet_text=findViewById(R.id.seeAll_internet_text);
        seeAll_internet_text.setVisibility(View.GONE);
        seeAll_internet_text2.setVisibility(View.GONE);
        swipeRefreshLayout=findViewById(R.id.seeAll_seipToRefresh);
        progressBar = findViewById(R.id.prgreeBar);
        noItems = findViewById(R.id.seeAll_noItems);
        noItems.setVisibility(View.GONE);
        addToFavourity = findViewById(R.id.seeAll_AddFavourity);
        progressBar = findViewById(R.id.prgreeBar);
        recycler_see_all = findViewById(R.id.recycler_seeAll);
        recycler_see_all.setVisibility(View.GONE);
        noItems.setVisibility(View.GONE);
        final internet checkInternetTask=new internet();
        checkInternetTask.execute(1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                seeAll_internet_text.setVisibility(View.INVISIBLE);
                seeAll_internet_text2.setVisibility(View.INVISIBLE);
                internet checkInternetTask=new internet();
                checkInternetTask.execute(1);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }


        });
    }
    private void start(){
        recycler_see_all.setVisibility(View.VISIBLE);
        b = getIntent().getExtras();
        BaseUrl = b.getString("BaseUrl");
        option = b.getInt("option");
        mAdapter = new see_allAdapter(Films);
        recycler_see_all.setAdapter(mAdapter);
        recycler_see_all.setLayoutManager(new LinearLayoutManager(seeAll.this));
        if (option == 1) {
            db = new dbHelper(seeAll.this);
            Films = db.AllFilms();
            if (Films.size() == 0) {
                noItems.setVisibility(View.VISIBLE);
            }
            mAdapter = new see_allAdapter(Films);
            recycler_see_all.setAdapter(mAdapter);
            recycler_see_all.setLayoutManager(new LinearLayoutManager(seeAll.this));
            progressBar.setVisibility(View.GONE);
        } else if (option == 2) {
            String mSearch = b.getString("search");
            progressBar.setVisibility(View.VISIBLE);
            mAdapter = new see_allAdapter(Array_seeAll);
            recycler_see_all.setAdapter(mAdapter);
            recycler_see_all.setLayoutManager(new LinearLayoutManager(seeAll.this));
            counter = 1;
            while (counter < 10) {
                String pagrNumber = String.valueOf(counter);
                mSearchBaseUrl += mSearch + "&page=" + pagrNumber;
                getData(1);
                counter++;
            }
        } else {
            progressBar.setVisibility(View.VISIBLE);
            //Toast.makeText(seeAll.this,"else",Toast.LENGTH_SHORT).show();
            mAdapter = new see_allAdapter(Array_seeAll);
            recycler_see_all.setAdapter(mAdapter);
            recycler_see_all.setLayoutManager(new LinearLayoutManager(seeAll.this));
            counter = 1;
            while (counter < 10) {
                String pagrNumber = String.valueOf(counter);
                FinalUrl = BaseUrl + "&page=" + pagrNumber;
                getData(0);
                counter++;
            }
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public void Resume_ifInternetAvailable(){
        if (option == 1) {
            db = new dbHelper(seeAll.this);
            Films = db.AllFilms();
            if (Films.size() == 0) {
                noItems.setVisibility(View.VISIBLE);
            }
            mAdapter = new see_allAdapter(Films);
            mAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        } else {
            mAdapter = new see_allAdapter(Array_seeAll);
            recycler_see_all.setAdapter(mAdapter);
        }
    }

    private void getData(int op) {
        RequestQueue queue = Volley.newRequestQueue(this);
        if (op == 0) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, FinalUrl, listener, errorListener);
            queue.add(stringRequest);
        } else if (op == 1) {
            // Toast.makeText(seeAll.this,"hi",Toast.LENGTH_SHORT).show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, mSearchBaseUrl, listener, errorListener);
            queue.add(stringRequest);
        }


    }

    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            ArrayList<Film> test = new ArrayList<>();
            test = jsonRead.Read(response);
            for (int i = 0; i < test.size(); i++)
                Array_seeAll.add(test.get(i));
            Collections.sort(Array_seeAll);
            mAdapter = new see_allAdapter(Array_seeAll);
            recycler_see_all.setAdapter(mAdapter);
            if (counter == 10)
                progressBar.setVisibility(View.GONE);
        }

    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(seeAll.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
            }
            else {
                recycler_see_all.setVisibility(View.GONE);
                noItems.setVisibility(View.GONE);
               progressBar.setVisibility(View.GONE);
                seeAll_internet_text.setVisibility(View.VISIBLE);
                seeAll_internet_text2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomIntent.customType(seeAll.this,"right-to-left");
    }
}
