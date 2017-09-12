package com.example.administrator.addressselectordemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.addressselectordemo.R;

public class City1ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mxList;

    private int clickTemp = -1;

    // 标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    public City1ListViewAdapter(Context mContext, List<String> mxList) {
        super();
        this.mxList = mxList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mxList == null) {
            return 0;
        } else {
            return this.mxList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mxList == null) {
            return null;
        } else {
            return this.mxList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.new_activity_selectcitys_listview, parent,
                    false);

            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ll_name = (LinearLayout) convertView.findViewById(R.id.ll_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.mxList != null && !this.mxList.isEmpty()) {
            String objClass = this.mxList.get(position);

            if (objClass != null) {
                if (clickTemp == position) {
                    holder.ll_name.setBackgroundColor(0xfff4f3fa);
                    holder.tv_name.setTextAppearance(mContext, R.style.typeface_style_14_5f97ff);
                } else {
                    holder.ll_name.setBackgroundColor(0xffffffff);
                    holder.tv_name.setTextAppearance(mContext, R.style.typeface_style_14_666666);
                }

                holder.tv_name.setText(objClass);
                holder.tv_id.setText(objClass);

            }
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tv_id;
        LinearLayout ll_name;
        TextView tv_name;
    }
}
