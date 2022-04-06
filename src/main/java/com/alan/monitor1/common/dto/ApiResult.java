package com.alan.monitor1.common.dto;

import com.alan.monitor1.common.consts.ApiResultConsts;
import lombok.Data;

@Data
public class ApiResult {

    private String result_code; //
    private String result_message; //


    private Object result_data; //

    public ApiResult() {
        this.result_code = ApiResultConsts.resultCode.SUCCESS.getCode();
        this.result_message = ApiResultConsts.resultCode.SUCCESS.getMessage();
    }

    public ApiResult(ApiResultConsts.resultCode resultCode) {
        this.result_code = resultCode.getCode();
        this.result_message = resultCode.getMessage();
    }

    public ApiResult(Object result_data, ApiResultConsts.resultCode resultCode) {
        this.result_data = result_data;

        this.result_code = resultCode.getCode();
        this.result_message = resultCode.getMessage();
    }


}
