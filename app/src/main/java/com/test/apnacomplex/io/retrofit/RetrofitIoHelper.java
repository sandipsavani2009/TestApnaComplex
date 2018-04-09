package com.test.apnacomplex.io.retrofit;

import com.test.apnacomplex.io.dto.DocumentCategoryResponse;
import com.test.apnacomplex.io.dto.DocumentResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sc on 5/4/18.
 */

public class RetrofitIoHelper {

    private static RetrofitIoHelper sInstance;
    private RetrofitInterfaceService mInterfaceService;

    public synchronized static RetrofitIoHelper getInnstance() {
        if (sInstance == null) {
            sInstance = new RetrofitIoHelper();
        }
        return sInstance;
    }

    private RetrofitIoHelper() {
        init();
    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://s3.ap-south-1.amazonaws.com/mobileassignment/repository/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getCustomHttpClient())
                .build();

        mInterfaceService = retrofit.create(RetrofitInterfaceService.class);
    }

    private OkHttpClient getCustomHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }

    public Call<DocumentCategoryResponse> getDocumentCategoryResponse() {
        return mInterfaceService.getDocumentCategory();
    }

    public Call<DocumentResponse> getDocuments(String docCategory) {
        return mInterfaceService.getDocumentList(docCategory);
    }

}
