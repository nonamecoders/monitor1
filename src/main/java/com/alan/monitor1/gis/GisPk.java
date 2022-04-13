package com.alan.monitor1.gis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class GisPk implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brand_cd;
    private String idx;
    private String reg_date;

}
