package com.test.apnacomplex.modules.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.apnacomplex.R;
import com.test.apnacomplex.modules.dto.HomeGridItemDto;

import java.util.List;

/**
 * Created by sc on 5/4/18.
 */

public class HomeGridAdapter extends BaseAdapter {

    private List<HomeGridItemDto> mHomeGridItemList;
    private Context mContext;

    public HomeGridAdapter(Context context, List<HomeGridItemDto> homeGridItemDtos) {
        mContext = context;
        mHomeGridItemList = homeGridItemDtos;
    }

    @Override
    public int getCount() {
        return (mHomeGridItemList != null) ? mHomeGridItemList.size() : 0;
    }

    @Override
    public HomeGridItemDto getItem(int position) {
        if (mHomeGridItemList != null && position >= 0 && position < mHomeGridItemList.size()) {
            return mHomeGridItemList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mHomeGridItemList != null && position >= 0 && position < mHomeGridItemList.size()) {
            return mHomeGridItemList.get(position).getId();
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder gridViewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_grid_list_item, null);
            gridViewHolder = new GridViewHolder(convertView);
            convertView.setTag(gridViewHolder);
        } else {
            gridViewHolder = (GridViewHolder) convertView.getTag();
        }

        HomeGridItemDto homeGridItemDto = getItem(position);
        if (homeGridItemDto != null) {
            gridViewHolder.documentImageView.setImageResource(homeGridItemDto.getBgImage());
            gridViewHolder.documentLabelTextView.setText(homeGridItemDto.getLable());
        }

        return convertView;
    }

    public class GridViewHolder {
        ImageView documentImageView;
        TextView documentLabelTextView;

        GridViewHolder(View view) {
            documentImageView = (ImageView) view.findViewById(R.id.home_grid_item_imageView);
            documentLabelTextView = (TextView) view.findViewById(R.id.home_grid_item_label_imageView);
        }
    }
}
