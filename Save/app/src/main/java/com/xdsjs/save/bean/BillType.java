package com.xdsjs.save.bean;

/**
 * 表示每一种记账种类
 * Created by xdsjs on 2015/10/20.
 */
public class BillType {
    private String type;//记账种类
    private int time;//对应次数

    private float weight = 0;//对应权重
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name:" + this.getName() + "type:" + this.getType() + "time:" + this.getTime() + "weight:" + this.getWeight();
    }
}
