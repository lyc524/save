package com.wecanstudio.xdsjs.save.View.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecanstudio.xdsjs.save.Model.Bill;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 时光轴的适配器
 * Created by xdsjs on 2015/10/24.
 */
public class TimeAdapter extends BaseAdapter {

    private Context context;
    private List<Bill> bills = new ArrayList<>();

    public TimeAdapter(Context context, List<Bill> bills) {
        super();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.recycler_timeline_item, null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.tvTypeName = (TextView) convertView.findViewById(R.id.tv_type_name);
            viewHolder.tvRemark = (TextView) convertView.findViewById(R.id.tv_type_remark);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int resId = context.getResources().getIdentifier("type_" + bills.get(position).getTypeId() + "_normal", "drawable", context.getPackageName());
        viewHolder.ivIcon.setImageResource(resId);
        viewHolder.tvDate.setText(TimeUtils.getPrettyTime(Long.valueOf(bills.get(position).getTime())));
        viewHolder.tvTypeName.setText(Global.types[Integer.valueOf(bills.get(position).getTypeId())]);
        if (!TextUtils.isEmpty(bills.get(position).getRemark())) {
            viewHolder.tvRemark.setText(bills.get(position).getRemark());
        } else {
            viewHolder.tvRemark.setVisibility(View.GONE);
        }
        if (bills.get(position).getTypeId().equals("0")) {
            viewHolder.tvMoney.setTextColor(Color.GREEN);
            viewHolder.tvMoney.setText(bills.get(position).getMoney());
        } else {
            viewHolder.tvMoney.setTextColor(Color.RED);
            viewHolder.tvMoney.setText("-" + bills.get(position).getMoney());
        }
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
