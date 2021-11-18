package com.alan.monitor1.gis;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.alan.monitor1.common.AES256Util;
import com.alan.monitor1.spc.ReceiptOfSpc;
import com.alan.monitor1.util.CipherUtil;
import com.alan.monitor1.util.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;
import sun.security.krb5.internal.crypto.Aes256;


@Controller
public class HomeController {

//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HomeController.class);
    static String localdate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);//yyyymmdd
    static String localtime = (String) new SimpleDateFormat("HHmmss").format(new java.util.Date());

//    /*
//     * @Scheduled(cron = "0 0/1 * * * *") public void cronTest1(){ try { //String
//     * value = schedulerDao.test(); //System.out.println("value:"+value);
//     *
//     * //System.out.println("30초");
//     *
//     * Connection conn = null; PreparedStatement pstmt = null; ResultSet rs = null;
//     *
//     * String ip = "10.190.10.121"; String port = "1433"; String user = "sa"; String
//     * pass = "1q2w3e4r5t"; String db_name = "yang"; //String ip = "10.210.2.70";
//     * String ip = "222.233.18.70"; String port = "8934"; String user = "kakao";
//     * String pass = "CNTzkzkdh2017)!!"; String db_name = "CMS_KAKAO_NEW"; //
//     * =============================================================================
//     * ======================
//     *
//     * try { //System.out.println(" >> 작업 시작!! ");
//     * //System.out.println(" >> DataBase 연결 시작"); // DataBase 연결
//     * Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); conn =
//     * DriverManager.getConnection("jdbc:sqlserver://" + ip + ":" + port +
//     * ";SelectMethod=cursor;DatabaseName=" + db_name, user, pass);
//     *
//     * String[] coupons = {"KA2Q" , "KA2B"};
//     *
//     * for (String coupon : coupons) {
//     *
//     * String brand_cd = "";
//     *
//     * switch (coupon) { case "KA2Q": brand_cd = "BBQ"; break;
//     *
//     * case "KA2B": brand_cd = "BHC"; break;
//     *
//     * default: break; }
//     *
//     * // Query 작성 String sql=""; sql = sql +
//     * " select (select top 1 MAX_SERIAL_NUM from TB_COUPON_MASTER where 1=1 and COUPON_ID = '"
//     * + coupon + "') - COUNT(PD.COUPON_ID) as cnt       "; sql = sql +
//     * " select max(50) as cnt                               "; sql = sql +
//     * " FROM  TB_PAY_DETAIL PD with(nolock)                       "; sql = sql +
//     * " inner join TB_ORDER_STATE OS with (nolock)                "; sql = sql +
//     * "    on PD.K_ORDER_ID = OS.K_ORDER_ID                       "; sql = sql +
//     * "    and OS.ORDER_STATE not in ('B', 'C', 'Q', 'X', 'Z')    "; sql = sql +
//     * " WHERE 1=1                                                 "; sql = sql +
//     * " and PD.COUPON_ID = '" + coupon + "'                                 "; sql
//     * = sql + " and PD.REG_DATE = CONVERT(varchar(8), getdate(), 112)     "; sql =
//     * sql + "select 30 as cnt";
//     *
//     * //System.out.println(" >> Query 실행"); // Query 실행 pstmt =
//     * conn.prepareStatement(sql); rs = pstmt.executeQuery();
//     *
//     * //System.out.println("쿼리 실행~");
//     *
//     * while(rs.next()){
//     *
//     * long time = System.currentTimeMillis();
//     *
//     * SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//     *
//     * String str = dayTime.format(new Date(time));
//     *
//     *
//     * //System.out.println(rs.getInt("cnt")); System.out.println(str +
//     * " >> "+brand_cd+"의 "+coupon+"쿠폰의 잔여 갯수가 "+ rs.getInt("cnt")+ "개");
//     *
//     * if (rs.getInt("cnt") <= 50) {
//     *
//     * // Query 작성 sql =""; sql = sql +
//     * " select brand_cd, coupon_id, phone_no from TB_COUPON_SMS_DAILY   "; sql =
//     * sql + " where 1=1                                                       ";
//     * sql = sql +
//     * " and coupon_id = '"+coupon+"'                                    "; sql =
//     * sql + " and send_flag = 'N'                                             ";
//     *
//     * System.out.println(" >> "+brand_cd+"의 "+coupon+"쿠폰의 잔여 갯수가 "+
//     * rs.getInt("cnt")+ "개 이므로 문자발송 Query 실행"); // Query 실행 pstmt =
//     * conn.prepareStatement(sql);
//     *
//     * sendSMS(brand_cd, coupon, pstmt.executeQuery()); } } }
//     *
//     *
//     *
//     * } catch (Exception e) {
//     *
//     * //System.out.println("exception : " + e.toString());
//     *
//     * } finally {
//     *
//     * //System.out.println(" >> DataBase Close"); conn.close(); pstmt.close();
//     * rs.close(); }
//     *
//     * } catch (Exception e) { e.printStackTrace(); } }
//     *
//     * private void sendSMS(String brand_cd, String coupon, ResultSet resultSet) {
//     * try { //System.out.println("문자발송");
//     *
//     * // DataBase Setting
//     * =============================================================================
//     * ===== Connection conn = null; PreparedStatement pstmt = null; ResultSet rs =
//     * null;
//     *
//     * String ip = "211.219.135.19"; String port = "1433"; String user = "kakao_cs";
//     * String pass = "CS_kakao17)(*"; String db_name = "SMS_SK"; //
//     * =============================================================================
//     * ======================
//     *
//     * try {
//     *
//     * List<String> numbers = new ArrayList<String>();
//     *
//     * while(resultSet.next()){ numbers.add(resultSet.getString("phone_no")); }
//     * System.out.println(" >> 작업 시작!! "); System.out.println(" >> DataBase 연결 시작");
//     * // DataBase 연결 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//     * conn = DriverManager.getConnection("jdbc:sqlserver://" + ip + ":" + port +
//     * ";SelectMethod=cursor;DatabaseName=" + db_name, user, pass);
//     *
//     * System.out.println("numbers : " + numbers);
//     *
//     * if (numbers.size() > 0) {
//     *
//     * // Query 작성 String sql=""; sql = sql +
//     * " Insert into SMS_SK.dbo.TBL_SUBMIT_QUEUE     "; sql = sql +
//     * "   (                                         "; sql = sql +
//     * "         [USR_ID]                            "; sql = sql +
//     * "         ,[SMS_GB]                           "; sql = sql +
//     * "         ,[USED_CD]                          "; sql = sql +
//     * "         ,[RESERVED_FG]                      "; sql = sql +
//     * "         ,[RESERVED_DTTM]                    "; sql = sql +
//     * "         ,[SAVED_FG]                         "; sql = sql +
//     * "         ,[RCV_PHN_ID]                       "; sql = sql +
//     * "         ,[SND_PHN_ID]                       "; sql = sql +
//     * "         ,[SND_MSG]                          "; sql = sql +
//     * "         ,[CONTENT_CNT]                      "; sql = sql +
//     * "         ,[SMS_STATUS]                       "; sql = sql +
//     * " 		  ,[MSG_TITLE]                        "; sql = sql +
//     * "    ) values                                 ";
//     *
//     * for (String number : numbers) {
//     *
//     * sql = sql +
//     * " (                                                                                         "
//     * ; sql = sql +
//     * " 		'4453'                                                                              "
//     * ; sql = sql +
//     * " 		,'1'                                                                                "
//     * ; sql = sql +
//     * " 		,'00'                                                                               "
//     * ; sql = sql +
//     * " 		,'I'                                                                                "
//     * ; sql = sql +
//     * " 		,replace(replace(replace(convert(varchar,getdate(),120),'-',''),':',''),' ','')     "
//     * ; sql = sql +
//     * " 		,'1'                                                                                "
//     * ; sql = sql + " 		,'"
//     * +number+"'                                                                       "
//     * ; sql = sql +
//     * " 		,'023090380'                                                                        "
//     * ; sql = sql +
//     * " 		,'"+brand_cd+"브랜드 쿠폰의 잔여수량이 50개 미만입니다.'                            "
//     * ; sql = sql +
//     * " 		,'0'                                                                                "
//     * ; sql = sql +
//     * " 		,'0'                                                                                "
//     * ; sql = sql +
//     * " 		,'kakao'                                                                            "
//     * ; sql = sql +
//     * " ),                                                                                        "
//     * ; }
//     *
//     * sql = replaceLast(sql, ",", "");
//     *
//     *
//     * System.out.println(" >> SMS발송"); System.out.println(" >> Query 실행"); // Query
//     * 실행 //pstmt = conn.prepareStatement(sql); //pstmt.execute();
//     *
//     * updateSendFlag(brand_cd, coupon);
//     *
//     * //System.out.println(" >> DataBase Close"); conn.close(); //pstmt.close(); }
//     *
//     * } catch (Exception e) {
//     *
//     * System.out.println("exception : " + e.toString());
//     * //System.out.println(" >> DataBase Close"); conn.close(); //pstmt.close();
//     *
//     * } finally {
//     *
//     * //System.out.println(" >> DataBase Close"); //rs.close(); }
//     *
//     * } catch (Exception e) { e.printStackTrace(); }
//     *
//     *
//     *
//     * }
//     *
//     * private void updateSendFlag(String brand_cd, String coupon) { try { //String
//     * value = schedulerDao.test(); //System.out.println("value:"+value);
//     *
//     * System.out.println("문자발송내역 업데이트");
//     *
//     * // DataBase Setting
//     * =============================================================================
//     * ===== Connection conn = null; PreparedStatement pstmt = null; ResultSet rs =
//     * null;
//     *
//     * String ip = "10.190.10.121"; String port = "1433"; String user = "sa"; String
//     * pass = "1q2w3e4r5t"; String db_name = "yang"; //String ip = "10.210.2.70";
//     * String ip = "222.233.18.70"; String port = "8934"; String user = "kakao";
//     * String pass = "CNTzkzkdh2017)!!"; String db_name = "CMS_KAKAO_NEW"; //
//     * =============================================================================
//     * ======================
//     *
//     * try { System.out.println(" >> 작업 시작!! ");
//     * System.out.println(" >> DataBase 연결 시작"); // DataBase 연결
//     * Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); conn =
//     * DriverManager.getConnection("jdbc:sqlserver://" + ip + ":" + port +
//     * ";SelectMethod=cursor;DatabaseName=" + db_name, user, pass);
//     *
//     * System.out.println("brnad_cd : " + brand_cd + ", coupon : " + coupon);
//     *
//     * // Query 작성 // brand_cd / coupon을 받아서 send_flag "Y"로 없데이트 String sql=""; sql
//     * = sql + " update TB_COUPON_SMS_DAILY set        "; sql = sql +
//     * " 	send_flag = 'Y'                     "; sql = sql +
//     * " where 1=1                             "; sql = sql +
//     * " and brand_cd = '"+brand_cd+"'         "; sql = sql +
//     * " and coupon_id = '"+coupon+"'          ";
//     *
//     * //System.out.println(" >> Query 실행"); // Query 실행 pstmt =
//     * conn.prepareStatement(sql); pstmt.execute();
//     *
//     *
//     *
//     *
//     * } catch (Exception e) {
//     *
//     * System.out.println("exception : " + e.toString());
//     *
//     * } finally {
//     *
//     * //System.out.println(" >> DataBase Close"); conn.close(); pstmt.close(); }
//     *
//     * } catch (Exception e) { e.printStackTrace(); }
//     *
//     * }
//     */

    /**
     * Simply selects the home view to render by returning its name.
     *
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) throws Exception {

//        logger.info("Welcome home! The client locale is {}."+"\n 오늘 날짜 : "+localdate+" 실행한 시간 : "+localtime, locale);

        // 표준시간(협정 세계시 (UTC))을 기준으로한 현재 시간을 Long형으로 변환
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        // 현재 시간 데이터 변환 ex) #년 #월 #일 (월) 오후 #시 #분 #초
        String formattedDate = dateFormat.format(date);
//        String linkedServer = "Alpha";

//        model.addAttribute("linkedServer",linkedServer);
        model.addAttribute("serverTime", formattedDate);

        return "uploadActionExcel";
    }

    @RequestMapping(value = "/uploadActionExcel", method = RequestMethod.POST)
    public String excelUpload(String[] args, HttpServletRequest request) throws Exception {


        try {

            String strIdx;
            int strOrd, strCx, strCy;

            String storeId, zoneId, vtxNo;
            Double utmkX, utmkY;

            /* 엑셀 파일 업로드 저장 경로 */
            String savePath = request.getRealPath("/") + "upload";

            int sizeLimit = 30 * 1024 * 1024; // 파일 사이즈
            String formName = "";
            String fileName = "";
            long fileSize = 0;

            List<testob> test = new ArrayList<testob>();

            XSSFRow row = null;
            XSSFCell cell = null;

            // 파일 업로드 처리
            MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8",
                    new DefaultFileRenamePolicy());

            Enumeration formNames = multi.getFileNames();

            while (formNames.hasMoreElements()) {

                formName = (String) formNames.nextElement();
                fileName = multi.getFilesystemName(formName);

                if (fileName != null) { // 파일이 업로드 되면

                    fileSize = multi.getFile(formName).length();

                }
            }

            // 서버에 업로드한 엑셀 파일의 Sheet1 객체를 가져옴
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(savePath + "/" + fileName));
            XSSFSheet sheet = workbook.getSheetAt(0);
            /* Sheet sheet = workbook.getSheet(0); */

            // MSSQL 연결
            Connection con = null;
            PreparedStatement pstmt = null; // 한번 사용한 SQL문이 저장되기 때문에 반복해서 사용할 경우 성능이 좋음
            String query = null;

            // DataBase Setting
            // ==================================================================================

            /*알파*/

			 String ip = "211.200.4.166";
			 String port = "12433";
			 String user = "cntt";
			 String pass = "Tldpsxlroqkfwk@)!^";
			 String db_name = "CMS_KAKAO_NEW";

            /*로컬*/
//
//            String ip = "localhost";
//            String port = "1433";
//            String user = "cntt";
//            String pass = "Tldpsxlroqkfwk@)!^";
//            String db_name = "CMS_KAKAO_NEW_ALAN";

            // ===================================================================================================

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(
                    "jdbc:sqlserver://" + ip + ":" + port + ";SelectMethod=cursor;DatabaseName=" + db_name, user, pass);

            con.setAutoCommit(false);

            int rows = sheet.getPhysicalNumberOfRows(); // 행 개수 가져오기

            for (int rowIndex = 1; rowIndex < rows; rowIndex++) {

                // 방어코드 : 해당 로우가 없을 경우 해당 로우 Insert 안함. (Null이 아닌 행은 Insert 진행)
                // 행 값이 Null인 Row 출력
                if (sheet.getRow(rowIndex) == null)
                    break;
                /*
                 * if (sheet.getRow(rowIndex) == null) {
                 *
                 * System.out.println(
                 * "===================================================================================================="
                 * ); System.out.println((row.getRowNum() + 1) + "번째 Row Null");
                 * System.out.println(rowIndex + "번째 Row Null"); System.out.println(
                 * "===================================================================================================="
                 * );
                 *
                 * continue;
                 *
                 * }
                 */

                /* System.out.println(sheet.getRow(rowIndex)); */

                row = sheet.getRow(rowIndex); // 행 가져오기

                // 방어코드 : 해당 로우의 첫번째 셀의 값이 null인경우 해당 로우 Insert 안함. (Null이 아닌 행은 Insert 진행)
                // 셀 값이 Null인 Row 출력
                /* if (row.getCell(0) == null) break; */
                if (row.getCell(0) == null || row.getCell(0).equals("")) {

                    System.out.println(
                            "====================================================================================================");
                    System.out.println(row.getRowNum() + " Row IDX 값 Null");
                    System.out.println(
                            "====================================================================================================");

                    continue;
                }

                storeId = getExcelVelueToString(row, 0);
//                System.out.println(storeId);
                zoneId = getExcelVelueToString(row, 1);
//                System.out.println(zoneId);
                vtxNo = getExcelVelueToString(row, 2);
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

            }

            // UTMK Excel Row 개수 체크
            int utmkSize = test.size();

            // 리스트를 900개씩 자른다는 의미
            int max = 900; // 쿼리의 #{}부분이 한번에 2100개가 넘으면 안되므로 2100/#{}개수 로 수정해야한다 리

            int query_cnt = 1;

            for (int i = 0; i < utmkSize; i = i + max) {

                if (i + max > utmkSize) { // 남은 리스트의 사이즈가 200미만일경우

                    // Query 작성
                    String sql = "";
                    /* sql = sql + " insert into gis_border_pizzahut values "; */
                    sql = sql + " insert into gis_border_pizzahut_history values ";

                    for (testob ob : test.subList(i, utmkSize)) {
                        sql = sql + "('" + ob.getIdx() + "',  " + ob.getOrd() + ", " + ob.getTrans_x() + ", "
                                + ob.getTrans_y() + ", ";
                        sql = sql
//								+ "convert(varchar(8), GETDATE(), 112), replace(convert(varchar(8), GETDATE(), 108), ':', '')),";
//								+ "convert(varchar(8), GETDATE(), 112), convert(varchar,(convert(int,(replace(CONVERT(varchar(8), getdate(), 108), ':', ''))/100)*100))),";
                                + localdate+","+ localtime
                                +"),";
                    }

                    sql = replaceLast(sql, ",", "");

                    System.out.println(" >> Query 실행 [" + query_cnt + "]");

                    query_cnt++;

                    System.out.println(sql);

                    // Query 실행
                    pstmt = con.prepareStatement(sql);
                    pstmt.execute();

                    break;
                } else { // 남은 리스트의 사이즈가 900이상일 경우

                    // Query 작성
                    String sql = "";
                    /* sql = sql + " insert into gis_border_pizzahut values "; */
                    sql = sql + " insert into gis_border_pizzahut_history values ";

                    for (testob ob : test.subList(i, i + max)) {
                        sql = sql + "('" + ob.getIdx() + "',  " + ob.getOrd() + ", " + ob.getTrans_x() + ", "
                                + ob.getTrans_y() + ", ";
                        sql = sql
//								+ "convert(varchar(8), GETDATE(), 112), replace(convert(varchar(8), GETDATE(), 108), ':', '')),";
//								+ "convert(varchar(8), GETDATE(), 112), convert(varchar,(convert(int,(replace(CONVERT(varchar(8), getdate(), 108), ':', ''))/100)*100))),";
                                + localdate+","+ localtime+"),";
                    }

                    sql = replaceLast(sql, ",", "");

                    System.out.println(" >> Query 실행 [" + query_cnt + "]");

                    query_cnt++;

                    // Query 실행
                    pstmt = con.prepareStatement(sql);
                    pstmt.execute();


                }


            }
            pstmt.close();
            con.commit();
            workbook.close();

            // DB Insert 완료 시 업로드한 파일 삭제
            /*
             * File file = new File(savePath + "/" + fileName); file.delete();
             */

            if (con != null)
                con.close();

            String excelFileName = null;
            excelFileName = fileName;

            System.out.println(
                    "====================================================================================================");
            System.out.println(" UTMK 자료 DB 등록 완료되었습니다.");
            System.out.println("엑셀 파일 이름 : " + excelFileName);
            System.out.println(
                    "====================================================================================================");

        } catch (SQLServerException e) {

            System.out.println(
                    "====================================================================================================");
            System.out.println(" >> 쿼리실행 오류 발생");
            System.out.println(e);
            System.out.println(e.getMessage());
            System.out.println(
                    "====================================================================================================");
        }

        return "uploadActionExcel";

    }

    private String getExcelVelueToString(XSSFRow row, int i) throws Exception {

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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testHome(Locale locale, Model model) throws Exception {

//        logger.info("Welcome home! The client locale is {}."+"\n 오늘 날짜 : "+localdate+" 실행한 시간 : "+localtime, locale);

        // 표준시간(협정 세계시 (UTC))을 기준으로한 현재 시간을 Long형으로 변환
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        // 현재 시간 데이터 변환 ex) #년 #월 #일 (월) 오후 #시 #분 #초
        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);






        return "test";
    }

    @RequestMapping(value = "/uploadActionExcelTest", method = RequestMethod.POST)
    public String excelUploadTest(String[] args, HttpServletRequest request) throws Exception {


        String savePath = request.getRealPath("/"+"upload");
        int sizeLimit = 30 * 1024 * 1024; // 파일 사이즈
        System.out.println(savePath);
        MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8",
                    new DefaultFileRenamePolicy());
        System.out.println(multi.getFileNames());

//        try {
//
//            String strIdx;
//            int strOrd, strCx, strCy;
//
//            String storeId, zoneId, vtxNo;
//            Double utmkX, utmkY;
//
//            /* 엑셀 파일 업로드 저장 경로 */
//            String savePath = request.getRealPath("/") + "upload";
//
//            int sizeLimit = 30 * 1024 * 1024; // 파일 사이즈
//            String formName = "";
//            String fileName = "";
//            long fileSize = 0;
//
//            List<testob> test = new ArrayList<testob>();
//
//            XSSFRow row = null;
//            XSSFCell cell = null;
//
//            // 파일 업로드 처리
//            MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8",
//                    new DefaultFileRenamePolicy());
//
//            Enumeration formNames = multi.getFileNames();
//
//            while (formNames.hasMoreElements()) {
//
//                formName = (String) formNames.nextElement();
//                fileName = multi.getFilesystemName(formName);
//
//                if (fileName != null) { // 파일이 업로드 되면
//
//                    fileSize = multi.getFile(formName).length();
//
//                }
//            }
//
//            // 서버에 업로드한 엑셀 파일의 Sheet1 객체를 가져옴
//            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(savePath + "/" + fileName));
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            /* Sheet sheet = workbook.getSheet(0); */
//
//            // MSSQL 연결
//            Connection con = null;
//            PreparedStatement pstmt = null; // 한번 사용한 SQL문이 저장되기 때문에 반복해서 사용할 경우 성능이 좋음
//            String query = null;
//
//            // DataBase Setting
//            // ==================================================================================
//
//            /*알파*/
//
////			 String ip = "211.200.4.166";
////			 String port = "12433";
////			 String user = "cntt";
////			 String pass = "Tldpsxlroqkfwk@)!^";
////			 String db_name = "CMS_KAKAO_NEW";
//
//            /*로컬*/
////
//            String ip = "localhost";
//            String port = "1433";
//            String user = "cntt";
//            String pass = "Tldpsxlroqkfwk@)!^";
//            String db_name = "CMS_KAKAO_NEW_ALAN";
//
//            // ===================================================================================================
//
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            con = DriverManager.getConnection(
//                    "jdbc:sqlserver://" + ip + ":" + port + ";SelectMethod=cursor;DatabaseName=" + db_name, user, pass);
//
//            con.setAutoCommit(false);
//
//            int rows = sheet.getPhysicalNumberOfRows(); // 행 개수 가져오기
//
//            for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
//
//                // 방어코드 : 해당 로우가 없을 경우 해당 로우 Insert 안함. (Null이 아닌 행은 Insert 진행)
//                // 행 값이 Null인 Row 출력
//                if (sheet.getRow(rowIndex) == null)
//                    break;
//                /*
//                 * if (sheet.getRow(rowIndex) == null) {
//                 *
//                 * System.out.println(
//                 * "===================================================================================================="
//                 * ); System.out.println((row.getRowNum() + 1) + "번째 Row Null");
//                 * System.out.println(rowIndex + "번째 Row Null"); System.out.println(
//                 * "===================================================================================================="
//                 * );
//                 *
//                 * continue;
//                 *
//                 * }
//                 */
//
//                /* System.out.println(sheet.getRow(rowIndex)); */
//
//                row = sheet.getRow(rowIndex); // 행 가져오기
//
//                // 방어코드 : 해당 로우의 첫번째 셀의 값이 null인경우 해당 로우 Insert 안함. (Null이 아닌 행은 Insert 진행)
//                // 셀 값이 Null인 Row 출력
//                /* if (row.getCell(0) == null) break; */
//                if (row.getCell(0) == null || row.getCell(0).equals("")) {
//
//                    System.out.println(
//                            "====================================================================================================");
//                    System.out.println(row.getRowNum() + " Row IDX 값 Null");
//                    System.out.println(
//                            "====================================================================================================");
//
//                    continue;
//                }
//
//                storeId = getExcelVelueToString(row, 0);
////                System.out.println(storeId);
//                zoneId = getExcelVelueToString(row, 1);
////                System.out.println(zoneId);
//                vtxNo = getExcelVelueToString(row, 2);
////                System.out.println(vtxNo);
//                utmkX = row.getCell(3).getNumericCellValue();
////                System.out.println(utmkX);
//                utmkY = row.getCell(4).getNumericCellValue();
////                System.out.println(utmkY);
//
//                test.add(new testob(
//                        (storeId.indexOf(".") > -1 ? storeId.substring(0, storeId.indexOf(".")) : storeId) + zoneId,
//                        vtxNo, utmkX, utmkY));
//
//                // Insert 데이터 확인
//                /*
//                 * System.out.println("storeId : " + storeId + " / " + "zoneId : " + zoneId +
//                 * " / " + "vtxNo : " + vtxNo + " / " + "utmkX : " + utmkX + " / " + "utmkY : "
//                 * + utmkY);
//                 */
//
//            }
//
//            // UTMK Excel Row 개수 출력
//            System.out.println(
//                    "====================================================================================================");
//            System.out.println(" UTMK Excel Row Count (Header 제외) : " + test.size());
//            System.out.println(
//                    "====================================================================================================");
//
//            // UTMK > KAINESS 좌표로 변환
//            for (testob ob : test) {
//                GeoPoint in_pt = new GeoPoint(ob.getOrigin_x(), ob.getOrigin_y());
//                GeoPoint katec_pt = GeoTrans.convert(GeoTrans.UTMK, GeoTrans.KATEC, in_pt);
//
//                ob.setTrans_x(katec_pt.getX());
//                ob.setTrans_y(katec_pt.getY());
//
//                // 변환된 KAINESS 좌표 넣기
//                /*
//                 * strIdx = ob.idx; strOrd = (int) Double.parseDouble(ob.ord); strCx =
//                 * Integer.parseInt(String.valueOf(Math.round(ob.trans_x))); strCy =
//                 * Integer.parseInt(String.valueOf(Math.round(ob.trans_y)));
//                 */
//
//            }
//
//            // UTMK Excel Row 개수 체크
//            int utmkSize = test.size();
//
//            // 리스트를 900개씩 자른다는 의미
//            int max = 900; // 쿼리의 #{}부분이 한번에 2100개가 넘으면 안되므로 2100/#{}개수 로 수정해야한다 리
//
//            int query_cnt = 1;
//
//            for (int i = 0; i < utmkSize; i = i + max) {
//
//                if (i + max > utmkSize) { // 남은 리스트의 사이즈가 200미만일경우
//
//                    // Query 작성
//                    String sql = "";
//                    /* sql = sql + " insert into gis_border_pizzahut values "; */
//                    sql = sql + " insert into gis_border_pizzahut_history values ";
//
//                    for (testob ob : test.subList(i, utmkSize)) {
//                        sql = sql + "('" + ob.getIdx() + "',  " + ob.getOrd() + ", " + ob.getTrans_x() + ", "
//                                + ob.getTrans_y() + ", ";
//                        sql = sql
////								+ "convert(varchar(8), GETDATE(), 112), replace(convert(varchar(8), GETDATE(), 108), ':', '')),";
////								+ "convert(varchar(8), GETDATE(), 112), convert(varchar,(convert(int,(replace(CONVERT(varchar(8), getdate(), 108), ':', ''))/100)*100))),";
//                                + localdate+","+ localtime
//                                +"),";
//                    }
//
//                    sql = replaceLast(sql, ",", "");
//
//                    System.out.println(" >> Query 실행 [" + query_cnt + "]");
//
//                    query_cnt++;
//
//                    System.out.println(sql);
//
//                    // Query 실행
//                    pstmt = con.prepareStatement(sql);
//                    pstmt.execute();
//
//                    break;
//                } else { // 남은 리스트의 사이즈가 900이상일 경우
//
//                    // Query 작성
//                    String sql = "";
//                    /* sql = sql + " insert into gis_border_pizzahut values "; */
//                    sql = sql + " insert into gis_border_pizzahut_history values ";
//
//                    for (testob ob : test.subList(i, i + max)) {
//                        sql = sql + "('" + ob.getIdx() + "',  " + ob.getOrd() + ", " + ob.getTrans_x() + ", "
//                                + ob.getTrans_y() + ", ";
//                        sql = sql
////								+ "convert(varchar(8), GETDATE(), 112), replace(convert(varchar(8), GETDATE(), 108), ':', '')),";
////								+ "convert(varchar(8), GETDATE(), 112), convert(varchar,(convert(int,(replace(CONVERT(varchar(8), getdate(), 108), ':', ''))/100)*100))),";
//                                + localdate+","+ localtime+"),";
//                    }
//
//                    sql = replaceLast(sql, ",", "");
//
//                    System.out.println(" >> Query 실행 [" + query_cnt + "]");
//
//                    query_cnt++;
//
//                    // Query 실행
//                    pstmt = con.prepareStatement(sql);
//                    pstmt.execute();
//
//
//                }
//
//
//            }
//            pstmt.close();
//            con.commit();
//            workbook.close();
//
//            // DB Insert 완료 시 업로드한 파일 삭제
//            /*
//             * File file = new File(savePath + "/" + fileName); file.delete();
//             */
//
//            if (con != null)
//                con.close();
//
//            String excelFileName = null;
//            excelFileName = fileName;
//
//            System.out.println(
//                    "====================================================================================================");
//            System.out.println(" UTMK 자료 DB 등록 완료되었습니다.");
//            System.out.println("엑셀 파일 이름 : " + excelFileName);
//            System.out.println(
//                    "====================================================================================================");
//
//        } catch (SQLServerException e) {
//
//            System.out.println(
//                    "====================================================================================================");
//            System.out.println(" >> 쿼리실행 오류 발생");
//            System.out.println(e.getMessage());
//            System.out.println(
//                    "====================================================================================================");
//        }

        return "uploadActionExcelTest";

    }

    /**
     * SPC 현금 영수증
     * @return
     */
    @RequestMapping("/receiptOfSpc")
    public ModelAndView receiptOfSpc() throws UnsupportedEncodingException, GeneralSecurityException, JsonProcessingException {
        ModelAndView mav = new ModelAndView("receiptOfSpc");

//        if (StringUtils.isEmpty(param.getAccessToken())
//                || StringUtils.isEmpty(param.getId())
////            || StringUtils.isEmpty(param.getSeq()) // seq를 카카오에서는 따로 저장/전송하지 않기 때문에 파라미터에서 제외
//                ) {
//            mav.addObject("error_message", "파라미터가 유효하지 않습니다.");
//            return mav;
//        }
//
//        String custId = reverseApi.getCustId(param.getAccessToken());
//
//        if (StringUtils.isEmpty(custId)) {
//            mav.addObject("error_message", "유효하지 않은 사용자입니다.");
//            return mav;
//        }
//
//        // 주문 마스터 조회
//        TbOrderMaster od = orderService.getOrderMasterKakao(param.getId());
//
//        if (od == null || !custId.equals(od.getCust_id())) {
//            mav.addObject("error_message", "유효하지 않은 주문 번호입니다.");
//            return mav;
//        }


        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("channelOrderId","25973613");
        data.put("cbPrice","28000");

        ObjectMapper om = new ObjectMapper();
        String data_string = om.writeValueAsString(data);
        String data_json = data.toString();
        System.out.println("data_json : " + data_json);
        System.out.println("data_string : " + data_string);

        AES256Util aes256Util = new AES256Util("qks9kt4zdg19v4046750y03ws66jh61c");

        String final_data =  new String(Base64.encodeBase64(aes256Util.aesEncode(data_string).getBytes()));
        System.out.println("final_data : " +final_data);


        boolean is_prod = true;
        String url = (is_prod) ? "https://www.happyorder.co.kr/?am=etc/cashReceipt" : "https://dev.happyorder.co.kr/?am=etc/cashReceipt";

        mav.addObject("brandCode","BRkorea");//고정
        mav.addObject("channelCode","KAKAO");
        mav.addObject("data", final_data);
        mav.addObject("url",url);


        return mav;

    }



}

