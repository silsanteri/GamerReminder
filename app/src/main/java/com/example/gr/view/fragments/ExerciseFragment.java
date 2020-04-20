package com.example.gr.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gr.R;

import java.util.ArrayList;

// author Ruslan

public class ExerciseFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> exercises;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadExercises();
    }


    private void loadExercises() {
        // todo load exercises from db
        exercises = new ArrayList<>();
        exercises.add("15.04.2020 30 min");
        exercises.add("14.04.2020 25 min");
        exercises.add("13.04.2020 29 min");
        exercises.add("12.04.2020 50 min");
        exercises.add("11.04.2020 70 min");
        exercises.add("10.04.2020 10 min");
        exercises.add("9.04.2020 20 min");
        exercises.add("8.04.2020 32 min");
        exercises.add("7.04.2020 67 min");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView_exercise);
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, exercises) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // inflate view
                View elementView = inflater.inflate(R.layout.exercise_element, parent, false);

                // add textview with relevant text
                TextView textView = elementView.findViewById(R.id.element_txt);
                textView.setText(exercises.get(position));

                // add remove button listener
                Button btn_remove = elementView.findViewById(R.id.element_remove);
                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // todo remove
                    }
                });

                // add add button listener
                Button btn_add = elementView.findViewById(R.id.element_add);
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // todo add
                    }
                });

                return elementView;
            }
        });

    }

}