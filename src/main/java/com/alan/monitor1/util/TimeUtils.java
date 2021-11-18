package com.alan.monitor1.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 *
 * 시간 관련 유틸리티
 *
 * 참고 : 일자시간관련 포맷팅  https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
 *
 *
 * 결제페이지 / 알림톡 등 날짜/시간 표기 통일 : http://jira.daumkakao.com/browse/CORDER-1039
 * -----------------------------------------------------------------------------------------
 *   날짜만 : 2017.07.12(화)
 *   시간만 : 15:30 ("30분 뒤 도착 예정" 등은 제외)
 *   날짜+시간 : 2017.07.12(화) 15:30
 *
 */
@Slf4j
public class TimeUtils {

  /**
   * DateTimeFormatter : yyyyMMddHHmmssSSS
   */
  public static final DateTimeFormatter DATE_TIME_MILIE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

  /**
   * DateTimeFormatter : yyyy-MM-dd HH:mm:ss.SSS
   */
  public static final DateTimeFormatter DATE_TIME_MILIE_FORMAT_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


  /**
   * DateTimeFormatter : yyyy-MM-dd HH:mm
   */
  public static final DateTimeFormatter DATE_TIME_MINUTE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  /**
   * DateTimeFormatter : yyyy-MM-dd HH:mm:ss
   */
  public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * DateTimeFormatter : yyyy-MM-ddTHH:mm:ss
   */
  public static final DateTimeFormatter DATE_TIME_FORMAT_T = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  /**
   * DateTimeFormatString : 2017년 11월 20일 (월) 10:45
   */
  public static final String DATE_TIME_READABLE_FORMAT_1_STRING = "yyyy년 M월 d일 (E) HH:mm";

  /**
   * DateTimeFormatString : 2017.07.12(화) 15:30
   */
  public static final String DATE_TIME_READABLE_FORMAT_2_STRING = "yyyy.MM.dd(E) HH:mm";

  /**
   * DateTimeFormatter : 2017년 11월 20일 (월) 10:45
   */
  public static final DateTimeFormatter DATE_TIME_READABLE_FORMAT_1 = DateTimeFormatter.ofPattern(DATE_TIME_READABLE_FORMAT_1_STRING);

  /**
   * DateTimeFormatter : 2017.07.12(화) 15:30
   */
  public static final DateTimeFormatter DATE_TIME_READABLE_FORMAT_2 = DateTimeFormatter.ofPattern(DATE_TIME_READABLE_FORMAT_2_STRING);

  /**
   * DateTimeFormatter : yyyyMMdd
   */
  public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

  /**
   * DateTimeFormatString : 2017.07.12(화)
   */
  public static final String DATE_READABLE_FORMAT_2_STRING = "yyyy.MM.dd(E)";

  /**
   * DateTimeFormatter : 2017.07.12(화)
   */
  public static final DateTimeFormatter DATE_READABLE_FORMAT_2 = DateTimeFormatter.ofPattern(DATE_READABLE_FORMAT_2_STRING);

  /**
   * DateTimeFormatter : HHmmss
   */
  public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HHmmss");

  /**
   * DateTimeFormatter : HHmm
   */
  public static final DateTimeFormatter TIME_HM_FORMAT = DateTimeFormatter.ofPattern("HHmm");

  /**
   * DateTimeFormatString : HH:mm
   */
  public static final String TIME_READABLE_FORMAT_1_STRING = "HH:mm";

  /**
   * DateTimeFormatter : HH:mm
   */
  public static final DateTimeFormatter TIME_READABLE_FORMAT_1 = DateTimeFormatter.ofPattern(TIME_READABLE_FORMAT_1_STRING);

  /**
   * DateTimeFormatString : yyyyMMddHHmmss
   */
  public static final String yyyyMMddHHmmss_FORMAT_STRING = "yyyyMMddHHmmss";

  /**
   * DateTimeFormatter : yyyyMMddHHmmss
   */
  public static final DateTimeFormatter yyyyMMddHHmmss_FORMAT = DateTimeFormatter.ofPattern(yyyyMMddHHmmss_FORMAT_STRING);



  /**
   * 
   * @param date : "yyyy-MM-dd HH:mm:ss"
   * @param format : 지정형식
   * @return
   */
  public static Date getStringTransDateFormat(String date, String format) {
    Date result = null;
    SimpleDateFormat transFormat = new SimpleDateFormat(format);

    try {
      result = transFormat.parse(date);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return result;
  }

  /**
   * convert DateTime String format yyyyMMddHHmmssSSS to Date
   * @param s
   * @return Date
   */
  public static Date dateParseDateTimeMilisec(String s) {
    return getStringTransDateFormat(s, "yyyyMMddHHmmssSSS");
  }
  
  /**
   * @return yyyyMMddHHmmssSSS
   */
  public static String getCurrentTime_DATE_TIME_MILIE_FORMAT() {
    ZonedDateTime now = ZonedDateTime.now();
    return now.format(DATE_TIME_MILIE_FORMAT);
  }

  /**
   * @return yyyyMMddHHmmssSSS
   */
  public static String getCurrentTime_DATE_TIME_MILIE_FORMAT_1() {
    ZonedDateTime now = ZonedDateTime.now();
    return now.format(DATE_TIME_MILIE_FORMAT_1);
  }

  /**
   * @return yyyy-MM-dd HH:mm
   */
  public static String getCurrentTime_DATE_TIME_MINUTE() {
    ZonedDateTime now = ZonedDateTime.now();
    return now.format(DATE_TIME_MINUTE_FORMAT);
  }

  /**
   * @return yyyy-MM-dd HH:mm:ss
   */
  public static String getCurrentTime_DATE_TIME_FORMAT() {
    ZonedDateTime now = ZonedDateTime.now();
    return now.format(DATE_TIME_FORMAT);
  }

  /**
   * @return yyyy-MM-ddTHH:mm:ss
   */
  public static String getCurrentTime_DATE_TIME_FORMAT_T() {
    ZonedDateTime now = ZonedDateTime.now();
    return now.format(DATE_TIME_FORMAT_T);
  }


  /**
   * 
   * @param addMinute
   * @return yyyy-MM-dd HH:mm:ss
   */
  public static String getCurrentTime_DATE_TIME_FORMAT(int addMinute) {
    ZonedDateTime now = ZonedDateTime.now();

    if (addMinute > 0) {
      now = now.plusMinutes(addMinute);
    } else if (addMinute < 0) {
      now = now.minusMinutes(addMinute);
    }

    return now.format(DATE_TIME_FORMAT);
  }

  /**
   * @return yyyyMMdd
   */
  public static String getCurrentTime_DATE_FORMAT() {
    ZonedDateTime now = ZonedDateTime.now();
    return now.format(DATE_FORMAT);
  }

  /**
   * @return HHmmss
   */
  public static String getCurrentTime_TIME_FORMAT() {
    ZonedDateTime now = ZonedDateTime.now();
    return now.format(TIME_FORMAT);
  }

  public static long getCurrentUnixTimestamp_SECOND() {
    Instant timestamp = Instant.now();
    return timestamp.getEpochSecond();
  }

  public static long getCurrentUnixTime() {
    Instant timestamp = Instant.now();
    return timestamp.toEpochMilli();
  }

  /**
   * convert Unix Epoch Time(long) to yyyyMMddHHmmss(string)
   * 
   * @param epochTime
   * @return yyyyMMddHHmmss
   */
  public static String getyyyyMMddHHmmss(long epochTime) {
    return Instant.ofEpochSecond(epochTime).atZone(ZoneId.systemDefault()).format(yyyyMMddHHmmss_FORMAT);
  }

  /**
   * (밀리초단위 변환) convert Unix Epoch Time(long) to yyyyMMddHHmmss(string)
   * @param epochTimeMilliseconds
   * @return
   */
  public static String getyyyyMMddHHmmssOfMs(long epochTimeMilliseconds) {
    return getyyyyMMddHHmmss(epochTimeMilliseconds / 1000);
  }

  public static LocalDateTime getLocalDate(String date, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return LocalDateTime.parse(date, formatter);
  }

  public static LocalDateTime getLocalDate(String date, DateTimeFormatter formatter) {
    return LocalDateTime.parse(date, formatter);
  }

  public static LocalTime getLocalTime(String time, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return LocalTime.parse(time, formatter);
  }

  public static LocalTime getLocalTime(String time, DateTimeFormatter formatter) {
    return LocalTime.parse(time, formatter);
  }

  public static String getCustomFormatDate(String datetime, String parseFormat, String returnFormat) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(returnFormat);
    LocalDateTime localDate = getLocalDate(datetime, parseFormat);
    return localDate.format(formatter);

  }

  public static String getCustomFormatDate(String datetime, DateTimeFormatter parseFormatter, DateTimeFormatter returnFormatter) {
    LocalDateTime localDate = getLocalDate(datetime, parseFormatter);
    return localDate.format(returnFormatter);

  }

  public static String getCustomFormatTime(String time, String parseFormat, String returnFormat) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(returnFormat);
    LocalTime localTime = getLocalTime(time, parseFormat);
    return localTime.format(formatter);

  }

  public static String getCustomFormatTime(String time, DateTimeFormatter parseFormatter, DateTimeFormatter returnFormatter){
    LocalTime localTime = getLocalTime(time, parseFormatter);
    return localTime.format(returnFormatter);

  }

  public static String getCustomFormatReserveDate(String paramDateTime, String parseFormat){
    LocalDateTime paramLocalDateTime = getLocalDate(paramDateTime, parseFormat);

    // 예약일 LocalDate (연월일)
    LocalDate paramLocalDate = paramLocalDateTime.toLocalDate();

    // 당일 LocalDate  (연월일)
    LocalDate todayLocalDate  = LocalDateTime.now().toLocalDate();

    String returnFormat = "";

    int compareValue = paramLocalDate.compareTo(todayLocalDate);

    if( compareValue > 0 ) // 연월일을 기준으로 예약일이 당일보다 미래라면,
      returnFormat = "\n" + DATE_TIME_READABLE_FORMAT_2_STRING;
    else
      returnFormat = TIME_READABLE_FORMAT_1_STRING;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(returnFormat, Locale.KOREAN);

    return paramLocalDateTime.format(formatter);
  }

  /**
   * 포맷팅#1 (HHmmss -> HH:mm)
   *
   * @param time
   * @return
   */
  public static String format1(String time) {
    if(StringUtils.isEmpty(time)) return null;

    try {
      return LocalTime.parse(time, TIME_FORMAT).format(TIME_READABLE_FORMAT_1);

    } catch (DateTimeException dte) {
      log.error("DateTimeException", dte);
      return null;

    }

  }

  /**
   * 포맷팅#2 (yyyy-MM-dd HH:mm:ss -> yyyyMMddHHmmss)
   *
   * @param datetime
   * @return
   */
  public static String format2(String datetime) {
    if(StringUtils.isEmpty(datetime)) return null;

    try {
      return  LocalDateTime.parse(datetime, DATE_TIME_FORMAT).format(yyyyMMddHHmmss_FORMAT);

    } catch (DateTimeException dte) {
      log.error("DateTimeException", dte);
      return null;

    }

  }

}
