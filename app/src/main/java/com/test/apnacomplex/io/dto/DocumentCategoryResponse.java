package com.test.apnacomplex.io.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sc on 5/4/18.
 */

public class DocumentCategoryResponse {

    @SerializedName("DocumentCategories")
    private List<Repository> repositoryList;

    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }
}
