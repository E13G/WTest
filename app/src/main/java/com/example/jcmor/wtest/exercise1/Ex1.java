package com.example.jcmor.wtest.exercise1;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.example.jcmor.wtest.R;
import com.example.jcmor.wtest.utils.PostalCodeListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Fragment Related to the first exercise .
 *
 * In this class we control the recyclerView, setup the search bar in the action bar and create an
 * AsyncTask class to make the Http request in a background thread
 */
public class Ex1 extends Fragment {

    private static final String TAG = Ex1.class.getName();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<String> resultList;
    private PostalCodeOpenHelper db;
    private PostalCodeListAdapter adapter;

    public Ex1() {
    }

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //show search icon
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_ex1, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        progressBar = view.findViewById(R.id.progress);

        //populate the RecyclerView with maximum of 200 values
        resultList = new ArrayList<>();
        db = new PostalCodeOpenHelper(getContext());
        for (int i = 0; i < db.count() && i < 200;i++){
            resultList.add(formatPostalCode(db.query(i).getPostalCode()));
        }
        setupRecyclerView(view);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        return view;
    }

    private String formatPostalCode(String unformattedPostalCode) {

        StringBuilder postalCode = new StringBuilder(" ");
        postalCode.append(unformattedPostalCode.split(",")[0]);
        postalCode.append(" , ");
        postalCode.append(unformattedPostalCode.split(",")[1]);
        return postalCode.toString();
    }

    private void setupRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new PostalCodeListAdapter(getActivity(), resultList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Search","Done");
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                showSearchResult(newText);
                return false;
            }
        });
    }
    private void showSearchResult(String query) {
        ArrayList<String> searchList = new ArrayList<>();
        Cursor cursor = db.search(query);
        cursor.moveToFirst();
        if ( cursor.getCount() > 0) {
            int index;
            String result;
            do {
                index = cursor.getColumnIndex(PostalCodeOpenHelper.KEY_POSTAL_CODE);
                result = cursor.getString(index);
                searchList.add(formatPostalCode(result));
            } while (cursor.moveToNext());
            cursor.close();
        }
        recyclerView.swapAdapter(new PostalCodeListAdapter(getContext(),searchList), false);
    }
    @Override
    public void onStart() {
        super.onStart();
        //Verify if there is at least 100 items to show
        if(db.count() <100) {
            Log.d(TAG, "onStart: <50");
            hideList();
        }
        //Verify if data is already in database otherwise keep loading the missing
        if(db.count() < 10000){
            Log.d(TAG, "onStart: <10000");
            runHttpRequest();
        }
    }

    //Show either the Progress Bar or the RecyclerView in case loaded data is less than 100
    private void hideList(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void showList(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
    //Load postal code data
    private void runHttpRequest() {
        DownloadPostalCodesTask task = new DownloadPostalCodesTask();
        task.execute("https://raw.githubusercontent.com/centraldedados/codigos_postais/master/data/codigos_postais.csv" );
    }

    //Async task to get PostalCode Values in a background Thread
    private class DownloadPostalCodesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(urls[0])
                            .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String[] RowData = response.body().string().split("\n");

                    int j = 1;
                    if (db.count() > 0) j =(int) db.count();

                    for(int i = j; i < RowData.length; i++) {
                        String name = RowData[i];
                        String[] pieces = name.split(",");
                        String postal = pieces[14] + "-" + pieces[15] + " , " + pieces[16];

                        if( i == 200 ) showList();
                        db.insert(postal);
                    }
                    return "Successfull";
                }
            }catch (IOException e) {
                Log.d(TAG,"doInBackground: " + e);
            }
            return "Failed";
        }
        @Override
        protected void onPostExecute(String result) {
            showList();
        }
    }
}
