package com.alan.monitor1.common;

import com.alan.monitor1.common.consts.ApiResultConsts;
import com.alan.monitor1.common.consts.ApiResultConsts.resultCode;
import com.alan.monitor1.common.dto.ApiResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("commonService")
public class CommonService {

    public static ResponseEntity<?> apiResult(ApiResult apiResult) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        HttpStatus httpStatus = null;

        int code_length = ApiResultConsts.resultCode.values().length;
        resultCode[] apiResultArr = ApiResultConsts.resultCode.values();

        for(int i=0; i < code_length; i++) {
            if ( apiResultArr[i].getCode().equals(apiResult.getResult_code()) ) {
                httpStatus = apiResultArr[i].getHttpsCode();
            }
        }

        if(apiResult.getResult_code().equals(resultCode.SUCCESS.getCode())) {
            //성공 로그
        } else {
            //실패 로그
        }

        return new ResponseEntity<>(apiResult, headers, httpStatus);
    }

}
