package com.test.apnacomplex.modules.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.apnacomplex.R;
import com.test.apnacomplex.io.dto.Document;
import com.test.apnacomplex.modules.dataModels.FileDownloadTaskListener;
import com.test.apnacomplex.modules.dataModels.FileDownloadTask;
import com.test.apnacomplex.utils.BitmapMemoryLruCache;
import com.test.apnacomplex.utils.Constants;
import com.test.apnacomplex.utils.FileUtil;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sc on 8/4/18.
 */

public class DocumentListRecyclerAdapter extends RecyclerView.Adapter<DocumentListRecyclerAdapter.DocumentListHolder> {

    private final String TAG = DocumentListRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private List<Document> mDocumentList;
    private BitmapMemoryLruCache mImageCache;
    private DocumentListAdapterListener mAdapterListener;
    private boolean mIsDescOrder = false;

    public DocumentListRecyclerAdapter(Context ctx, List<Document> documents,
                                       DocumentListAdapterListener adapterListener) {
        mImageCache = new BitmapMemoryLruCache();
        mContext = ctx;
        mDocumentList = documents;
        mAdapterListener = adapterListener;
    }

    @Override
    public DocumentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.document_list_item, parent, false);
        return new DocumentListHolder(view);
    }

    @Override
    public void onBindViewHolder(final DocumentListHolder holder, int position) {
        if (mDocumentList != null && position < mDocumentList.size()) {

            final Document document = mDocumentList.get(position);
            if (document != null) {

                if (!TextUtils.isEmpty(document.getDocUrl())) {
                    if (document.getDocType().equalsIgnoreCase(Constants.DocType.JPG)) {
                        if (mImageCache.contains(document.getDocUrl())) {
                            holder.docImageView.setImageBitmap(mImageCache.getBitmap(document.getDocUrl()));
                        } else {
                            Glide.with(mContext).load(document.getDocUrl()).into(holder.docImageView);
                        }
                    }
                }

                holder.docNameTextView.setText(document.getDocName());
                holder.docSizeTextView.setText("File Size " + document.getDocSize());

                holder.docOptionsMenuTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onViewDocClicked(holder, document);
                    }
                });
            }
        }
    }

    private void onFileOpenClicked(final Document document) {
        final String fileNameWithExt = FileUtil.getFileNameFromUrl(document.getDocUrl());
        File file = new File(FileUtil.getDirPath(), fileNameWithExt);

        if (file.exists()) {
            openFileChooser(document, fileNameWithExt);
        } else {
            new FileDownloadTask(document.getDocUrl(), new FileDownloadTaskListener() {
                @Override
                public void onDownloadSuccess() {
                    openFileChooser(document, fileNameWithExt);
                }
            }).execute();
        }
    }

    private void openFileChooser(Document document, String fileNameWithExt) {
        if (document.getDocType().equalsIgnoreCase(Constants.DocType.JPG)) {
            if (mAdapterListener != null) {
                mAdapterListener.viewPhoto(FileUtil.getDirPath(), fileNameWithExt);
            }
        } else {
            if (mAdapterListener != null) {
                mAdapterListener.viewFile(FileUtil.getDirPath(), fileNameWithExt);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mDocumentList != null) ? mDocumentList.size() : 0;
    }


    public class DocumentListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout parent;
        ImageView docImageView;
        TextView docNameTextView;
        TextView docSizeTextView;
        TextView docOptionsMenuTextView;

        public DocumentListHolder(View itemView) {
            super(itemView);

            parent = (LinearLayout) itemView.findViewById(R.id.doc_list_item_parent);
            docImageView = (ImageView) itemView.findViewById(R.id.doc_imgView);
            docNameTextView = (TextView) itemView.findViewById(R.id.doc_name_textView);
            docSizeTextView = (TextView) itemView.findViewById(R.id.doc_size_textView);
            docOptionsMenuTextView = (TextView) itemView.findViewById(R.id.doc_view_options_textView);

            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.doc_list_item_parent:
                    onFileOpenClicked(getItem(getAdapterPosition()));
                    break;
            }

        }
    }

    private Document getItem(int position) {
        if (mDocumentList != null && position < mDocumentList.size()) {
            return mDocumentList.get(position);
        }
        return null;
    }

    private void onViewDocClicked(final DocumentListHolder holder, final Document document) {
        PopupMenu popup = new PopupMenu(mContext, holder.docOptionsMenuTextView);
        popup.inflate(R.menu.doc_list_options_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_view_doc:
                        onFileOpenClicked(document);
                        break;
                }

                return false;
            }
        });
        popup.show();
    }

    public void changeSortingOrder() {
        if (mDocumentList != null) {
            mIsDescOrder = !mIsDescOrder;   // Changes Order

            Collections.sort(mDocumentList, new Comparator<Document>() {

                @Override
                public int compare(Document doc1, Document doc2) {
                    if (mIsDescOrder) {
                        return doc2.getDocName().compareTo(doc1.getDocName());
                    } else {
                        return doc1.getDocName().compareTo(doc2.getDocName());
                    }
                }
            });

            notifyDataSetChanged();
        }
    }
}
