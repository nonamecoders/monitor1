//package com.alan.monitor1.schedule;
//
//import com.alan.monitor1.mapper.OrderItemMapper;
//import com.alan.monitor1.mapper1.SmsMapper;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//public class OrderItemSchedule {
////    @Scheduled(cron = "0 0 * * * *")
//    @Resource
//    OrderItemMapper orderItemMapper;
//
//    @Resource
//    SmsMapper smsMapper;
//
////    @Resource
//
//    @Scheduled(fixedDelay = 10000) // 테스트 (10초간격으로)
//    public void cronJobSch() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        System.out.println("Java cron job expression:: " + strDate);
//
//        try {
//            int order_count =  orderItemMapper.orderItem();
//            System.out.println(order_count+"입니다.");
//
////            String phone="01049070531";
//            String msg="프로모션 판매건수 : " + order_count + "건 입니다.";
//            System.out.println(msg);
//
//
//            int sms_count = smsMapper.selectSms();
//            System.out.println(sms_count);
//
//            smsMapper.insertSms(msg);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
////    @Scheduled(cron="0 0 0/1 * * *") // 테스트 (10초간격으로)
////    public void cronJobSch2() {
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
////        Date now = new Date();
////        String strDate = sdf.format(now);
////        System.out.println("Java cron job expression:: " + strDate);
////
////        try {
////            int order_count =  orderItemMapper.orderItem();
////            System.out.println(order_count+"입니다.");
////
//////            String phone="01049070531";
////            String msg="프로모션 판매건수 : " + order_count + "건 입니다.";
////            System.out.println(msg);
////
////
//////            int sms_count = smsMapper.selectSms();
//////            System.out.println(sms_count);
////
//////            smsMapper.insertSms(msg);
////
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////
////    }
//
//}
