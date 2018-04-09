package com.test.apnacomplex.modules.dataModels;

import com.test.apnacomplex.ApnaComplexApplication;
import com.test.apnacomplex.io.dto.DocumentCategoryResponse;
import com.test.apnacomplex.io.dto.DocumentResponse;
import com.test.apnacomplex.io.retrofit.RetrofitIoHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sc on 5/4/18.
 */

public class DocumentApiModelImpl {

    public void fetchDocumentListFromServer(String docCategory,
            final DocumentListApiListener apiListener) {

        if (apiListener != null) {
            if (ApnaComplexApplication.getInstance().hasNetworkConnection()) {

                RetrofitIoHelper.getInnstance().getDocuments(docCategory)
                        .enqueue(new Callback<DocumentResponse>() {
                            @Override
                            public void onResponse(Call<DocumentResponse> call,
                                                   Response<DocumentResponse> response) {

                                if (response != null && response.isSuccessful()) {

                                    apiListener.successResponse(response.body());
                                } else {
                                    apiListener.failureResponse();
                                }
                            }

                            @Override
                            public void onFailure(Call<DocumentResponse> call, Throwable t) {
                                apiListener.failureResponse();
                            }
                        });

            } else {
                apiListener.noNetworkConnection();
            }
        }
    }

    public interface DocumentListApiListener {
        void noNetworkConnection();
        void failureResponse();
        void successResponse(DocumentResponse documentResponse);
    }
}
