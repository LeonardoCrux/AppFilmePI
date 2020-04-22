package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pi.appfilme.R;
import com.pi.appfilme.view.fragment.FilmesFragment;
import com.pi.appfilme.view.fragment.MaisFragment;
import com.pi.appfilme.view.fragment.QuizFragment;
import com.pi.appfilme.view.fragment.SeriesFragment;

public class MainActivity extends AppCompatActivity {
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initAbas();
    }

    public void initViews(){
        smartTabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    public void initAbas(){
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this).add(R.string.filmes, FilmesFragment.class).add(R.string.series, SeriesFragment.class).add(R.string.pessoas, QuizFragment.class).add(R.string.mais, MaisFragment.class).create());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }
}
