package com.wecanstudio.xdsjs.save.Model;

/**
 * 账单
 * Created by xdsjs on 2015/10/14.
 */
public class Bill {
    private String type;
    private String money;
    private String remark;
    private String time;
    private int upload = 0;//0表示未更新，1表示已更新

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }
}
