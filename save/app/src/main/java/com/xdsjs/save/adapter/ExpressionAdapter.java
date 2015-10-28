/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xdsjs.save.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdsjs.save.R;
import com.xdsjs.save.bean.BillType;


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
        if (getItem(position).isPressed()){
            int resId = getContext().getResources().getIdentifier("type_" + getItem(position).getType()+"_press", "drawable", getContext().getPackageName());

            ivTypeAvatar.setImageResource(resId);
        }else {
            int resId = getContext().getResources().getIdentifier("type_" + getItem(position).getType()+"_normal", "drawable", getContext().getPackageName());

            ivTypeAvatar.setImageResource(resId);
        }

        tvTypeName.setText(getItem(position).getName());

        return convertView;
    }

}
