package com.test.apnacomplex.modules.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.test.apnacomplex.R;
import com.test.apnacomplex.common.BaseActivity;
import com.test.apnacomplex.io.dto.Repository;
import com.test.apnacomplex.io.dto.DocumentCategoryResponse;
import com.test.apnacomplex.modules.adapter.RepositoryAdapterListener;
import com.test.apnacomplex.modules.adapter.RepositoryRecyclerAdapter;
import com.test.apnacomplex.modules.dataModels.DocumentCategoryApiModelImpl;
import com.test.apnacomplex.utils.Constants;

import org.parceler.Parcels;

public class RepositoryActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = RepositoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setShouldAnimateActivity(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_document_category);
        setupToolbar();
        initViews();
        loadDocumentCategoriesFromServer();
    }

    private void initViews() {
        findViewById(R.id.doc_cat_fab).setOnClickListener(this);
        findViewById(R.id.back_icon).setOnClickListener(this);
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.doc_cat_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void loadDocumentCategoriesFromServer() {
        new DocumentCategoryApiModelImpl().fetchDocumentCategoryListFromServer(
                new DocumentCategoryApiModelImpl.DocumentCategoryApiListener() {
                    @Override
                    public void noNetworkConnection() {
                        handleError(getString(R.string.no_internet_connection));
                    }

                    @Override
                    public void failureResponse() {
                        handleError(getString(R.string.something_went_wrong));
                    }

                    @Override
                    public void successResponse(DocumentCategoryResponse documentCategoryResponse) {
                        onSuccessfulDocReceived(documentCategoryResponse);
                    }
                });
    }

    private void onSuccessfulDocReceived(DocumentCategoryResponse docCatResponse) {
        if (docCatResponse != null) {

            if (docCatResponse.getRepositoryList() != null &&
                    !docCatResponse.getRepositoryList().isEmpty()) {

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.repository_recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));

                recyclerView.setAdapter(new RepositoryRecyclerAdapter
                        (docCatResponse.getRepositoryList(), getAdapterListener()));

            } else {
                handleError(getString(R.string.no_data_found));
            }

        } else {
            handleError(getString(R.string.something_went_wrong));
        }
    }

    @NonNull
    private RepositoryAdapterListener getAdapterListener() {
        return new RepositoryAdapterListener() {
            @Override
            public void onDocumentCategoryChosen(Repository repository) {
                if (repository != null) {
                    Bundle args = new Bundle();
                    args.putParcelable(Constants.BundleKeys.DOCUMENT_CATEGORY, Parcels.wrap(repository));

                    Intent intent = new Intent(RepositoryActivity.this, DocumentListActivity.class);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            }
        };
    }

    private void handleError(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = getString(R.string.something_went_wrong);
        }
        Snackbar.make(findViewById(R.id.activity_parent), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doc_cat_fab:
                handleError("Functionality not enabled");
                break;

            case R.id.back_icon:
                finish();
                break;
        }
    }
}
