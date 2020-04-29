package com.example.gr.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.gr.R;
import com.example.gr.logic.ItemType;
import com.example.gr.logic.MathUtils;
import com.example.gr.logic.StringUtils;
import com.example.gr.logic.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for ViewPager in HistoryActivity
 *
 * @author Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class ItemFragment extends Fragment {

    private ListView listView;
    private TextView average;
    private final ArrayList<Pair<String, Integer>> items = new ArrayList<>();
    private final StringUtils stringUtils = new StringUtils();
    private final MathUtils mathUtils = new MathUtils();
    private UserData mUserData;
    private ItemType type;

    /**
     * Constructor
     *
     * @param type ItemType
     */
    public ItemFragment(ItemType type, UserData userData) {
        this.type = type;
        this.mUserData = userData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadValues();
    }


    private void loadValues() {
        List<Pair<String, Integer>> values = mUserData.getAllValues(type);
        if (values != null) {
            items.clear();
            items.addAll(values);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set list view
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, items) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // inflate view
                View elementView = inflater.inflate(R.layout.exercise_element, parent, false);

                // set textView with relevant text
                TextView textView = elementView.findViewById(R.id.element_txt);
                textView.setText(getString(R.string.history_list_item, stringUtils.getNiceDate(items.get(position).first), items.get(position).second));

                // set remove button listener
                Button btn_remove = elementView.findViewById(R.id.element_edit);
                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditDialog(position);
                    }
                });

                return elementView;
            }
        });

        // set average textView
        average = view.findViewById(R.id.average);
        updateAverage();
    }

    private void updateAverage() {
        if (average != null && mUserData != null) {
            average.setText(getString(R.string.history_average, mathUtils.getAverage(mUserData.getAllValuesPlain(type)), type.getAppendix()));

        }
    }

    /**
     * show dialog to edit values
     *
     * @param position position of the element in items array
     */
    private void showEditDialog(final int position) {
        final View dialogView = View.inflate(requireContext(), R.layout.dialog_edit, null);
        final EditText editText = dialogView.findViewById(R.id.edit_value);
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
        alertDialog.setTitle(getString(R.string.dialog_edit_title));
        alertDialog.setView(dialogView);

        // set positive button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            mUserData.editItem(type, items.get(position).first, Integer.parseInt(editText.getText().toString()));
                            // update values after changes
                            loadValues();
                            // update views after updating values after changes
                            listView.invalidateViews();
                            updateAverage();
                            // set result to update after
                            requireActivity().setResult(Activity.RESULT_OK);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });

        // set negative button
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

}