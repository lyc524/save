package com.wecanstudio.xdsjs.save.Model;

/**
 * 账单
 * Created by xdsjs on 2015/10/14.
 */
public class Bill {
    private String typeId;
    private String money;
    private String remark;
    private String time;
    private int upload = 0;//0表示未更新，1表示已更新

    public Bill() {
    }

    public Bill(String typeId, String money, String remark, String time, int upload) {
        this.typeId = typeId;
        this.money = money;
        this.remark = remark;
        this.time = time;
        this.upload = upload;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    @Override
    public String toString() {
        return "typeId:--->" + this.getTypeId() + "\n" +
                "price:--->" + this.getMoney();
    }
}
