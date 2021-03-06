package com.alan.monitor1.service;

import com.alan.monitor1.common.consts.ApiResultConsts;
import com.alan.monitor1.common.dto.ApiResult;
import com.alan.monitor1.gis.*;
import com.alan.monitor1.repository.GisEmptyRepository;
import com.alan.monitor1.repository.GisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.valid.IsValidOp;
import org.locationtech.jts.operation.valid.TopologyValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service("com.alan.monitor1.service.BorderJPAService")
public class BorderJPAService {

    @Autowired
    GisRepository gisRepository;

    @Autowired
    GisEmptyRepository gisEmptyRepository;

    public ApiResult uplaodGis(String fileName, XSSFSheet sheet) {

        ApiResult result = new ApiResult();

        String localdate = LocalDate.now().plusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String localtime = (String) new SimpleDateFormat("HHmmss").format(new java.util.Date());

        try {

            String storeId, zoneId, vtxNo;
            double utmkX, utmkY;

            XSSFRow row;

            List<TransXY> test = new ArrayList<>();

            int rows = sheet.getPhysicalNumberOfRows();

            for(int index =1 ; index < rows; index++){

                if(sheet.getRow(index) == null)
                    break;
                row = sheet.getRow(index);

                if(row.getCell(0) == null || row.getCell(0).equals("")){

                    log.info(
                            "====================================================================================================");
                    log.info(row.getRowNum() + " Row IDX ??? Null");
                    log.info(
                            "====================================================================================================");

                    continue;

                }

                storeId = getExcelValueToString(row, 0);
                zoneId = getExcelValueToString(row, 1);
                vtxNo = getExcelValueToString(row, 2);
                utmkX = row.getCell(3).getNumericCellValue();
                utmkY = row.getCell(4).getNumericCellValue();

                test.add(new TransXY(
                        (storeId.indexOf(".") > -1 ? storeId.substring(0, storeId.indexOf(".")) : storeId) + zoneId,
                        vtxNo, utmkX, utmkY));

            }

            List<TransPoint> transPointList = new ArrayList<>();

            // UTMK > KAINESS ????????? ??????
            for (TransXY ob : test) {
                GeoPoint in_pt = new GeoPoint(ob.getOrigin_x(), ob.getOrigin_y());
                GeoPoint katec_pt = GeoTrans.convert(GeoTrans.UTMK, GeoTrans.KATEC, in_pt);

                ob.setTrans_x(katec_pt.getX());
                ob.setTrans_y(katec_pt.getY());

                TransPoint transPoint = new TransPoint();
                transPoint.setIdx(ob.getIdx());
                transPoint.setPointX((int) ob.getTrans_x());
                transPoint.setPointY((int) ob.getTrans_y());

                transPointList.add(transPoint);

            }

            Map<String,String> map = new LinkedHashMap();

            for(TransPoint transPoint : transPointList){
                String point = transPoint.getPointX() + " " + transPoint.getPointY();
                if(map.get(transPoint.getIdx()) == null){
                    map.put(transPoint.getIdx(),point);
                } else {
                    map.put(transPoint.getIdx(),map.get(transPoint.getIdx())+","+point);
                }
            }

            if(!map.isEmpty()){
                for(String key : map.keySet()){
                    Map<Boolean,String> isValidMap = isValidationGeometry(map.get(key));
                    Set<Boolean> keySet = isValidMap.keySet();

                    for(Boolean k : keySet) {
                        if(!k) {
                            log.info("idx : [" + key + "] ?????? ????????? ???????????? ????????????.");

                            result.setResult_code(ApiResultConsts.resultCode.ERROR_NOT_INSERT.getCode());
                            result.setResult_message("idx : [" + key + "] ?????? ????????? ???????????? ????????????." +"\n" +isValidMap.get(k));
                            result.setResult_data(fileName);

                            log.info(isValidMap.get(k));
                            log.info(result.toString());

                            //??????????????? ???????????????...
                            return result;
                        } else {
                            log.info("idx : [" + key + "] ?????? ????????? ???????????????.");
                        }
                    }
                }

                // insert into history db
                for(String key : map.keySet()){
                    gisRepository.save(Gis.builder()
                            .brand_cd("PIZZAHUT")
                            .idx(key)
                            .is_valid(1)
                            .poly(map.get(key))
                            .reg_id("alan")
                            .reg_date(localdate)
                            .reg_time(localtime)
                            .upd_id("alan")
                            .upd_date(localdate)
                            .upd_time(localtime)
                            .build());
                }

            }


            return new ApiResult(fileName, ApiResultConsts.resultCode.SUCCESS);

        } catch (Exception ex){
            log.info(ex.getMessage());
            result.setResult_code(ApiResultConsts.resultCode.ERROR.getCode());
            result.setResult_message(ex.getMessage());

        }

        return result;
    }

    public ApiResult saveGisEmpty(DeleteGis gis) throws  Exception {

        String[] idxArr = gis.getIdx();

        try {
            for (String s : idxArr) {

                gisEmptyRepository.save(GisEmpty.builder()
                        .brand_cd(gis.getBrand_cd())
                        .idx(s)
                        .reg_id("system")
                        .reg_date(LocalDate.now().plusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE))
                        .reg_time(new SimpleDateFormat("HHmmss").format(new java.util.Date()))
                        .build());
            }

            return new ApiResult(ApiResultConsts.resultCode.SUCCESS);

        } catch (Exception ex) {
            log.info(ex.getMessage());
            return new ApiResult(gis, ApiResultConsts.resultCode.ERROR);
        }
    }

    String getExcelValueToString(XSSFRow row, int i) throws Exception {

        try {

            return (row.getCell(i).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                    ? Double.toString(row.getCell(i).getNumericCellValue())
                    : row.getCell(i).getStringCellValue();

        } catch (NullPointerException e) {

            log.info(
                    "====================================================================================================");
            log.info("Excel ?????? ?????? : " + row.getRowNum() + " Row : " + i + " (Cell)");
            log.info(
                    "====================================================================================================");

            throw new Exception();
        }
    }

    public Map<Boolean,String> isValidationGeometry(String poly) {

        Map<Boolean,String> map = new HashMap<>();
        boolean result = false;
        String message = "";

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
            log.info(e.getMessage());
            result = false;
            message = e.getMessage();
        } finally {
            map.put(result,message);
            return map;
        }
    }
}
