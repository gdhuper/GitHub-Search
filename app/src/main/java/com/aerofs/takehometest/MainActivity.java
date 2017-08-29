package com.aerofs.takehometest;


import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.io.*;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;



import org.json.JSONArray;

import retrofit2.http.POST;


public class MainActivity extends AppCompatActivity {


    SearchView searchView;
    //ListView repoListView;
    String userName;

    private final static String REPOS_BASE_URL = "https://api.github.com/users/";
    private final static String REPOS_URL_POSTFIX = "/repos";


    private Context context;

    private RepoListAdapter adapter;
    private ArrayList<RepoListItem> repoList;

    private TextView defaultTextView;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        //Adding action bar in main layout
        addActionbar();

        // list view object to display repositories list
        final ListView repoListView = (ListView) findViewById(R.id.repoList);

        defaultTextView = (TextView) findViewById(R.id.default_view);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //searchview query listener for reading user input
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userName = query;
                System.out.println("Username Entered:" + userName);
                if(userName != null){
                    String fullRepoUrl = REPOS_BASE_URL + userName + REPOS_URL_POSTFIX;

                    //get repositories list for a username
                  repoList =  JsonUtility.getRepos(userName);

                    if(repoList != null) {
                        //clear old user bio layout if it exists
                        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.user_bio_view);
                        insertPoint.removeAllViewsInLayout();
                        //setting default textview invisible
                        defaultTextView.setVisibility(View.INVISIBLE);
                        defaultTextView.setVisibility(View.GONE);
                        repoListView.setVisibility(View.VISIBLE);
                        //setting list of repositories
                        adapter = new RepoListAdapter(context, repoList);
                        repoListView.setAdapter(adapter);
                        //hide keyboard on result
                        searchView.clearFocus();

                        //gets user bio information
                        final UserBioActivity userBio = JsonUtility.getUserBio(userName);

                        System.out.println("bio: "+ userBio.getAvatar_url() +" " + userBio.getName());
                        try {
                            injectUserBio(userBio);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        // clear old user bio layout in case of error
                        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.user_bio_view);
                        insertPoint.removeAllViewsInLayout();
                        defaultTextView.setVisibility(View.VISIBLE);
                        repoListView.setVisibility(View.INVISIBLE);
                        defaultTextView.setText("User " + userName +" does not exist!");
                        System.out.println(" User name does not exist!");
                    }

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }




    /*
     * Helper method to add user bio layout to activity_main
     */
    public void injectUserBio(final UserBioActivity uBio) throws MalformedURLException, IOException{

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  v = vi.inflate(R.layout.user_bio, null);

        ImageView avatar = (ImageView) v.findViewById(R.id.avatar);

        URL url = new URL(uBio.getAvatar_url());
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        avatar.setImageBitmap(bmp);



        TextView nameView = (TextView) v.findViewById(R.id.name);
        nameView.setText(uBio.getName());

        TextView userNameView = (TextView) v.findViewById(R.id.username);
        userNameView.setText(uBio.getUserName());

        TextView locationView = (TextView) v.findViewById(R.id.location);
        locationView.setText(uBio.getLocation());


        TextView blogUrlView = (TextView) v.findViewById(R.id.blog);
        blogUrlView.setText(uBio.getBlogUrl());


        // insert into main view
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.user_bio_view);

        insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));


    }



    /*
     * Helper method to add action bar to top of main activity
     */
    public void addActionbar(){
        //adding icon to action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.mipmap.github_logo);
        ab.setDisplayUseLogoEnabled(true);
        ab.setTitle(R.string.app_name);
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

        if(id == R.id.home){
            startActivity(new Intent(this, MainActivity.class));
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }



}
