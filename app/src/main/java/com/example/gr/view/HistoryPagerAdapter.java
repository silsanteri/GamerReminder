package com.example.gr.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gr.view.fragments.CaloriesFragment;
import com.example.gr.view.fragments.ExerciseFragment;
import com.example.gr.view.fragments.WaterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for ViewPager in HistoryActivity
 * author ruslan
 */

public class HistoryPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    private List<String> titles;

    public HistoryPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // add fragments to the list
        ArrayList<Fragment> fragmentsArray = new ArrayList<>();
        fragmentsArray.add(new ExerciseFragment());
        fragmentsArray.add(new WaterFragment());
        fragmentsArray.add(new CaloriesFragment());
        fragments = fragmentsArray;

        // add titles
        ArrayList<String> titlesArray = new ArrayList<>();
        titlesArray.add("Exercise");
        titlesArray.add("Water");
        titlesArray.add("Calories");
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
