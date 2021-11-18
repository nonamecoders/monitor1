package com.alan.monitor1.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mybatis.annotations.Entity;

@Getter
@Setter
@Entity
public class Order {

    private String brand_cd;
    private String brand_nm;
    private String coupon_id;
    private String discount;
    private String total_order;
    private String real_order;
    private String cancel_order;

    private String start_date;
    private String end_date;

}
