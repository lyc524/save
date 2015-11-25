package com.wecanstudio.xdsjs.save.ViewModel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wecanstudio.xdsjs.save.Model.BillType;
import com.wecanstudio.xdsjs.save.Model.BillTypeList;
import com.wecanstudio.xdsjs.save.Model.MaxTypeResponse;
import com.wecanstudio.xdsjs.save.Model.UserInfo;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.Model.db.TimeDao;
import com.wecanstudio.xdsjs.save.Model.net.RestApi;
import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.Utils.ResourceIdUtils;
import com.wecanstudio.xdsjs.save.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.wecanstudio.xdsjs.save.R;

/**
 * Created by xdsjs on 2015/11/18.
 */
public class MainPageViewModel extends LoadingViewModel {

    private static final String TAG = "**MainPageViewModel**";

    private StringBuffer totalBuffer = new StringBuffer();
    private StringBuffer stringBuffer = new StringBuffer();
    public final ObservableField<String> totalMoney = new ObservableField<>();
    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableBoolean isOneDialChoosed = new ObservableBoolean();
    public final ObservableBoolean isTwoDialChoosed = new ObservableBoolean();
    public final ObservableBoolean isThreeDialChoosed = new ObservableBoolean();
    public final ObservableField<Drawable> defaultChooseType = new ObservableField<>();//默认选择的记账类型
    public final ObservableField<Drawable> avatar = new ObservableField<>();//头像

    /*
    初始化操作
     */
    public void onInit() {
        total.set("0.0");
        totalMoney.set("0.0");
        defaultChooseType.set(appContext.getResources().getDrawable(R.drawable.type_1));
        avatar.set(appContext.getResources().getDrawable(R.drawable.delete_press));
        //获取个人信息
        MyApplication.getInstance().createApi(RestApi.class)
                .getUserInfo((String) SPUtils.get(appContext, Global.SHARE_PERSINAL_TOKEN, "123"), (String) SPUtils.get(appContext, Global.SHARE_PERSONAL_ACCOUNT, ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        SPUtils.put(appContext, Global.SHARE_PERSONAL_AVATAR, userInfo.getImgurl());
                        Log.e("UserInfoViewModel", "头像地址" + userInfo.getImgurl());
                        avatar.set(appContext.getResources().getDrawable(R.drawable.remark));
                    }
                });
    }

    @Command
    public void onLeftBarClicked(View view) {
        if ((boolean) SPUtils.get(MyApplication.getContext(), Global.SHARE_PERSONAL_AUTO_LOGIN, false)) {
            //跳转到个人信息界面
        } else {
            //跳转到登录注册界面
        }
    }

    @Command
    public void onRightBarClicked(View view) {

    }

    @Command
    public void onNumKeyClicked(View view) {
        String tag = view.getTag().toString();
        if ((tag.equals("0") || tag.equals(".") || tag.equals("+")) && stringBuffer.length() == 0)
            return;
        if (stringBuffer.length() != 0 && tag.equals("+")) {
            if (stringBuffer.charAt(stringBuffer.length() - 1) == '+') {
                return;
            } else {
                stringBuffer = new StringBuffer();
            }
        }
        if (stringBuffer.length() != 0 && tag.equals(".")) {
            if (stringBuffer.charAt(stringBuffer.length() - 1) == '.') {
                return;
            }
        }
        stringBuffer.append(tag);
        totalBuffer.append(tag);
        total.set(totalBuffer.toString());
        totalMoney.set(calculate(totalBuffer));
    }

    @Command
    public void onDeleteKeyClicked(View view) {
        if (totalBuffer.length() == 0)
            return;
        if (stringBuffer.length() != 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        totalBuffer.deleteCharAt(totalBuffer.length() - 1);
        totalMoney.set(totalBuffer.length() == 0 ? "0.0" : calculate(totalBuffer));
        total.set(totalBuffer.length() == 0 ? "0.0" : totalBuffer.toString());
    }

    @Command
    public void onClearKeyClicked(View view) {
        totalBuffer = new StringBuffer();
        stringBuffer = new StringBuffer();
        total.set("0.0");
        totalMoney.set("0.0");
    }

    @Command
    public void onPlusKeyClicked(View view) {
        String tag = view.getTag().toString();
        if (tag.equals("+") && totalBuffer.length() == 0)
            return;
        totalBuffer.append(tag);
    }

    @Command
    public void onSubmitKeyClicked(View view) {

    }

    @Override
    public View.OnClickListener onRetryClick() {
        return null;
    }

    private String calculate(StringBuffer stringBuffer) {
        if (!stringBuffer.toString().contains("+"))
            return stringBuffer.toString();
        String num = new String(stringBuffer);
        String[] nums = num.split("\\+");
        float total = 0f;
        for (int i = 0; i < nums.length; i++) {
            total += Float.valueOf(nums[i]);
        }
        return String.valueOf(total);
    }


    /*
    确定点的状态
     */
    public void setCurDial(int position) {
        switch (position) {
            case 0:
                isOneDialChoosed.set(true);
                isTwoDialChoosed.set(false);
                isThreeDialChoosed.set(false);
                break;
            case 1:
                isOneDialChoosed.set(false);
                isTwoDialChoosed.set(true);
                isThreeDialChoosed.set(false);
                break;
            case 2:
                isOneDialChoosed.set(false);
                isTwoDialChoosed.set(false);
                isThreeDialChoosed.set(true);
                break;
        }
    }

    /*
    添加viewPager监听器
     */
    public ViewPager.OnPageChangeListener getOnPagerChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurDial(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    /*
    添加gridView监听器
     */
    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
    }

    /*
    处理类型选择动画
     */
    public void startAnimator() {

    }


    private List<BillType> billTypes;

    /*
    本地数据库获取所有记账类型
     */
    public List<BillType> getBillTypeListFromDB() {
        TimeDao timeDao = new TimeDao(MyApplication.getContext());
        billTypes = timeDao.getBillTypeList();
        return billTypes;
    }

    /*
    设置默认的记账类型（即预判得到的）
     */
    public void setDefaultType() {
        List<BillType> newbillTypes = new ArrayList<>();
        if (billTypes != null) {
            newbillTypes = billTypes;
            Collections.sort(newbillTypes, new Comparator<BillType>() {
                @Override
                public int compare(BillType lhs, BillType rhs) {
                    return lhs.getTime() - lhs.getTime();
                }
            });
        }
        final BillType billType = newbillTypes.get(0);
        refresh(billType);
        List<BillTypeList.DataEntity> dataEntities = new ArrayList<>();
        BillTypeList billTypeList = new BillTypeList();
        BillTypeList.DataEntity dataEntity;
        for (BillType billType1 : billTypes) {
            dataEntity = new BillTypeList.DataEntity();
            dataEntity.setChoose(billType.getTime());
            dataEntity.setTime(TimeUtils.getCurrentTime());
            dataEntity.setType_id(billType.getTypeId());
            dataEntities.add(dataEntity);
        }
        billTypeList.setData(dataEntities);
        //服务器请求获取预判的类型并更新
        MyApplication.getInstance().createApi(RestApi.class)
                .getMaxType("token", billTypeList)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MaxTypeResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "服务器获取预判类型完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "服务器获取预判类型失败");
                    }

                    @Override
                    public void onNext(MaxTypeResponse maxTypeResponse) {
                        if (maxTypeResponse.getStatus().equals("1")) {
                            refresh(billTypes.get(maxTypeResponse.getType()));
                        }
                    }
                });
    }

    private void refresh(BillType billType) {
        int resId = ResourceIdUtils.getIdOfResource("type_" + billType.getTypeId() + "_normal", "drawable");
        defaultChooseType.set(appContext.getResources().getDrawable(resId));
    }
}
