package com.alan.monitor1.gis;

import lombok.Data;

@Data
public class gis {

    String brand_cd;
    String idx;
    String poly;
    int is_valid;
    String reg_id;
    String reg_date;
    String reg_time;
    String upd_id;
    String upd_date;
    String upd_time;

    public gis(String brand_cd, String idx, String poly, int is_valid, String reg_id, String reg_date, String reg_time,
               String upd_id, String upd_date, String upd_time) {
        super();
        this.brand_cd = brand_cd;
        this.idx = idx;
        this.poly = poly;
        this.is_valid = is_valid;
        this.reg_id = reg_id;
        this.reg_date = reg_date;
        this.reg_time = reg_time;
        this.upd_id = upd_id;
        this.upd_date = upd_date;
        this.upd_time = upd_time;
    }

}