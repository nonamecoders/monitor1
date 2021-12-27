package com.alan.monitor1.controller;

import com.alan.monitor1.gis.*;
import com.alan.monitor1.order.Border;
import com.alan.monitor1.service.BorderService;
import com.alan.monitor1.util.JSONUtils;
import com.oreilly.servlet.MultipartRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.valid.IsValidOp;
import org.locationtech.jts.operation.valid.TopologyValidationError;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.util.*;


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


    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadExcel(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Map<String,Object> result = new HashMap<>();
        try {

            String storeId, zoneId, vtxNo;
            Double utmkX, utmkY;

            String savePath = request.getSession().getServletContext().getRealPath("/") + "upload";

            int sizeLimit = 30 * 1024 * 1024;
            String formName = "";
            String fileName = "";

            long fileSize = 0;

            List<testob> test = new ArrayList<>();

            XSSFRow row = null;
            XSSFCell cell = null;

            MultipartRequest multipartRequest = new MultipartRequest(request, savePath,sizeLimit,"UTF-8");

            Enumeration formNames = multipartRequest.getFileNames();
            while(formNames.hasMoreElements()){
                formName = (String) formNames.nextElement();
                fileName = multipartRequest.getFilesystemName(formName);

                if(fileName != null){
                    fileSize = multipartRequest.getFile(formName).length();
                }
            }

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(savePath + "/" + fileName));
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();

            for(int index =1 ; index < rows; index++){

                if(sheet.getRow(index) == null)
                    break;
                row = sheet.getRow(index);

                if(row.getCell(0) == null || row.getCell(0).equals("")){

                    System.out.println(
                            "====================================================================================================");
                    System.out.println(row.getRowNum() + " Row IDX 값 Null");
                    System.out.println(
                            "====================================================================================================");

                    continue;

                }

                storeId = getExcelValueToString(row, 0);
//                System.out.println(storeId);
                zoneId = getExcelValueToString(row, 1);
//                System.out.println(zoneId);
                vtxNo = getExcelValueToString(row, 2);
//                System.out.println(vtxNo);
                utmkX = row.getCell(3).getNumericCellValue();
//                System.out.println(utmkX);
                utmkY = row.getCell(4).getNumericCellValue();
//                System.out.println(utmkY);

                test.add(new testob(
                        (storeId.indexOf(".") > -1 ? storeId.substring(0, storeId.indexOf(".")) : storeId) + zoneId,
                        vtxNo, utmkX, utmkY));

                // Insert 데이터 확인
                /*
                 * System.out.println("storeId : " + storeId + " / " + "zoneId : " + zoneId +
                 * " / " + "vtxNo : " + vtxNo + " / " + "utmkX : " + utmkX + " / " + "utmkY : "
                 * + utmkY);
                 */

            }

            // UTMK Excel Row 개수 출력
            System.out.println(
                    "====================================================================================================");
            System.out.println(" UTMK Excel Row Count (Header 제외) : " + test.size());
            System.out.println(
                    "====================================================================================================");

            List<TransPoint> transPointList = new ArrayList<>();

            // UTMK > KAINESS 좌표로 변환
            for (testob ob : test) {
                GeoPoint in_pt = new GeoPoint(ob.getOrigin_x(), ob.getOrigin_y());
                GeoPoint katec_pt = GeoTrans.convert(GeoTrans.UTMK, GeoTrans.KATEC, in_pt);

                ob.setTrans_x(katec_pt.getX());
                ob.setTrans_y(katec_pt.getY());

                // 변환된 KAINESS 좌표 넣기
                /*
                 * strIdx = ob.idx; strOrd = (int) Double.parseDouble(ob.ord); strCx =
                 * Integer.parseInt(String.valueOf(Math.round(ob.trans_x))); strCy =
                 * Integer.parseInt(String.valueOf(Math.round(ob.trans_y)));
                 */

//                System.out.println(JSONUtils.toJson(ob));
                TransPoint transPoint = new TransPoint();
                transPoint.setIdx(ob.getIdx());
                transPoint.setPointX((int) ob.getTrans_x());
                transPoint.setPointY((int) ob.getTrans_y());

                transPointList.add(transPoint);

            }

//            System.out.println(JSONUtils.toJson(transPointList));

            StringBuffer sb = new StringBuffer();
            Map<String,String> map = new LinkedHashMap();

            for(TransPoint transPoint : transPointList){
                String point = transPoint.getPointX() + " " + transPoint.getPointY();
                if(map.get(transPoint.getIdx()) == null){
                    map.put(transPoint.getIdx(),point);
                } else {
                    map.put(transPoint.getIdx(),map.get(transPoint.getIdx())+","+point);
                }
            }
            System.out.println(JSONUtils.toJson(map));

            int valid_count = 0;
            int total_count = 0;
            Set<String> set = new HashSet<>();
            if(!map.isEmpty()){
                for(String key : map.keySet()){
                    total_count++;
                    if(!isValidationGeometry(map.get(key))) {
                        System.out.println("idx : [" + key + "] 해당 권역은 유효하지 않습니다.");
                        set.add(key);
                    } else{
                        valid_count++;
                        System.out.println("idx : [" + key + "] 해당 권역은 유효합니다.");
                    }
                }

                if(total_count != valid_count){

                    result.put("code","E0001");
                    System.out.println("실패");
                    return result;
                }
            }

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("성공");
        result.put("code","S0000");
        return result;
    }


    String getExcelValueToString(XSSFRow row, int i) throws Exception {

        try {

            return (row.getCell(i).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                    ? Double.toString(row.getCell(i).getNumericCellValue())
                    : row.getCell(i).getStringCellValue();

        } catch (NullPointerException e) {

            System.out.println(
                    "====================================================================================================");
            System.out.println("Excel 빈값 체크 : " + row.getRowNum() + " Row : " + i + " (Cell)");
            System.out.println(
                    "====================================================================================================");

            throw new Exception();
        }

    }

    private static String replaceLast(String string, String toReplace, String replacement) {

        int pos = string.lastIndexOf(toReplace);

        if (pos > -1) {

            return string.substring(0, pos) + replacement + string.substring(pos + toReplace.length(), string.length());

        } else {

            return string;

        }

    }

    public boolean isValidationGeometry(String poly) {

        boolean result = false;

        try {
            WKTReader wktr = new WKTReader();
            Geometry geom = (Polygon) wktr.read("POLYGON (( " + poly + "))");

            IsValidOp validOp = new IsValidOp(geom);
            TopologyValidationError error = validOp.getValidationError();

            if(error != null)
                result = false;
            else
                result = true;

        } catch(Exception e) {
            System.out.println(e.getMessage());
            result = false;
        } finally {
            return result;
        }
    }




}
