package com.example.jcmor.wtest.exercise2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jcmor.wtest.R;
import com.example.jcmor.wtest.utils.PostalCodeListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;


/**
 * Fragment Related to the second exercise .
 *
 * In this class we create a normal RecyclerView with random values and load a picture as a header
 * of the recyclerView
 */
public class Ex2 extends Fragment {

    private ArrayList<String> resultList;

    public Ex2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        View view = inflater.inflate(R.layout.fragment_ex2, container, false);

        resultList = new ArrayList<>();
        Random rn = new Random();
        for (int i = 0; i < 50;i++){
            resultList.add("Random : " + rn.nextInt(100));
        }

        //Loading AsyncImage using Picasso Libray
        Picasso.with(getContext())
                .load("https://cdn.pixabay.com/photo/2014/01/04/13/53/suspension-bridge-238519_960_720.jpg")
                .into((ImageView) view.findViewById(R.id.recyclerHeader));

        setupRecyclerView(view);

        return view;
    }

    public void setupRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview2);
        PostalCodeListAdapter adapter = new PostalCodeListAdapter(getActivity(), resultList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }
}
