package com.wecanstudio.xdsjs.save.View.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.wecanstudio.xdsjs.save.Model.BillType;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.databinding.GridviewItemBinding;

import java.util.List;

public class ExpressionAdapter extends ArrayAdapter<BillType> {

    private GridviewItemBinding gridviewItemBinding;
    private LayoutInflater inflater;
    private Context context;
    public View.OnClickListener onClickListener;//不要设置成private

    public ExpressionAdapter(Context context, int textViewResourceId, List<BillType> billTypes) {
        super(context, textViewResourceId, billTypes);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            gridviewItemBinding = DataBindingUtil.inflate(inflater, R.layout.gridview_item, parent, false);
            convertView = gridviewItemBinding.getRoot();
            convertView.setTag(gridviewItemBinding);
        } else {
            gridviewItemBinding = (GridviewItemBinding) convertView.getTag();
        }
        gridviewItemBinding.setType(getItem(position).getType());
        int resId = getContext().getResources().getIdentifier("type_" + getItem(position).getType() + "_normal", "drawable", getContext().getPackageName());
        gridviewItemBinding.setImg(context.getResources().getDrawable(resId));
        gridviewItemBinding.setAdapter(this);

        return convertView;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
