package com.wecanstudio.xdsjs.save.View.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecanstudio.xdsjs.save.Model.BillType;
import com.wecanstudio.xdsjs.save.R;

import java.util.List;

public class ExpressionAdapter extends ArrayAdapter<BillType> {

    public ExpressionAdapter(Context context, int textViewResourceId, List<BillType> billTypes) {
        super(context, textViewResourceId, billTypes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.gridview_item, null);
        }

        ImageView ivTypeAvatar = (ImageView) convertView.findViewById(R.id.iv_type_avatar);
        TextView tvTypeName = (TextView) convertView.findViewById(R.id.tv_type_name);

        String typeName = getItem(position).getName();
        if (getItem(position).isPressed()) {
            int resId = getContext().getResources().getIdentifier("type_" + getItem(position).getType() + "_press", "drawable", getContext().getPackageName());

            ivTypeAvatar.setImageResource(resId);
        } else {
            int resId = getContext().getResources().getIdentifier("type_" + getItem(position).getType() + "_normal", "drawable", getContext().getPackageName());

            ivTypeAvatar.setImageResource(resId);
        }

        tvTypeName.setText(getItem(position).getName());

        return convertView;
    }

}
