package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

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
    private SearchView searchView;
    private String nomeBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initAbas();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nomeBusca = query;
                Intent intent = new Intent(getApplicationContext(), ResultadoBuscaActivity.class);
                intent.putExtra("NOME", nomeBusca);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void initViews(){
        smartTabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        searchView = findViewById(R.id.searchView);
    }

    public void initAbas(){
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this).add(R.string.filmes, FilmesFragment.class).add(R.string.series, SeriesFragment.class).add(R.string.pessoas, QuizFragment.class).add(R.string.mais, MaisFragment.class).create());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }
}
