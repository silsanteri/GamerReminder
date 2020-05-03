package com.example.gr.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gr.logic.ItemType;
import com.example.gr.logic.UserData;
import com.example.gr.view.fragments.ItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for ViewPager in HistoryActivity.
 *
 * @author Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class HistoryPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    // todo change userData to helper
    public HistoryPagerAdapter(FragmentManager supportFragmentManager, UserData userData, Context context) {
        super(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // add fragments to the list
        ArrayList<Fragment> fragmentsArray = new ArrayList<>();
        ArrayList<String> titlesArray = new ArrayList<>();

        for (ItemType type : ItemType.values()) {
            // add fragment
            fragmentsArray.add(new ItemFragment(type, userData));
            // add title
            titlesArray.add(type.getLocaledName(context));

        }
        fragments = fragmentsArray;
        titles = titlesArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
