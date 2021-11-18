package com.alan.monitor1.gis;

import lombok.Data;

import java.util.List;

@Data
public class Point {

    private String idx;
    private List<Point_xy> point;

    @Data
    public static class Point_xy {

        private double point_lat;
        private double point_long;
    }


}