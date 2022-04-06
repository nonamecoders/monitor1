package com.alan.monitor1.common.consts;

import org.springframework.http.HttpStatus;

public class ApiResultConsts {

    public enum resultCode {

        //200 성공
        SUCCESS("s0000", "성공", HttpStatus.OK),

        // 400 잘못된 요청
        ERROR("e40000", "에러", HttpStatus.BAD_REQUEST),
        ERROR_MISSING_TOKEN("e40001", "토큰값 누락", HttpStatus.BAD_REQUEST),
        ERROR_MISSING_PARAMETER_DEFAULT("e40002", "기본 파라미터 누락", HttpStatus.BAD_REQUEST),
        ERROR_MISSING_PARAMETER_MUST("e40003", "필수 파라미터 누락", HttpStatus.BAD_REQUEST),
        ERROR_API_NOT_USE("e40004", "사용이 중지된 API 호출", HttpStatus.BAD_REQUEST),
        ERROR_MAX_TOKEN_COUNT_OVER("e40005", "최대 토큰 보유 갯수 초과.", HttpStatus.BAD_REQUEST),
        ERROR_TOKEN_OVER_EXPIRE_DATE("e40006", "토큰 유효기간 초과.", HttpStatus.BAD_REQUEST),
        ERROR_NOT_VALIDATE_ORDER_AMT("e40007", "주문금액 불일치.", HttpStatus.BAD_REQUEST),
        ERROR_NOT_UPDATE("e40008", "데이터가 변경되지 않았습니다.", HttpStatus.BAD_REQUEST),
        ERROR_NOT_INSERT("e40009", "데이터가 저장되지 않았습니다.", HttpStatus.BAD_REQUEST),
        ERROR_NOT_REMOVE("e40010", "데이터가 삭제되지 않았습니다.", HttpStatus.BAD_REQUEST),
        ERROR_NO_DATA("e40011", "데이터가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
        ERROR_NOT_AVAILABLE_PARAMETER("e40012", "유효하지 않은 파라미터 값 입니다.", HttpStatus.BAD_REQUEST),

        // 401 권한없음 : 인증 관련 오류
        ERROR_PARAMETER_AUTH("e40101", "파라미터 유효성 인증 실패", HttpStatus.UNAUTHORIZED),
        ERROR_DECODE_PARAMETER("e40102", "파라미터 암호화 인증 실패", HttpStatus.UNAUTHORIZED),
        ERROR_NOT_VALIDATE_AUTHNUM("e40103", "유효하지 않은 인증 번호 입니다.", HttpStatus.UNAUTHORIZED),
        ERROR_NOT_ACCEPT_ACCOUNT("e40104", "계정 인증 실패.", HttpStatus.UNAUTHORIZED),
        ERROR_TOKEN_NOT_ACCEPT_ROLE("e40105", "권한 인증 실패.", HttpStatus.UNAUTHORIZED),

        // 404 찾을 수 없음
        ERROR_NOT_FOUND("e40401", "잘못된 URL이거나 현재 서비스 준비중입니다.", HttpStatus.NOT_FOUND),

        // 405 지원하지 않음
        ERROR_METHOD_NOT_ALLOWED("e40501", "허용하지 않는 주소입니다.", HttpStatus.METHOD_NOT_ALLOWED),

        // 500 서버 오류
        ERROR_REVERSE_API_CALL("e99996", "시스템 에러(외부 요청)", HttpStatus.INTERNAL_SERVER_ERROR), // 외부 API 호출 실패로 인한 시스템 에러
        ERROR_MISSING_PROCESS("e99997", "시스템 에러", HttpStatus.INTERNAL_SERVER_ERROR), // 비정상적인 흐름, 디버깅시 활용
        ERROR_ENCODE("e99997", "인코딩 에러", HttpStatus.INTERNAL_SERVER_ERROR),
        ERROR_DECODE("e99998", "디코딩 에러", HttpStatus.INTERNAL_SERVER_ERROR),
        ERROR_SYSTEM("e99999", "시스템 에러", HttpStatus.INTERNAL_SERVER_ERROR);


        private String code;
        private String message;
        private HttpStatus httpsCode;

        resultCode(String code, String message, HttpStatus httpsCode) {
            this.code = code;
            this.message = message;
            this.httpsCode = httpsCode;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getHttpsCode() {
            return httpsCode;
        }
    }


}
