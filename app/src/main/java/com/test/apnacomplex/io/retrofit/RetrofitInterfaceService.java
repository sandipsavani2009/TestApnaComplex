package com.test.apnacomplex.io.retrofit;

import com.test.apnacomplex.io.dto.DocumentCategoryResponse;
import com.test.apnacomplex.io.dto.DocumentResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by sc on 5/4/18.
 */

public interface RetrofitInterfaceService {

    @GET("doc_categories")
    Call<DocumentCategoryResponse> getDocumentCategory();

    @GET("docs_list/{doc_cat}")
    Call<DocumentResponse> getDocumentList(@Path(value = "doc_cat", encoded = true) String docCategory);

}
