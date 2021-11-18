package com.alan.monitor1;


import com.alan.monitor1.gis.GeoPoint;
import com.alan.monitor1.gis.GeoTrans;
import com.alan.monitor1.order.Border;
import com.alan.monitor1.service.BorderService;
import com.alan.monitor1.util.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Monitor1ApplicationTests {


        @Resource(name="com.alan.monitor1.service.BorderService")
        BorderService mBorderService;
	@Test
	public void contextLoads() throws Exception {

//
//        // System.out.println("------- e쿠폰 - 기프티쇼 승인취소 처리 서비스 -------");
//
//        // String serverIP = "220.117.242.158"; // 기프티쇼
//        String serverIP = "220.117.242.172"; // 기프티쇼 Real
//        // System.out.println("서버에 연결중입니다. 서버 IP : " + serverIP);
//
//        DataOutputStream dataOutputStream = null;// 서버로 데이터를 보내는 스트림
//
//        // 소켓을 생성하여 연결을 요청한다.
//        Socket socket = new Socket();
//        socket.connect(new InetSocketAddress(serverIP, 17212), 3000);  // 기프티쇼
//        socket.setSoTimeout(3000);
//        /*
//        if (socket.isConnected() == true) {
//            System.out.println("연결이 성공적으로 이루어졌습니다.");
//        } else {
//            System.out.println("연결에 문제가 있습니다.");
//        }
//        */
//        //dataOutputStream = new DataOutputStream(socket.getOutputStream());
//        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//
//        String data = "";
//        String version = "0002";
//        String funCode = "102";
//        int headLen = 0;
//        int result_position = 0;
//
//        String branch_id = "001150";
//        String branch_nm = "수원역점";
//        String ecoupon_num = "946932274368";
//        String pos_cd = "001150";
//        String partner_id = "MR0001"; // 제휴사ID
//        String approval_no = "";
//        String recv_phone = "";
//
//        if (version == "0001") { // Version1
//
//            // Header
//            String h1 = "0001"; // 버전[4]
//            String h2 = StringUtils.rightPad(partner_id, 6); // 요청아이디[6]
//            String h3 = funCode; // 기능코드[3]
//            String h4 = StringUtils.rightPad(branch_id, 10); // 지점코드 [10]
//            String h5 = StringUtils.rightPad("MRPZ" + pos_cd, 10); // POS코드[10]
//            String h6 = TimeUtils.getCurrentTime_DATE_FORMAT(); // 거래일자[8]
//            String h7 = TimeUtils.getCurrentTime_TIME_FORMAT(); // 거래시간[6]
//            String h8 = "   "; // 상태코드[3]
//            String h9 = "     "; // 실패코드[5]
//            String h10 = "32  "; // Header부분 - Body길이[4]
//
//            // Body
//            String b1 = ecoupon_num; // 쿠폰번호[12]
//            String b2 = StringUtils.rightPad(approval_no, 20); // 승인번호[20]
//
//            data = h1 + h2 + h3 + h4 + h5 + h6 + h7 + h8 + h9 + h10 + b1 + b2;
//            headLen = 59;
//            result_position = 47;
//        } else if (version == "0002") { // Version 2
//
//            // Header
//            String h1 = "0002"; // 버전[4]
//            String h2 = StringUtils.rightPad(partner_id, 6); // 요청아이디[6]
//            String h3 = funCode; // 기능코드[3]
//            String h4 = StringUtils.rightPad(branch_id, 10); // 지점코드 [10]
//            String h5 = addSpace(branch_nm, 20);// 지점명[20]
//            String h6 = StringUtils.rightPad(pos_cd, 10); // POS코드[10]
//            String h7 = "2"; // 수신자번호처리[1]
//            String h8 = TimeUtils.getCurrentTime_DATE_FORMAT();
//            ; // 거래일자[8]
//            String h9 = TimeUtils.getCurrentTime_TIME_FORMAT();
//            ; // 거래시간[6]
//            String h10 = "   "; // 상태코드[3]
//            String h11 = "     "; // 실패코드[5]
//            String h12 = "52  "; // Header부분 - Body길이[4]
//
//            // Body
//            String b1 = ecoupon_num; // 쿠폰번호[12]
//            String b2 = StringUtils.rightPad(approval_no, 20); // 승인번호[20]
//            String b3 = StringUtils.rightPad(recv_phone, 20);// 수신자번호[20]
//
//            data = h1 + h2 + h3 + h4 + h5 + h6 + h7 + h8 + h9 + h10 + h11 + h12 + b1 + b2 + b3;
//            headLen = 80;
//            result_position = 68;
//        }
//
//        // 요청 메시지 출력, 전달
////        log.debug("기프트쇼 취소 요청 데이터 => {}", data);
//        dataOutputStream.write(data.getBytes("euc-kr"));
//        dataOutputStream.flush();
//
//        byte[] buffer = new byte[4096];
//        int cnt = -1;
//        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
//
//        String response_msg = "";
//        String status_code = "";
//        String error_code = "";
//
//        // 결과리턴값 세팅
////        ECoupon.ResultInquiry result = new ECoupon.ResultInquiry();
//
//        System.out.println("--------------------------------");
//        while ((cnt = bis.read(buffer)) != -1) {
//
//            response_msg = new String(buffer, 0, cnt, "euc-kr");
//            // System.out.println("response_msg : " + response_msg);
//
//            status_code = new String(buffer, result_position, 3, "euc-kr");
//            // System.out.println("status_code : " + status_code);
//
//            error_code = new String(buffer, result_position + 3, 5, "euc-kr");
//            // System.out.println("error_code : " + error_code);
//
//            // -------------- 요청응답결과 -------------
//            String allianceCode = new String(buffer, 4, 6, "euc-kr").trim(); // 제휴사 ID[6]
//            String branchID = new String(buffer, 13, 10, "euc-kr").trim(); // 가맹점 ID[10]
//            String branchName = new String(buffer, 23, 20, "euc-kr").trim(); // 가맹점명[20]
//            String posCode = new String(buffer, 43, 10, "euc-kr").trim(); // 제휴사 지점 POS ID[10]
//            String reqDate = new String(buffer, 54, 8, "euc-kr").trim(); // 승인 요청일자[8]
//            String reqTime = new String(buffer, 62, 6, "euc-kr").trim(); // 승인 요청시간[6]
//            String couponNo = new String(buffer, 80, 12, "euc-kr").trim(); // 쿠폰 번호
//            String approvalNo = ""; // 승인번호[20]
//            String respMsg = ""; // 응답메시지
//
//            if (status_code.equals("000")) {
//                approvalNo = new String(buffer, headLen + 12, 20, "euc-kr").trim(); // 승인번호[20]
//                System.out.println("승인 완료 후 승인번호:" + approvalNo);
//            } else {
//
////                respMsg = getGiftiShowErrorMsg(error_code);
//
////                log.error("[쿠폰번호: {}, 취소 에러] => {}", param.getCoupon_id(), respMsg);
//
//                if (error_code.equals("E0006")) {
////                    result.setResultCode(ApiResultConsts.resultCode.ERROR_ECOUPON_USED);
//                    System.out.println("이미 사용된 쿠폰입니다");
//
//                } else if (error_code.equals("E0010")) { // E0010, 이미 교환 취소되었습니다.
////                    result.setResultCode(ApiResultConsts.resultCode.ERROR_ECOUPON_CANCELED);
////                    result.setBrand_cd(param.getBrand_cd());
////                    result.setCoupon_id(param.getCoupon_id());
////                    // 취소 History Update 시 쿼리조건에 order_id 필요하여 추가 [2019-02-11 / Ryan ]
////                    result.setOrder_id(param.getOrder_id());
//                    System.out.println("이미 교환 취소 됨.");
//
//                } else {
////                    result.setResultCode(ApiResultConsts.resultCode.ERROR_ECOUPON_INVALID);
//
//                    System.out.println("유효하지않은 쿠폰");
//                }
//            }
//            /*
//            System.out.println("FunCode : " + funCode); // 기능코드
//            System.out.println("status_code : " + status_code); // 상태코드
//            System.out.println("error_code : " + error_code); // 실패코드
//            System.out.println("AllianceCode : " + allianceCode);
//            System.out.println("BranchID : " + branchID);
//            System.out.println("BranchName : " + branchName);
//            System.out.println("PosCode : " + posCode);
//            System.out.println("ReqDate : " + reqDate);
//            System.out.println("ReqTime : " + reqTime);
//            System.out.println("CouponNo : " + couponNo);
//            System.out.println("ApprovalNo : " + approvalNo);
//            System.out.println("RespMsg : " + respMsg);
//            */
//            if (bis.available() == 0)
//                break;
//        }
//
//        // 스트림과 소켓을 닫는다.
//        bis.close();
//        socket.close();
//
////        return result;
//    }
//    // 문자열 공백 채우기
//    public String addSpace(String str, int len) throws Exception{
//
//        len = len - str.getBytes("euc-kr").length;
//
//        for(int i=0;i<len;i++){
//            str += " ";
//        }
//
//        return str;
//    }
        //utmk 좌표 (최대, 최소)
//        int utmk_max_x = 316299;
//        int utmk_min_x = 312266;
//        int utmk_max_y = 522753;
//        int utmk_min_y = 519385;
//
//
//        //wgs84 (최대, 최소)
//        double center_x=0;
//        double center_y=0;
//
//
//        center_x = utmk_min_x + ((utmk_max_x - utmk_min_x) / 2);
//        center_y = utmk_min_y + ((utmk_max_y - utmk_min_y) / 2);
//
//        System.out.println("center_x : " + center_x);
//        System.out.println("center_y : " + center_y);
//        GeoPoint in_pt = new GeoPoint(center_x,center_y);
//        GeoPoint utmk_to_wgs = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, in_pt);
//
//        System.out.println(utmk_to_wgs.getX()+"//"+utmk_to_wgs.getY());


            try {
                    String url = "https://sandbox-api-order.devel.kakao.com/v1/convert/polygon?to=KTM";

                    Border border = new Border();
                    border.setBrand_cd("PIZZAHUT");
                    border.setIdx("0737V-02");

                    Border borderPoly =mBorderService.getGisBorderList(border);
                    System.out.println(borderPoly);

                    borderPoly.getPoly();


                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // JSON
                    headers.add("Authorization", "OTM0RkUyMzE0M0NEOERDRDEzMUU2QkQ3NDMwQUExMjc=");
                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
                    HttpEntity<?> reqEntity = new HttpEntity<>(borderPoly.getPoly(), headers);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> resEntity = restTemplate.exchange(builder.build(true).encode().toUri(), HttpMethod.POST, reqEntity, String.class);

                    String response = resEntity.getBody();
                    System.out.println(response.toString());

            } catch (Exception ex) {
                   ex.getMessage();
                    System.out.println( ex.getMessage());
            }

    }

}




