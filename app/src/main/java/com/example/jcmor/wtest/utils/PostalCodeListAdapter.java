package com.example.jcmor.wtest.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jcmor.wtest.R;

import java.util.ArrayList;

/*
 * Created by jcmor on 27/01/2018.
 */

public class PostalCodeListAdapter
        extends RecyclerView.Adapter<PostalCodeListAdapter.PostalCodeViewHolder> {

    private ArrayList<String> list;
    private LayoutInflater inflater;

    public PostalCodeListAdapter(Context context, ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public PostalCodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.postalcode_item, parent, false);
        return new PostalCodeViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(PostalCodeViewHolder holder, int position) {

        holder.postCodeItemView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return (int) list.size();
    }

    class PostalCodeViewHolder extends RecyclerView.ViewHolder {

        final TextView postCodeItemView;
        final PostalCodeListAdapter postalCodeAdapter;

        PostalCodeViewHolder(View itemView, PostalCodeListAdapter adapter) {
            super(itemView);

            postCodeItemView = itemView.findViewById(R.id.txt_postalCode);
            this.postalCodeAdapter = adapter;
        }
    }
}
