package com.test.apnacomplex.modules.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.test.apnacomplex.R;
import com.test.apnacomplex.common.BaseActivity;
import com.test.apnacomplex.modules.DataHelper;
import com.test.apnacomplex.modules.adapter.HomeGridAdapter;
import com.test.apnacomplex.utils.Constants;

import static com.test.apnacomplex.modules.DataHelper.getGridList;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupToolbar();
        populateHomeGrid();
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void populateHomeGrid() {
        GridView gridView = findViewById(R.id.home_gridView);
        gridView.setOnItemClickListener(getGRidItemClickListener());
        gridView.setAdapter(new HomeGridAdapter(this, DataHelper.getGridList()));
    }

    @NonNull
    private AdapterView.OnItemClickListener getGRidItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == Constants.HomeGridItemIds.REPOSITORY) {
                    launchRepositoryActivity();
                }
            }
        };
    }

    private void launchRepositoryActivity() {
        Intent intent = new Intent(this, RepositoryActivity.class);
        startActivity(intent);
    }
}
