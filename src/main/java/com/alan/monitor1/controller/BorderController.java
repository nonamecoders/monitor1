package com.alan.monitor1.controller;

import com.alan.monitor1.gis.GeoPoint;
import com.alan.monitor1.gis.GeoTrans;
import com.alan.monitor1.gis.MinMaxPoint;
import com.alan.monitor1.gis.Point;
import com.alan.monitor1.order.Border;
import com.alan.monitor1.service.BorderService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BorderController {
    @Resource(name="com.alan.monitor1.service.BorderService")
    BorderService mBorderService;

    @RequestMapping("/borderList") //게시판 리스트 화면 호출
    private String borderList(Model model) throws Exception{

        model.addAttribute("list",mBorderService.borderListService());
        return "borderList";
    }

//    @RequestMapping(name="/uploadExcel", method = RequestMethod.POST)
//    private String uploadExcel(Model model,HttpServletRequest request){
//
//
//
//        return "";
//    }


    @RequestMapping("/polyList") //게시판 리스트 화면 호출
    private String polyList(Model model) throws Exception{

        //좌표리스트(katech)
        List<Border> borderList = mBorderService.selectPolyListService();

        List<Border> borderList2 = mBorderService.selectPolyListService2();

        //중심좌표
        MinMaxPoint minMaxPoint = mBorderService.selectMinMaxPoint();
        MinMaxPoint.CenterPoint centerPoint =  caculateCenterPoint(minMaxPoint);
        model.addAttribute("center",centerPoint);

        //katech => wgs84
//        List<Point> pointList = katech_to_wgs_84(borderList);
//        List<Point> pointList2 = katech_to_wgs_84(borderList2);
//        System.out.println("**********************************************");
//        System.out.println("pointList : " + pointList);
//        System.out.println("**********************************************");
//        List<Border> pizzahut_wgs4 = new ArrayList<>();
//
//        // wgs84 => katech  (pointList = wgs84)
//        if(!pointList.isEmpty()){
//
//            Border border;
//            for(Point point : pointList){
//
//                border = new Border();
//                border.setBrand_cd("PIZZAHUT");
//                border.setIdx(point.getIdx());
//                border.setIs_valid("1");
//
//                String poly ="";
//
//                int cnt_point =0;
//                for(Point.Point_xy point_xy : point.getPoint()){
//                    String lat_wgs84 =  String.valueOf(point_xy.getPoint_lat());
//                    String long_wgs84 =  String.valueOf(point_xy.getPoint_long());
//
//                    cnt_point++;
//
//                    if (cnt_point != point.getPoint().size())
//                        poly += long_wgs84 + " " + lat_wgs84 + ",";
//                    else {
//                        poly += long_wgs84 + " " + lat_wgs84;
//                    }
//
//
//                }
//                border.setPoly(poly);
//                pizzahut_wgs4.add(border);
//            }
//
//            System.out.println(pizzahut_wgs4);
//
//
//            Border border;
//            for(Point point : pointList){
//
//                border = new Border();
//                String poly ="";
//                border.setBrand_cd("PIZZAHUT");
//                border.setIs_valid("1");
//                String idx_wgs84 = point.getIdx();
//                border.setIdx(idx_wgs84);
//                System.out.println("**********************************************");
//                System.out.println("idx_wgs84 : " + idx_wgs84);
//                System.out.println("**********************************************");
//
//                int cnt_point =0;
//                for(Point.Point_xy point_xy : point.getPoint()){
//                    System.out.println(point.getPoint().size());
//
//                    double lat_wgs84 = point_xy.getPoint_lat();
//                    double long_wgs84 = point_xy.getPoint_long();
//
//                    //카카오 변환
//
//                    //소스 내 변환
//                    GeoPoint in_pt = new GeoPoint(long_wgs84,lat_wgs84);
//                    GeoPoint wgs_to_katech = GeoTrans.convert(GeoTrans.GEO, GeoTrans.KATEC, in_pt);
//
//
//                    String katech_x = String.valueOf(wgs_to_katech.getX());
//                    String katech_y = String.valueOf(wgs_to_katech.getY());
//
//
//                    System.out.println("**********************************************");
//                    System.out.println("katech_x : " + katech_x);
//                    System.out.println("katech_y : " + katech_y);
//                    System.out.println("**********************************************");
//
//                    cnt_point++;
//
//                    if (cnt_point != point.getPoint().size())
//                        poly += katech_x + " " + katech_y + ",";
//                    else {
//                        poly += katech_x + " " + katech_y;
//                    }
//                    border.setPoly(poly);
//
//                    System.out.println( "cnt_point : " +cnt_point);
//
//                }
//                System.out.println(border);
//                System.out.println("poly : "+ poly);
//                border.setPoly(poly);
//
//                pizzahut_wgs4.add(border);
//
//            }
//        }

        //기존
//        List<Point> pizzahut = katech_to_wgs_84(pizzahut_wgs4);
//
//        //카카오
//        List<Point> pizzahut_kakao = wgs84_to_katech_kakao(pizzahut_wgs4);



//        model.addAttribute("poly",pointList);
//        model.addAttribute("poly2",pointList2);
        model.addAttribute("list",borderList);
//        model.addAttribute("pizzahut",pizzahut);
        return "polyList";
    }


    /**
     * 최소 최대 포인트 구하기
     * @param minMaxPoint
     * @return
     */
    private MinMaxPoint.CenterPoint caculateCenterPoint(MinMaxPoint minMaxPoint){
        double max_x = minMaxPoint.getUtmk_max_x();
        double min_x = minMaxPoint.getUtmk_min_x();
        double max_y = minMaxPoint.getUtmk_max_y();
        double min_y = minMaxPoint.getUtmk_min_y();
        double center_x=0;
        double center_y=0;

        //


        center_x = min_x + ((max_x - min_x) / 2);
        center_y = min_y + ((max_y - min_y) / 2);


        GeoPoint in_pt = new GeoPoint(center_x,center_y);
        GeoPoint katech_to_wgs = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, in_pt);

        MinMaxPoint.CenterPoint centerPoint = new MinMaxPoint.CenterPoint();
        centerPoint.setCenter_long(katech_to_wgs.getX());
        centerPoint.setCenter_lat(katech_to_wgs.getY());
        return centerPoint;

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

    public List<Point> wgs84_to_katech_kakao(List<Border> list){


        List<Point> pointList = new ArrayList<>();

        for(Border border : list) {

            String origin_poly =border.getPoly();
            String polygon_wgs84 = "POLYGON((" + origin_poly +"))";


            System.out.println("*************** wgs84_to_katech_kakao**********************");
            System.out.println(border.getIdx());


            try {
                String url = "https://sandbox-api-order.devel.kakao.com/v1/convert/polygon?from=WGS84&to=KTM";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // JSON
                headers.add("Authorization", "OTM0RkUyMzE0M0NEOERDRDEzMUU2QkQ3NDMwQUExMjc=");
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
                HttpEntity<?> reqEntity = new HttpEntity<>(polygon_wgs84, headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> resEntity = restTemplate.exchange(builder.build(true).encode().toUri(), HttpMethod.POST, reqEntity, String.class);

                String polygon_katech = resEntity.getBody();

                String [] point_x_y = border.getPoly().split(",");

                System.out.println(resEntity);
                System.out.println(polygon_katech);

            } catch (Exception ex) {
                ex.getMessage();
                System.out.println( ex.getMessage());
            }

            System.out.println("*************** wgs84_to_katech_kakao**********************");

        }



        return null;
    }



}
