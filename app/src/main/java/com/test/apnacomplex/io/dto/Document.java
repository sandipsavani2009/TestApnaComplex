package com.test.apnacomplex.io.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sc on 8/4/18.
 */

public class Document {

    @SerializedName("doc_name")
    private String docName;

    @SerializedName("doc_size")
    private String docSize;

    @SerializedName("doc_type")
    private String docType;

    @SerializedName("doc_url")
    private String docUrl;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSize() {
        return docSize;
    }

    public void setDocSize(String docSize) {
        this.docSize = docSize;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
}
