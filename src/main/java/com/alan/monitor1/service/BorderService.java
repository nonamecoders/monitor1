package com.alan.monitor1.service;

import com.alan.monitor1.gis.*;
import com.alan.monitor1.mapper.BorderMapper;
import com.alan.monitor1.gis.Border;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("com.alan.monitor1.service.BorderService")
public class BorderService {
    @Resource(name = "com.alan.monitor1.mapper.BorderMapper")
    BorderMapper mBorderMapper;

    public List<Border> borderListService() throws Exception{
        List<Border> borderList = mBorderMapper.borderList();

        return borderList;
    }

    public List<Border> kateckBorderList() throws Exception {

        return mBorderMapper.selectPolyList();
    }

    public MinMaxPoint.CenterPoint createCenterPoint() throws Exception{

        List<Border> borderList = mBorderMapper.selectPolyList();

        ArrayList<Integer> xArray = new ArrayList<>();
        ArrayList<Integer> yArray = new ArrayList<>();
        for(Border border : borderList){
            String poly = border.getPoly();
            String[] pointList = poly.split(",");
            for(String s : pointList){
                String[] pointXY = s.split(" ");

                xArray.add(Integer.parseInt(pointXY[0]));
                yArray.add(Integer.parseInt(pointXY[1]));

            }
        }

        Collections.sort(xArray);
        Collections.sort(yArray);

        int min_x = xArray.get(0);
        int max_x = xArray.get(xArray.size()-1);
        int min_y = yArray.get(0);
        int max_y = yArray.get(yArray.size()-1);

        //중심좌표
        MinMaxPoint minMaxPoint = new MinMaxPoint();
        minMaxPoint.setUtmk_min_x(min_x);
        minMaxPoint.setUtmk_max_x(max_x);
        minMaxPoint.setUtmk_min_y(min_y);
        minMaxPoint.setUtmk_max_y(max_y);

        MinMaxPoint.CenterPoint centerPoint = calculateCenterPoint(minMaxPoint);

        return centerPoint;
    }

    //등록한 상권 조회
    public List<Point> wgs84New() throws Exception{
        List<Border> borderList = mBorderMapper.selectPolyList();
        List<Point> polyListNew = katech_to_wgs_84(borderList);

        return polyListNew;
    }

    public List<Point> wgs84Old() throws Exception{
        List<Border> borderList2 = mBorderMapper.selectPolyList2();
        List<Point> polyListNew = katech_to_wgs_84(borderList2);

        return polyListNew;
    }

    public List<Point> katech_to_wgs_84(List<Border> borderList){

        List<Point> pointList = new ArrayList<>();

        for(Border border : borderList){

            Point point = new Point();
            point.setIdx(border.getIdx());
            String [] point_x_y = border.getPoly().split(",");
            List<Point.Point_xy> point_xyList = new ArrayList<>();

            for(String str : point_x_y){
                String[] utmk_point = str.split(" ");

                double point_x = Double.parseDouble(utmk_point[0]);
                double point_y = Double.parseDouble(utmk_point[1]);

                GeoPoint in_pt = new GeoPoint(point_x,point_y);
                GeoPoint utmk_to_wgs = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, in_pt);
                double wgs84_x = utmk_to_wgs.getX();
                double wgs84_y = utmk_to_wgs.getY();

                Point.Point_xy point_xy= new Point.Point_xy();
                point_xy.setPoint_long(wgs84_x);
                point_xy.setPoint_lat(wgs84_y);

                point_xyList.add(point_xy);

            }

            point.setPoint(point_xyList);
            pointList.add(point);

        }

        return pointList;
    }

    /**
     * 최소 최대 포인트 구하기
     * @param minMaxPoint
     * @return
     */
    private MinMaxPoint.CenterPoint calculateCenterPoint(MinMaxPoint minMaxPoint){

        double max_x = minMaxPoint.getUtmk_max_x();
        double min_x = minMaxPoint.getUtmk_min_x();
        double max_y = minMaxPoint.getUtmk_max_y();
        double min_y = minMaxPoint.getUtmk_min_y();
        double center_x = 0;
        double center_y = 0;

        center_x = min_x + ((max_x - min_x) / 2);
        center_y = min_y + ((max_y - min_y) / 2);

        GeoPoint in_pt = new GeoPoint(center_x,center_y);
        GeoPoint katech_to_wgs = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, in_pt);

        MinMaxPoint.CenterPoint centerPoint = new MinMaxPoint.CenterPoint();
        centerPoint.setCenter_long(katech_to_wgs.getX());
        centerPoint.setCenter_lat(katech_to_wgs.getY());

        return centerPoint;

    }

}
