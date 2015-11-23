package com.wecanstudio.xdsjs.save.View.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.ViewModel.RegistViewModel;
import com.wecanstudio.xdsjs.save.databinding.FragmentRegistBinding;

/**
 * Created by xdsjs on 2015/11/23.
 */
public class RegistFragment extends BaseFragment<RegistViewModel, FragmentRegistBinding> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setViewModel(new RegistViewModel());
        setBinding(DataBindingUtil.<FragmentRegistBinding>inflate(inflater, R.layout.fragment_regist, container, false));
        getBinding().setRegistViewModel(getViewModel());
        return getBinding().getRoot();
    }
}
