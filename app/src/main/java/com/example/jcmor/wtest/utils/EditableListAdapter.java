package com.example.jcmor.wtest.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jcmor.wtest.R;

/**
 * Created by jcmor on 28/01/2018.
 */

public class EditableListAdapter
        extends RecyclerView.Adapter<EditableListAdapter.AdaptiveViewHolder> {

    private LayoutInflater inflater;

    public EditableListAdapter(Context context) {
        inflater = LayoutInflater.from(context);

    }

    @Override
    public AdaptiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.editable_list_item, parent, false);
        return new AdaptiveViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(AdaptiveViewHolder holder, int position) {
        switch (position%3){
            case 0:
                holder.textView.setText("Editable with Normal Text");
                holder.editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 1:
                holder.textView.setText("Editable with Capitalized Text");
                holder.editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                break;
            case 2:
                holder.textView.setText("Editable with Numbers Only");
                holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            default:
        }
    }

    @Override
    public int getItemCount () {
        return 50;
    }


    class AdaptiveViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;
        final EditText editText;
        final EditableListAdapter adapter;

        AdaptiveViewHolder(View itemView, EditableListAdapter adapter) {
            super(itemView);

            textView = itemView.findViewById(R.id.label_text);
            editText = itemView.findViewById(R.id.editable_input);
            this.adapter = adapter;
        }
    }
}
