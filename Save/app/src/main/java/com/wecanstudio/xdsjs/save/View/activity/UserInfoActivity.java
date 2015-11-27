package com.wecanstudio.xdsjs.save.View.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.View.widget.riseNum.RiseNumberTextView;
import com.wecanstudio.xdsjs.save.ViewModel.UserInfoViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivityUserInfoBinding;

public class UserInfoActivity extends BaseActivity<UserInfoViewModel, ActivityUserInfoBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setViewModel(new UserInfoViewModel());
        setBinding(DataBindingUtil.<ActivityUserInfoBinding>setContentView(this, R.layout.activity_user_info));
        getBinding().setUserInfo(getViewModel());
        initView();
    }

    private void initView() {
        setSupportActionBar(getBinding().toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getBinding().totalBill.withNumber(900);
        getBinding().totalBill.setDuration(1500);
        getBinding().totalBill.start();
        getBinding().totalBill.setOnEndListener(new RiseNumberTextView.EndListener() {
            @Override
            public void onEndFinish() {
                startAnim();
            }
        });
    }

    private void startAnim() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(getBinding().llTwo, "alpha", 0.25f, 1, 1),
                ObjectAnimator.ofFloat(getBinding().llTwo, "scaleX", 0.5f, 1),
                ObjectAnimator.ofFloat(getBinding().llTwo, "scaleY", 0.5f, 1)
        );
        animatorSet.setDuration(1500);
        getBinding().llTwo.setVisibility(View.VISIBLE);
        animatorSet.start();
        getBinding().totalBillIn.withNumber(1500.5f);
        getBinding().totalBillOut.withNumber(2600.8f);
        getBinding().totalBillIn.start();
        getBinding().totalBillIn.setDuration(1500);
        getBinding().totalBillOut.start();
        getBinding().totalBillOut.setDuration(1500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
