package com.xdsjs.save.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdsjs.save.R;
import com.xdsjs.save.bean.Bill;
import com.xdsjs.save.config.Global;

import java.util.List;

/**
 * 时光轴的适配器
 * Created by xdsjs on 2015/10/24.
 */
public class TimelineAdapter extends BaseAdapter {

    private Context context;
    private List<Bill> bills;

    public TimelineAdapter(Context context, List<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int position) {
        return bills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_timeline_item, null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.tvTypeName = (TextView) convertView.findViewById(R.id.tv_type_name);
            viewHolder.tvRemark = (TextView) convertView.findViewById(R.id.tv_type_remark);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int resId = context.getResources().getIdentifier("type_" + bills.get(position).getType(), "drawable", context.getPackageName());
        viewHolder.ivIcon.setImageResource(resId);
        viewHolder.tvDate.setText(bills.get(position).getTime());
        viewHolder.tvTypeName.setText(Global.types[Integer.valueOf(bills.get(position).getType())]);
        if (!bills.get(position).getRemark().equals(null)) {
            viewHolder.tvRemark.setText(bills.get(position).getRemark());
        } else {
            viewHolder.tvRemark.setVisibility(View.GONE);
        }
        viewHolder.tvMoney.setText(bills.get(position).getMoney());
        return convertView;
    }

    public class ViewHolder {
        public ImageView ivIcon;
        private TextView tvTypeName;
        private TextView tvRemark;
        private TextView tvDate;
        private TextView tvMoney;
    }
}
