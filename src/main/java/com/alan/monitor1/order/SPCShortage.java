package com.alan.monitor1.order;

import lombok.Data;

@Data
public class SPCShortage {

    private String brand_cd;
    private String item_cd;
    private String branch_id;
    private String shortage_cd;
    private String shortage_start_date;
    private String shortage_end_date;
    private String shortage_start_time;
    private String shortage_end_time;
    private String shortage_memo;

}
