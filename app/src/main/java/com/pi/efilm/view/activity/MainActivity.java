package com.pi.efilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pi.efilm.R;
import com.pi.efilm.view.fragment.FilmesFragment;
import com.pi.efilm.view.fragment.MaisFragment;
import com.pi.efilm.view.fragment.QuizFragment;
import com.pi.efilm.view.fragment.SeriesFragment;

import static com.pi.efilm.util.AppUtil.printHashKey;
import static com.pi.efilm.util.Constantes.NOME;

public class MainActivity extends AppCompatActivity {
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printHashKey(this);
        initViews();
        initAbas();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), ResultadoBuscaActivity.class);
                intent.putExtra(NOME, query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initViews() {
        smartTabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        searchView = findViewById(R.id.searchView);
    }

    private void initAbas() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this).add(R.string.filmes, FilmesFragment.class).add(R.string.series, SeriesFragment.class).add(R.string.pessoas, QuizFragment.class).add(R.string.mais, MaisFragment.class).create());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
