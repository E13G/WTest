package com.example.jcmor.wtest.exercise3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.jcmor.wtest.R;
import com.example.jcmor.wtest.utils.EditableListAdapter;

/**
 * Fragment Related to the third exercise .
 *
 * In this class we create a normal RecyclerView with 3 diferent labels and repective Editable
 * Text Fields and methods to handle clicks outside editable views to hide the keyboard
 */

public class Ex3 extends Fragment {


    public Ex3() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        View view = inflater.inflate(R.layout.fragment_ex3, container, false);

        setupRecyclerView(view);

        setupUI(view);

        return view;
    }

    public void setupRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview3);
        EditableListAdapter adapter = new EditableListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    // Set up touch listener for non-text box views to hide keyboard.
    public void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    // Set up to hide Keyboard if previous method is true
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
