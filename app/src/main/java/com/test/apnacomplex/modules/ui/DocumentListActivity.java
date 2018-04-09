package com.test.apnacomplex.modules.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.test.apnacomplex.R;
import com.test.apnacomplex.common.BaseActivity;
import com.test.apnacomplex.io.dto.Repository;
import com.test.apnacomplex.io.dto.DocumentResponse;
import com.test.apnacomplex.modules.adapter.DocumentListAdapterListener;
import com.test.apnacomplex.modules.adapter.DocumentListRecyclerAdapter;
import com.test.apnacomplex.modules.dataModels.DocumentApiModelImpl;
import com.test.apnacomplex.utils.Constants;

import org.parceler.Parcels;

import java.io.File;

public class DocumentListActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = DocumentListActivity.class.getSimpleName();
    private final int READ_STORAGE_PERMISSION_REQUEST_CODE = 98;
    private final int WRITE_STORAGE_PERMISSION_REQUEST_CODE = 99;

    private Repository mRepository;
    private RecyclerView mDocListRecyclerView;
    private DocumentListRecyclerAdapter mDocListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setShouldAnimateActivity(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_document_list);
        setupToolbar();
        initViews();
        initMembers();
        populateToolbarValues();
        loadDocumentsFromServer();
        checkForWriteStoragePermission();
    }

    private void populateToolbarValues() {
        if (mRepository != null) {
            TextView titleTextView = (TextView) findViewById(R.id.toolbar_doc_name_textView);
            titleTextView.setText(mRepository.getCategoryName());
        }
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.doc_list_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void checkForWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Write Storage permission is granted");
                checkForReadStoragePermission();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE }, WRITE_STORAGE_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void checkForReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Read Storage permission is granted");
            } else {
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE }, READ_STORAGE_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void initMembers() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mRepository = Parcels.unwrap(getIntent().getExtras().
                    getParcelable(Constants.BundleKeys.DOCUMENT_CATEGORY));
        }
    }

    private void initViews() {
        findViewById(R.id.doc_list_fab).setOnClickListener(this);
        findViewById(R.id.back_icon).setOnClickListener(this);
        findViewById(R.id.name_sort_layout).setOnClickListener(this);

        mDocListRecyclerView = (RecyclerView) findViewById(R.id.doc_list_recyclerView);
        mDocListRecyclerView.setHasFixedSize(true);
        mDocListRecyclerView.setLayoutManager(new LinearLayoutManager(mDocListRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false));
    }

    private void loadDocumentsFromServer() {
        if (mRepository != null && mRepository.getCategoryId() != null) {
            new DocumentApiModelImpl().fetchDocumentListFromServer(
                    mRepository.getCategoryId(), getApiListener());
        }
    }

    @NonNull
    private DocumentApiModelImpl.DocumentListApiListener getApiListener() {
        return new DocumentApiModelImpl.DocumentListApiListener() {
            @Override
            public void noNetworkConnection() {
                handleError(getString(R.string.no_internet_connection));
            }

            @Override
            public void failureResponse() {
                handleError("");
            }

            @Override
            public void successResponse(DocumentResponse documentResponse) {
                onDocumentsResponseSuccess(documentResponse);
            }
        };
    }

    private void onDocumentsResponseSuccess(DocumentResponse documentResponse) {
        if (documentResponse != null && documentResponse.getDocumentList() != null) {

             mDocListAdapter = new DocumentListRecyclerAdapter(mDocListRecyclerView.getContext(),
                            documentResponse.getDocumentList(), getAdapterListener());

            mDocListRecyclerView.setAdapter(mDocListAdapter);
        }
    }

    @NonNull
    private DocumentListAdapterListener getAdapterListener() {
        return new DocumentListAdapterListener() {
            @Override
            public void viewFile(String dir, String fileName) {
                Log.d(TAG, "Viewing file");

                File file = new File(dir, fileName);
                if (file.exists()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setType("file/*");
                    intent.setData(Uri.fromFile(file));
                    Intent chooserIntent = Intent.createChooser(intent, "Choose an application to open file");
                    startActivity(chooserIntent);
                }
            }

            @Override
            public void viewPhoto(String dir, String fileName) {
                Intent intent = new Intent(DocumentListActivity.this, ImageViewActivity.class);
                intent.putExtra(Constants.BundleKeys.IMAGE_NAME, fileName);
                startActivity(intent);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case WRITE_STORAGE_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: " + permissions[0] + " was " + grantResults[0]);
                    checkForReadStoragePermission();
                } else {
                    handleError("Write Permission Required");
                    checkForWriteStoragePermission();
                }
                break;

            case READ_STORAGE_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: " + permissions[0] + " was " + grantResults[0]);
                    checkForReadStoragePermission();
                } else {
                    handleError("Read Permission Required");
                    checkForReadStoragePermission();
                }
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doc_list_fab:
                handleError("Document uploads not allowed for this user");
                break;

            case R.id.back_icon:
                finish();
                break;

            case R.id.name_sort_layout:
                onSortOrderChangeClicked();
                break;
        }
    }

    private void onSortOrderChangeClicked() {
        if (mDocListAdapter != null) {
            mDocListAdapter.changeSortingOrder();
        }
    }
}
