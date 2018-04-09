package com.test.apnacomplex.modules.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.apnacomplex.R;
import com.test.apnacomplex.io.dto.Repository;

import java.util.List;

/**
 * Created by sc on 5/4/18.
 */

public class RepositoryRecyclerAdapter extends RecyclerView.Adapter<RepositoryRecyclerAdapter.RepositoryViewHolder> {

    private List<Repository> mDocCategoryList;
    private RepositoryAdapterListener mAdapterListener;

    public RepositoryRecyclerAdapter(List<Repository> documentCategories,
                                     RepositoryAdapterListener listener) {
        mDocCategoryList = documentCategories;
        mAdapterListener = listener;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.doc_category_list_item, parent, false);
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        if (mDocCategoryList != null && position >= 0 && position < mDocCategoryList.size()) {
            Repository repository = mDocCategoryList.get(position);

            if (repository != null) {
                holder.parent.setBackgroundResource(getDrawableId(holder.parent.getContext(), repository.getCategryBackgroundImage()));
                holder.iconImageView.setImageResource(getDrawableId(holder.iconImageView.getContext(), repository.getCategoryIcon()));
                holder.categoryNameTextView.setText(repository.getCategoryName());
                holder.countNumnerTextView.setText("(" + repository.getNumberDoc() + ")");
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mDocCategoryList != null) ? mDocCategoryList.size() : 0;
    }

    /**
     *
     * @param context
     * @param name - name of drawable available in apk
     * @return id of drawable-resource
     */
    private int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(
                name.split("\\.")[0],
                "drawable", context.getPackageName());
    }

    /**
     * View Holder for Repository
     */
    class RepositoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout parent;
        ImageView iconImageView;
        TextView categoryNameTextView;
        TextView countNumnerTextView;

        public RepositoryViewHolder(View itemView) {
            super(itemView);

            parent = (LinearLayout) itemView.findViewById(R.id.doc_cat_parent);
            iconImageView = (ImageView) itemView.findViewById(R.id.doc_cat_icn_imgView);
            categoryNameTextView = (TextView) itemView.findViewById(R.id.doc_cat_name_textView);
            countNumnerTextView = (TextView) itemView.findViewById(R.id.doc_cat_count_number_textView);

            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.doc_cat_parent:
                    onCategoryClicked(getAdapterPosition());
                    break;
            }
        }
    }

    private void onCategoryClicked(int position) {
        if (mDocCategoryList != null && position < mDocCategoryList.size() &&
                mAdapterListener != null) {
            mAdapterListener.onDocumentCategoryChosen(mDocCategoryList.get(position));
        }
    }
}
