package com.test.apnacomplex.modules.dataModels;

import com.test.apnacomplex.ApnaComplexApplication;
import com.test.apnacomplex.io.dto.DocumentCategoryResponse;
import com.test.apnacomplex.io.retrofit.RetrofitIoHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sc on 5/4/18.
 */

public class DocumentCategoryApiModelImpl {

    public void fetchDocumentCategoryListFromServer(final DocumentCategoryApiListener apiListener) {
        if (apiListener != null) {
            if (ApnaComplexApplication.getInstance().hasNetworkConnection()) {

                RetrofitIoHelper.getInnstance().getDocumentCategoryResponse()
                        .enqueue(new Callback<DocumentCategoryResponse>() {
                    @Override
                    public void onResponse(Call<DocumentCategoryResponse> call,
                                           Response<DocumentCategoryResponse> response) {

                        if (response != null && response.isSuccessful()) {

                            apiListener.successResponse(response.body());

                        } else {
                            apiListener.failureResponse();
                        }
                    }

                    @Override
                    public void onFailure(Call<DocumentCategoryResponse> call, Throwable t) {
                        apiListener.failureResponse();
                    }
                });

            } else {
                apiListener.noNetworkConnection();
            }
        }
    }

    public interface DocumentCategoryApiListener {
        void noNetworkConnection();
        void failureResponse();
        void successResponse(DocumentCategoryResponse documentCategoryResponse);
    }
}
