package com.alan.monitor1.gis;

import lombok.Data;

@Data
public class MinMaxPoint {

    private int utmk_max_x;
    private int utmk_min_x;
    private int utmk_max_y;
    private int utmk_min_y;

    @Data
    public static class CenterPoint {
        private double center_long;
        private double center_lat;

    }

}
