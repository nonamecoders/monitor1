package com.alan.monitor1.common;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Main {

//    static String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);//yyyymmdd

    public static void main(String[] args) throws IOException {

        List<String> exceptList = Arrays.asList("20220130", "20220131", "20220201", "20220202", "20220203", "20220204");

        List<String> answer = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int day = 2;
        String apply_date = "";

        int count = 0;
        while (count <7) {
            LocalDate order_date = LocalDate.now().plusDays(day);

//        System.out.println(today);
//        System.out.println(order_date);


            int period = Period.between(today, order_date).getDays();
//        System.out.println(period);

//        String start_date = LocalDate.now().plusDays(period).plusDays(2).format(DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate start_date = LocalDate.now().plusDays(period).plusDays(2);
//        String end_date = LocalDate.now().plusDays(period).plusDays(9).format(DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate end_date = LocalDate.now().plusDays(period).plusDays(9);

            period = Period.between(start_date, end_date).getDays();


//        while (count < 7) {
            for (int i = 0; i < period; i++) {
                apply_date = start_date.plusDays(i).toString().replace("-", "");
//                System.out.println(apply_date);

                boolean contains = exceptList.stream().anyMatch(apply_date::equals);
                if (!contains) {
                    answer.add(apply_date);

                }
//                System.out.println(answer.size());
            }

            System.out.println("--order_date : " + order_date);
//            System.out.println(" >>" + answer);
            String s = answer.stream()
                    .collect(Collectors.joining("|"));
//            System.out.println(s);
            System.out.println("insert into TB_SCHEDULE(BRAND_CD, SCHEDULE_TYPE, START_DATE, END_DATE, SPECIFIC_DAYS, MIN_DAYS, MAX_DAYS, IS_USE,REG_ID, REG_DATE, REG_TIME, UPD_ID, UPD_DATE ,UPD_TIME)\n" +
                    "values(\n" +
                    "'PBparis'--BRAND_CD\n" +
                    ",'SPECIFIC_DAYS'--SCHEDULE_TYPE\n" +
                    ",null--START_DATE\n" +
                    ",null\t--END_DATE");
            System.out.println(",'" + s + "'-- SPECIFIC_DAYS");
            System.out.println(",NULL--MIN_DAYS\n" +
                    ",NULL--MAX_DAYS\n" +
                    ",'Y'--IS_USE\n" +
                    ",'alan'--REG_ID\n" +
                    ",Convert(varchar(10),Getdate(),112)--REG_DATE\n" +
                    ",Replace(Convert(varchar(8),Getdate(),108),':','')--REG_TIME\n" +
                    ",'alan'--UPD_ID\n" +
                    ",Convert(varchar(10),Getdate(),112)--UPD_DATE\n" +
                    ",Replace(Convert(varchar(8),Getdate(),108),':','')--UPD_TIME\n" +
                    ")");
            count = answer.size();
            answer = new ArrayList<>();
            day++;
            System.out.println();
            System.out.println();
//        }
        }
    }
}

