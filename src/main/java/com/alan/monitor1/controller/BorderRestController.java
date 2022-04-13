package com.alan.monitor1.controller;

import com.alan.monitor1.common.CommonService;
import com.alan.monitor1.common.dto.ApiResult;
import com.alan.monitor1.gis.*;
import com.alan.monitor1.service.BorderJPAService;

import com.alan.monitor1.util.JSONUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Slf4j
@RestController
public class BorderRestController {

    @Resource(name="com.alan.monitor1.service.BorderJPAService")
    BorderJPAService borderJPAService;

    @RequestMapping(value = "/deleteGis", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<?> deleteGis(HttpServletRequest request, @RequestBody DeleteGis param) throws Exception {
        ApiResult result = new ApiResult();

        borderJPAService.saveGisEmpty(param);

        return CommonService.apiResult(result);

    }

    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    @ResponseStatus
    public @ResponseBody ResponseEntity<?> uploadExcel(@RequestParam("file") List<MultipartFile> files, HttpServletRequest request) {

        ApiResult result = new ApiResult();
        ApiResult uploadGisResult = new ApiResult();
        List<ApiResult> apiResultList = new ArrayList<>();


        try {
            log.info("files Count :" + files.size());

            String savePath = StringUtils.cleanPath(request.getSession().getServletContext().getRealPath("/") + "upload");

            String fileName = "";

            for(MultipartFile file : files) {
                fileName = file.getOriginalFilename();
                File dest = new File(savePath+"/"+fileName);
                file.transferTo(dest);
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(savePath + "/" + fileName));
                XSSFSheet sheet = workbook.getSheetAt(0);
                uploadGisResult = borderJPAService.uplaodGis(fileName,sheet);

                apiResultList.add(uploadGisResult);
            }


//            apiResultList.removeIf(c->c.getResult_code().equals(ApiResultConsts.resultCode.SUCCESS.getCode()));
//
//            if(apiResultList.size() > 0)
                result.setResult_data(apiResultList);

            log.info(JSONUtils.toJson(result));



        } catch (Exception ex){
            ex.printStackTrace();

        }

        return CommonService.apiResult(result);
    }

}
