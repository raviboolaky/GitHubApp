package com.githubapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.githubapp.adapter.RepositoryAdapter;
import com.githubapp.component.DividerItemDecoration;
import com.githubapp.component.RequestManager;
import com.githubapp.model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ArrayList<Repository> repositories = new ArrayList<Repository>();
    RecyclerView repositoryRecyclerView;
    android.support.v7.app.ActionBar actionBar;
    String URL = "https://api.github.com/users/Caged/repos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initVariables();
    }

    public void initViews(){
        this.repositoryRecyclerView = (RecyclerView) findViewById(R.id.repositoryRecyclerView);
        this.actionBar = getSupportActionBar();
        actionBar.setTitle("Caged");
    }

    public void initVariables(){
        JsonArrayRequest req = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject repository = (JSONObject) response.get(i);

                                String name = repository.getString("name");
                                String full_name = repository.getString("full_name");
                                String watchers = repository.getString("watchers");
                                String language = repository.getString("language");
                                JSONObject owner = repository.getJSONObject("owner");
                                String avatar_url = owner.getString("avatar_url");
                                String description = repository.getString("description");

                                repositories.add(new Repository(name, full_name, language, watchers, avatar_url, description));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestManager.getInstance(this).getRequestQueue().start();

        // Adding request to request queue
        RequestManager.getInstance(this).addToRequestQueue(req);

        RepositoryAdapter adapter = new RepositoryAdapter(repositories, new RepositoryAdapter.IClickListener() {
            @Override
            public void OnClick(int position, View view) {
                Log.i("click", "OK");
                Repository repo = repositories.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("repo", repo);
                startActivity(intent);
            }
        });
        this.repositoryRecyclerView.setAdapter(adapter);
        this.repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        this.repositoryRecyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
