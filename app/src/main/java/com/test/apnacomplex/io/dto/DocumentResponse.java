package com.test.apnacomplex.io.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sc on 8/4/18.
 */

public class DocumentResponse {

    @SerializedName("documents")
    private List<Document> documentList;

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }
}
