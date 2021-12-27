//package com.alan.monitor1.service;
//
//
//import com.alan.monitor1.mapper.OrderMapper;
//import com.alan.monitor1.order.Order;
//import com.alan.monitor1.order.SPCShortage;
//import org.apache.ibatis.session.SqlSession;
//import org.json.JSONObject;
//import org.json.JSONArray;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service("com.alan.monitor1.service.OrderService")
//public class OrderService {
//    @Resource(name = "com.alan.monitor1.mapper.OrderMapper")
//    OrderMapper mOrderMapper;
//
//    @Autowired
//    @Qualifier("db1BatchSqlSessionTemplate")
//    SqlSession sqlBatchSession;
//
//    public OrderService(SqlSession sqlBatchSession) {
//        this.sqlBatchSession = sqlBatchSession;
//    }
//
////    public List<Order> orderListService() throws Exception{
////
////        return mOrderMapper.orderList();
////    }
//
//    public List<Order> orderListService(String start_date, String end_date) throws Exception {
//
//        return mOrderMapper.orderList(start_date, end_date);
//    }
//
//
//    @Transactional
//    public void shortageTest(String params) throws Exception {
//
//        JSONObject obj = new JSONObject(params);
//        List<SPCShortage> insertParams = new ArrayList<SPCShortage>();
//
//        long start = System.currentTimeMillis();
//
//        JSONArray jarray = new JSONArray(obj.get("items").toString());
//
//        // Items Array 풀어서 DTO에 Insert row 별 결품등록 데이터 조합
//        for (int i = 0; i < jarray.length(); i++) {
//            SPCShortage shortageTestDto = new SPCShortage();
//            shortageTestDto.setBrand_cd(obj.get("brand_cd").toString());
//            shortageTestDto.setBranch_id(obj.get("branch_id").toString());
//            shortageTestDto.setItem_cd(jarray.get(i).toString());
//
//            insertParams.add(shortageTestDto);
//        }
////        System.out.println("인서트" + insertParams.get(0));
////        mOrderMapper.batchExecute(insertParams);
//
////        for(int i=0; i<insertParams.size(); i++){
////            mOrderMapper.batchExecute(insertParams.get(i));
////        }
//
//        batchFunction(insertParams);
//
//        long end = System.currentTimeMillis();
//
//        long total = end - start;
//        System.out.println("Total Process Time : " + total + "(ms)");
//
//    }
//
//    public void batchFunction(List<SPCShortage> insertParams){
//
//        for(int i=0; i<insertParams.size(); i++){
//            this.sqlBatchSession.insert("batchExecute",insertParams.get(i));
//        }
//        sqlBatchSession.flushStatements();
//    }
//
//
//}
