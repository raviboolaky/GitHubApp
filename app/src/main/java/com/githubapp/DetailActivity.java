package com.githubapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.githubapp.component.RequestManager;
import com.githubapp.model.Repository;


public class DetailActivity extends ActionBarActivity {

    ImageView avatarImageView;
    TextView descriptionTextView;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        initVariables();

    }


    private void initViews() {
        this.avatarImageView = (ImageView) findViewById(R.id.avatarImageView);
        this.descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
    }

    private void initVariables() {

        Intent intent = getIntent();
        this.repo = (Repository) intent.getSerializableExtra("repo");

        Log.i("full_name", repo.full_name);

        getSupportActionBar().setTitle(repo.full_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(repo.avatar_url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        avatarImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        avatarImageView.setImageResource(R.drawable.ic_launcher);
                    }
                });

        // Access the RequestQueue through your singleton class.
        RequestManager.getInstance(this).getRequestQueue().start();

        // Access the RequestQueue through your singleton class.
        RequestManager.getInstance(this).addToRequestQueue(request);

        Resources res = getResources();
        String.format(res.getString(R.string.description_content), this.repo.description);
        //this.descriptionTextView.setText(this.repo.description);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
        } else if (id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
