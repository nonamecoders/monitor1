package com.alan.monitor1.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderData {

    private String item_cd;                 // 상품코드
    private Integer item_qty;               // 상품수량
    private Double og_price;                // 상품가격
    private Double dc_price;                // 상품할인가격
    private String dc_cd;                   // 할인코드
    private String coupon_id;               // 쿠폰ID

    private Integer group_seq;              // 상품그룹번호
    private String is_set;                  // 세트여부
    private String is_div;                  // 하프앤하프 여부

    // 옵션용
    private String is_option_del;           // 제거 유무


}
