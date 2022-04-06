package com.alan.monitor1.gis;

import lombok.Data;

@Data
public class TransXY {

    String idx;
    String ord;
    double origin_x;
    double origin_y;
    double trans_x;
    double trans_y;

    public TransXY(String idx, String ord, double origin_x, double origin_y) {
        this.idx = idx;
        this.ord = ord;
        this.origin_x = origin_x;
        this.origin_y = origin_y;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public double getOrigin_x() {
        return origin_x;
    }

    public void setOrigin_x(double origin_x) {
        this.origin_x = origin_x;
    }

    public double getOrigin_y() {
        return origin_y;
    }

    public void setOrigin_y(double origin_y) {
        this.origin_y = origin_y;
    }

    public double getTrans_x() {
        return trans_x;
    }

    public void setTrans_x(double trans_x) {
        this.trans_x = trans_x;
    }

    public double getTrans_y() {
        return trans_y;
    }

    public void setTrans_y(double trans_y) {
        this.trans_y = trans_y;
    }



}
